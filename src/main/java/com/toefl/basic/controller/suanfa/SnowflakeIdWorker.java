package com.toefl.basic.controller.suanfa;

/**
 * Twitter_Snowflake<br>
 * SnowFlake的结构如下(每部分用-分开):<br>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000 <br>
 * 1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0<br>
 * 41位时间截(毫秒级)，注意，41位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)
 * 得到的值），这里的的开始时间截，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下下面程序IdWorker类的startTime属性）。41位的时间截，可以使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69<br>
 * 10位的数据机器位，可以部署在1024个节点，包括5位datacenterId和5位workerId<br>
 * 12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号<br>
 * 加起来刚好64位，为一个Long型。<br>
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右。
 * @author hai
 */
public class SnowflakeIdWorker {

    // ==============================Fields===========================================
    /** 开始时间截 (2015-01-01) */
    private final long twepoch = 1420041600000L;

    /** 机器id所占的位数 */
    private final long workerIdBits = 5L;

    /** 数据标识id所占的位数 */
    private final long datacenterIdBits = 5L;

    /** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /** 支持的最大数据标识id，结果是31 */
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    /** 序列在id中占的位数 */
    private final long sequenceBits = 12L;

    /** 机器ID向左移12位 */
    private final long workerIdShift = sequenceBits;

    /** 数据标识id向左移17位(12+5) */
    private final long datacenterIdShift = sequenceBits + workerIdBits;

    /** 时间截向左移22位(5+5+12) */
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /** 工作机器ID(0~31) */
    private long workerId;

    /** 数据中心ID(0~31) */
    private long datacenterId;

    /** 毫秒内序列(0~4095) */
    private long sequence = 0L;

    /** 上次生成ID的时间截 */
    private long lastTimestamp = -1L;

