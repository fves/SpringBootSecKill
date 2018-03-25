package me.fengkmm.seckill.exception;

/**
 * 重复秒杀异常
 * @author Administrator
 */

public class RepeatKillException extends SecKillException {
    public RepeatKillException(String message) {
        super(message);
    }
}
