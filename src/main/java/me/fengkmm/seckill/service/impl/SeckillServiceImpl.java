package me.fengkmm.seckill.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.fengkmm.seckill.dto.Exposer;
import me.fengkmm.seckill.dto.SeckillExecution;
import me.fengkmm.seckill.entity.SeckillGoods;
import me.fengkmm.seckill.entity.SuccessKilled;
import me.fengkmm.seckill.enums.SecKillStatEnum;
import me.fengkmm.seckill.exception.RepeatKillException;
import me.fengkmm.seckill.exception.SecKillCloseException;
import me.fengkmm.seckill.exception.SecKillException;
import me.fengkmm.seckill.mapper.SecKillGoodsDao;
import me.fengkmm.seckill.mapper.SuccessKillDao;
import me.fengkmm.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 *
 * 秒杀接口实现类
 * @author Administrator
 */
@Slf4j
@Service
public class SeckillServiceImpl implements SeckillService {

    private final SecKillGoodsDao secKillGoodsDao;

    private final SuccessKillDao successKillDao;

    /**
     * md5盐  随机字符串
     */
    private final String slat = "daljkdklasjdflkajsdflkjfks";

    @Autowired
    public SeckillServiceImpl(SecKillGoodsDao secKillGoodsDao, SuccessKillDao successKillDao) {
        this.secKillGoodsDao = secKillGoodsDao;
        this.successKillDao = successKillDao;
    }

    @Override
    public List<SeckillGoods> getSeckillList() {
        return secKillGoodsDao.queryAll(0, 4);
    }

    @Override
    public SeckillGoods getById(long seckillId) {
        return secKillGoodsDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        SeckillGoods seckillGoods = secKillGoodsDao.queryById(seckillId);
        if (seckillGoods == null) {
            return new Exposer(false,seckillId);
        }
        Date startTime = seckillGoods.getStartTime();
        Date endTime = seckillGoods.getEndTime();
        Date nowTime = new Date();
        if (nowTime.getTime() > endTime.getTime() || nowTime.getTime() < startTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    @Override
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SecKillException {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SecKillException("seckill data rewrite");
        }
        //执行秒杀逻辑：减库存+记录购买行为
        Date nowTime = new Date();
        try {
            int updateCount = secKillGoodsDao.reduceNumber(seckillId, nowTime);
            if (updateCount <= 0) {
                //没有更新记录，秒杀结束   （库存没有或者时间不在秒杀范围)
                throw new SecKillCloseException("seckill is closed");
            } else {
                int insertCount = successKillDao.insertSuccessKilled(seckillId, userPhone);
                if (insertCount <= 0) {
                    //重复秒杀
                    throw new RepeatKillException("seckill repeated");
                } else {
                    SuccessKilled successKilled = successKillDao.queryByGoodsIdAndUserPhone(seckillId,userPhone);
                    return new SeckillExecution(seckillId, SecKillStatEnum.SUCCESS, successKilled);
                }
            }
        } catch (SecKillCloseException | RepeatKillException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new SecKillException("seckill inner error:" + e.getMessage());
        }
    }
    private String getMD5(long seckillId){
        String base = seckillId + "/" + slat;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
