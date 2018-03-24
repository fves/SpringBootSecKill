package me.fengkmm.seckill.entity;

import lombok.Data;
import org.springframework.stereotype.Repository;
import java.util.Date;


/**
 * @author Administrator
 * 秒杀成功表
 */
@Data
@Repository
public class SuccessKilled {
    /**
     * 商品ID
     */
    private long goodsId;
    /**
     * 用户手机号码
     */
    private long userPhone;
    /**
     * 秒杀状态
     */
    private short state;
    /**
     * 秒杀时间
     */
    private Date createTime;
    /**
     * 秒杀商品信息
     */
    private SeckillGoods seckillGoods;

    public SuccessKilled() {
    }

    public SuccessKilled(long goodsId, long userPhone, short state, Date createTime, SeckillGoods seckillGoods) {
        this.goodsId = goodsId;
        this.userPhone = userPhone;
        this.state = state;
        this.createTime = createTime;
        this.seckillGoods = seckillGoods;
    }
}
