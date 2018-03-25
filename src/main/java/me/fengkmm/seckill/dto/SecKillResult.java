package me.fengkmm.seckill.dto;

import lombok.Data;

/**
 * 封装秒杀返回结果
 * @author Administrator
 */
@Data
public class SecKillResult<T> {
    private boolean success;
    private T data;
    private String error;

    public SecKillResult() {
    }

    public SecKillResult(boolean success, T data, String error) {
        this.success = success;
        this.data = data;
        this.error = error;
    }

    public SecKillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public SecKillResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }
}
