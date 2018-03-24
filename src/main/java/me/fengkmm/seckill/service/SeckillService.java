package me.fengkmm.seckill.service;

import me.fengkmm.seckill.dto.Exposer;
import me.fengkmm.seckill.dto.SeckillExecution;
import me.fengkmm.seckill.entity.SeckillGoods;
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
    List<SeckillGoods> getSeckillList();

    /**
     * 查询单个秒杀记录
     */
    SeckillGoods getById(long seckillId);

    /**
     * 秒杀开启是输出秒杀接口地址，
     * 否则输出系统时间和秒杀时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SecKillException;


}
