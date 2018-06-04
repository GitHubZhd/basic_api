package com.toefl.basic.guava;

import com.google.common.base.Throwables;

public class ThrowablesTest {

    public void run(){
        try{
//            delegate.run();
        }catch (RuntimeException e){
//            failures.increment();
            throw e;
        }catch (Error e){
//            failures.increment();
            throw e;
        }
    }

    //Java7用多重捕获解决了这个问题
    public void run1(){
        try{
//            delegate.run();
        }catch (RuntimeException | Error e){
//            failures.increment();
            throw e;
        }
    }

    public void run2(){
        try{
//            delegate.run();
        }catch (Throwable e){
//            failures.increment();
            throw e;
        }
    }

    /**
     * 尤其要注意的是，这个方案只适用于处理RuntimeException 或Error。
     * 如果catch块捕获了受检异常，你需要调用propagateIfInstanceOf来保留原始代码的行为，因为Throwables.propagate不能直接传播受检异常。
     * 总之，Throwables.propagate的这种用法也就马马虎虎，在Java7及以上中就没必要这样做了。
     */
    public void run3(){
        try{
//            delegate.run();
        }catch (Throwable t){
//            failures.increment();
            throw Throwables.propagate(t);
        }
    }
}
