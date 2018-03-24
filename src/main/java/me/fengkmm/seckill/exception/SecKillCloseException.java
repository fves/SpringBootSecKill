package me.fengkmm.seckill.exception;

/**
 * 秒杀关闭异常
 * @author Administrator
 */
public class SecKillCloseException extends SecKillException {
    public SecKillCloseException(String message) {
        super(message);
    }

    public SecKillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
