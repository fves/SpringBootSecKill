package me.fengkmm.seckill.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.fengkmm.seckill.dto.Exposer;
import me.fengkmm.seckill.dto.SecKillExecution;
import me.fengkmm.seckill.entity.SecKillGoods;
import me.fengkmm.seckill.entity.SuccessSecKilled;
import me.fengkmm.seckill.enums.SecKillStatEnum;
import me.fengkmm.seckill.exception.RepeatKillException;
import me.fengkmm.seckill.exception.SecKillCloseException;
import me.fengkmm.seckill.exception.SecKillException;
import me.fengkmm.seckill.mapper.SecKillGoodsDao;
import me.fengkmm.seckill.mapper.SuccessSecKillDao;
import me.fengkmm.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.security.auth.callback.Callback;
import java.util.Date;
import java.util.List;

/**
 * 秒杀接口实现类
 *
 * @author Administrator
 */
@Slf4j
@Service
public class SeckillServiceImpl implements SeckillService {

    private final SecKillGoodsDao secKillGoodsDao;

    private final SuccessSecKillDao successSecKillDao;

    /**
     * md5盐 ， 随机字符串，拼接商品ID并md5，验证使用
     */
    private final String slat = "daljkdklasjdflkajsdflkjfks";

    @Autowired
    public SeckillServiceImpl(SecKillGoodsDao secKillGoodsDao, SuccessSecKillDao successSecKillDao) {
        this.secKillGoodsDao = secKillGoodsDao;
        this.successSecKillDao = successSecKillDao;
    }

    @Override
    public List<SecKillGoods> getSeckillList() {
        return secKillGoodsDao.queryAll(0, 4);
    }

    @Override
    public SecKillGoods getById(long goodsId) {
        return secKillGoodsDao.queryById(goodsId);
    }

    @Override
    public Exposer exportSeckillUrl(long goodsId) {
        SecKillGoods secKillGoods = secKillGoodsDao.queryById(goodsId);
        if (secKillGoods == null) {
            return new Exposer(false, goodsId);
        }
        Date startTime = secKillGoods.getStartTime();
        Date endTime = secKillGoods.getEndTime();
        Date nowTime = new Date();
        if (nowTime.getTime() > endTime.getTime() || nowTime.getTime() < startTime.getTime()) {
            return new Exposer(false, goodsId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        String md5 = getMD5(goodsId);
        return new Exposer(true, md5, goodsId);
    }

    @Override
    @Transactional(rollbackFor = {SecKillException.class})
    public SecKillExecution executeSeckill(long goodsId, long userPhone, String md5) throws SecKillException {
        if (md5 == null || !md5.equals(getMD5(goodsId))) {
            //验证数据被篡改
            throw new SecKillException("验证数据被篡改");
        }
        //执行秒杀逻辑：减库存+记录购买行为
        Date nowTime = new Date();
        int updateCount = secKillGoodsDao.reduceQuantity(goodsId, nowTime);
        if (updateCount <= 0) {
            //没有更新记录   （库存没有或者时间不在秒杀范围)
            throw new SecKillCloseException("库存为0或者不在秒杀时间范围内");
        } else {
            int insertCount = successSecKillDao.insertSuccessKilled(goodsId, userPhone);
            if (insertCount <= 0) {
                //重复秒杀
                throw new RepeatKillException("用户:" + userPhone + " 重复秒杀商品，商品ID：" + goodsId);
            } else {
                //秒杀成功
                SuccessSecKilled successSecKilled = successSecKillDao.queryByGoodsIdAndUserPhone(goodsId, userPhone);
                return new SecKillExecution(goodsId, SecKillStatEnum.SUCCESS, successSecKilled);
            }
        }
    }

    private String getMD5(long goodsId) {
        String base = goodsId + "/" + slat;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
