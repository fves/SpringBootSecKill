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
public class SuccessSecKilled {
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
    private SecKillGoods secKillGoods;

    public SuccessSecKilled() {
    }

    public SuccessSecKilled(long goodsId, long userPhone, short state, Date createTime, SecKillGoods secKillGoods) {
        this.goodsId = goodsId;
        this.userPhone = userPhone;
        this.state = state;
        this.createTime = createTime;
        this.secKillGoods = secKillGoods;
    }
}
