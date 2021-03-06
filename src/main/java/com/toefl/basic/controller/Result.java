package com.toefl.basic.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hai
 */
public class Result implements Serializable {
    /**
     * @fields serialVersionUID
     */
    private static final long serialVersionUID = 5905715228490291386L;
    /**
     * @fields status  状态信息，正确返回OK，否则返回 ERROR，如果返回ERROR则需要填写Message信息
     */
    private Status status;
    /**
     * @fields record 消息对象
     */
    private Object message;
    private String nowTime;

    /**
     * 添加成功结果信息
     */
    public void addOK(Object message) {
        this.status = Status.OK;
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    /**
     * 添加错误消息
     *
     * @param message
     */
    public void addError(Object message) {
        this.status = Status.ERROR;
        this.message = message;
    }

    public Result(Status status, Object message) {
        this.status = status;
        this.message = message;
    }

    public Result(Long total, List data) {
        this.addDatas(total, data);
    }

    public void addDatas(Long total, List datas) {
        this.status = Status.OK;
        Map<String, Object> datasMap = new HashMap<>();
        datasMap.put("total", total);
        datasMap.put("data", datas);
        this.message = datasMap;
    }
    /**
     * @fields record 消息对象
     */
    public enum Status {
        OK, ERROR
    }
}
