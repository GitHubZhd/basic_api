package com.toefl.basic.dto;

public class Lrc{
    private String time;
    private String cn;
    private String en;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    @Override
    public String toString() {
        return "Lrc{" +
                "time='" + time + '\'' +
                ", cn='" + cn + '\'' +
                ", en='" + en + '\'' +
                '}';
    }
}
