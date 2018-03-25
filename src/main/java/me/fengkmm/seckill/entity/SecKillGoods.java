package me.fengkmm.seckill.entity;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author Administrator
 * 秒杀商品表
 */
@Data
@Repository
public class SecKillGoods {
    /**
     * 商品ID
     */
    private long goodsId;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品数量
     */
    private int quantity;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 创建时间
     */
    private Date createTime;

    public SecKillGoods() {
    }

    public SecKillGoods(long goodsId, String name, int number, Date startTime, Date endTime, Date createTime) {
        this.goodsId = goodsId;
        this.name = name;
        this.quantity = number;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createTime = createTime;
    }
}