    //==============================Constructors=====================================
    /**
     * 构造函数
     * @param workerId 工作ID (0~31)
     * @param datacenterId 数据中心ID (0~31)
     */
    public SnowflakeIdWorker(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    // ==============================Methods==========================================
    /**
     * 获得下一个ID (该方法是线程安全的)
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - twepoch) << timestampLeftShift) //
                | (datacenterId << datacenterIdShift) //
                | (workerId << workerIdShift) //
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }

    //==============================Test=============================================
    /** 测试 */
    public static void main(String[] args) {
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        System.out.println(idWorker.maxWorkerId);
        System.out.println(idWorker.maxDatacenterId);
        System.out.println(idWorker.sequenceMask);
        for (int i = 0; i < 10; i++) {
            long id = idWorker.nextId();
            System.out.println(Long.toBinaryString(id));
            System.out.println(id);
        }
    }

    /**
     * ----------------------------按位与运算符（&）----------------------------------
     * 参加运算的两个数据，按二进制位进行“与”运算。
     *
     * 运算规则：0&0=0;   0&1=0;    1&0=0;     1&1=1;
     *        即：两位同时为“1”，结果才为“1”，否则为0
     * 例如：3&5  即 0000 0011 & 0000 0101 = 0000 0001   因此，3&5的值得1。
     *
     * 另，负数按补码形式参加按位与运算。
     * “与运算”的特殊用途：
     * （1）清零。如果想将一个单元清零，即使其全部二进制位为0，只要与一个各位都为零的数值相与，结果为零。
     * （2）取一个数中指定位
     *      方法：找一个数，对应X要取的位，该数的对应位为1，其余位为零，此数与X进行“与运算”可以得到X中的指定位。
     *    例：设X=10101110，
     *      取X的低4位，用 X & 0000 1111 = 0000 1110 即可得到；
     *      还可用来取X的2、4、6位。
     *
     * -------------------------------按位或运算符（|）-------------------------------------
     * 参加运算的两个对象，按二进制位进行“或”运算。
     * 运算规则：0|0=0；   0|1=1；   1|0=1；    1|1=1；
     *       即 ：参加运算的两个对象只要有一个为1，其值为1。
     * 例如:3|5　即 0000 0011 | 0000 0101 = 0000 0111   因此，3|5的值得7。　
     *
     * 另，负数按补码形式参加按位或运算。
     * “或运算”特殊作用：
     * （1）常用来对一个数据的某些位置1。
     * 方法：找到一个数，对应X要置1的位，该数的对应位为1，其余位为零。此数与X相或可使X中的某些位置1。
     * 例：将X=10100000的低4位置1 ，用 X | 0000 1111 = 1010 1111即可得到。
     *
     *
     * ---------------------------------异或运算符（^）---------------------------------
     * 参加运算的两个数据，按二进制位进行“异或”运算。
     * 运算规则：0^0=0；   0^1=1；   1^0=1；   1^1=0；
     *    即：参加运算的两个对象，如果两个相应位为“异”（值不同），则该位结果为1，否则为0。
     *
     * “异或运算”的特殊作用：
     * （1）使特定位翻转 找一个数，对应X要翻转的各位，该数的对应位为1，其余位为零，此数与X对应位异或即可。
     * 例：X=10101110，使X低4位翻转，用X ^ 0000 1111 = 1010 0001即可得到。
     * （2）与0相异或，保留原值 ，X ^ 0000 0000 = 1010 1110。
     * 从上面的例题可以清楚的看到这一点。
     *
     * ------------------------------------取反运算符（~）-------------------------------
     * 参加运算的一个数据，按二进制位进行“取反”运算。
     * 运算规则：~1=0；   ~0=1；
     *       即：对一个二进制数按位取反，即将0变1，1变0。
     * 使一个数的最低位为零，可以表示为：a&~1。
     * ~1的值为1111111111111110，再按“与”运算，最低位一定为0。因为“~”运算符的优先级比算术运算符、关系运算符、逻辑运算符和其他运算符都高。
     *
     * -------------------------------------左移运算符（<<）-------------------------------
     * 将一个运算对象的各二进制位全部左移若干位（左边的二进制位丢弃，右边补0）。
     * 例：a = a << 2 将a的二进制位左移2位，右补0，
     * 左移1位后a = a * 2;
     * 若左移时舍弃的高位不包含1，则每左移一位，相当于该数乘以2。
     *
     * ----------------------------------------右移运算符（>>）-------------------------------
     * 将一个数的各二进制位全部右移若干位，正数左补0，负数左补1，右边丢弃。
     * 操作数每右移一位，相当于该数除以2。
     * 例如：a = a >> 2 将a的二进制位右移2位，
     * 左补0 or 补1 得看被移数是正还是负。
     *
     *
     *
     *
     *
     * >> 运算符把 expression1 的所有位向右移 expression2 指定的位数。expression1 的符号位被用来填充右移后左边空出来的位。向右移出的位被丢弃。
     *
     * 例如，下面的代码被求值后，temp 的值是 -4：
     *
     *   -14 （即二进制的 11110010）右移两位等于 -4 （即二进制的 11111100）。
     *
     *   var temp = -14 >> 2
     *
     *
     *
     *
     *
     *   无符号右移运算符（>>>）
     *
     *
     *
     *
     *
     * >>> 运算符把 expression1 的各个位向右移 expression2 指定的位数。右移后左边空出的位用零来填充。移出右边的位被丢弃。
     *
     * 例如：var temp = -14 >>> 2
     *
     * 变量 temp 的值为 -14 （即二进制的 11111111 11111111 11111111 11110010），向右移两位后等于 1073741820 （即二进制的 00111111 11111111 11111111 11111100）。
     *
     * 复合赋值运算符
     * 位运算符与赋值运算符结合，组成新的复合赋值运算符，它们是：
     *
     * &=    例：a &= b        相当于a=a & b
     *
     * |=    例：a |= b        相当于a=a | b
     *
     * >>=   例：a >>= b       相当于a=a >> b
     *
     * <<= 例：a <<= b       相当于a=a << b
     *
     * ^=   例：a ^= b       相当于a=a ^ b
     *
     * 运算规则：和前面讲的复合赋值运算符的运算规则相似。
     *
     * 不同长度的数据进行位运算
     * 如果两个不同长度的数据进行位运算时，系统会将二者按右端对齐，然后进行位运算。
     *
     * 以“与”运算为例说明如下：我们知道在C语言中long型占4个字节，int型占2个字节，如果一个long型数据与一个int型数据进行“与”运算，右端对齐后，左边不足的位依下面三种情况补足，
     *
     * （1）如果整型数据为正数，左边补16个0。
     *
     * （2）如果整型数据为负数，左边补16个1。
     *
     * （3）如果整形数据为无符号数，左边也补16个0。
     *
     * 如：long a=123;int b=1;计算a & b。
     *
     *
     *
     * 如：long a=123;int b=-1;计算a & b。
     *
     *
     *
     * 如：long a=123;unsigned int b=1;计算a & b。
     */
}
