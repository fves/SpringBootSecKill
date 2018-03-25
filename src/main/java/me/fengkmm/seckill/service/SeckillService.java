package me.fengkmm.seckill.service;

import me.fengkmm.seckill.dto.Exposer;
import me.fengkmm.seckill.dto.SecKillExecution;
import me.fengkmm.seckill.entity.SecKillGoods;
import me.fengkmm.seckill.exception.RepeatKillException;
import me.fengkmm.seckill.exception.SecKillCloseException;
import me.fengkmm.seckill.exception.SecKillException;

import java.util.List;

/**
 * 秒杀接口
 *
 * @author Administrator
 */
public interface SeckillService {
    /**
     * 查询所有秒杀记录
     * @return
     */
    List<SecKillGoods> getSeckillList();

    /**
     * 查询单个秒杀记录
     * @param goodsId 秒杀商品ID
     * @return 秒杀商品
     */
    SecKillGoods getById(long goodsId);

    /**
     * 秒杀开启是输出秒杀接口地址，
     * 否则输出系统时间和秒杀时间
     * @param goodsId 秒杀商品ID
     * @return 秒杀状态结果
     */
    Exposer exportSeckillUrl(long goodsId);

    /**
     * 执行秒杀操作
     * @param goodsId 商品ID
     * @param userPhone  用户手机号码
     * @param md5 加密验证
     * @return 秒杀执行结果
     * @throws SecKillException 秒杀相关的异常
     */
    SecKillExecution executeSeckill(long goodsId, long userPhone, String md5) throws SecKillException;


}
