package me.fengkmm.seckill.mapper;

import me.fengkmm.seckill.entity.SuccessSecKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SuccessSecKillDaoTest {

    @Resource
    private SuccessSecKillDao successSecKillDao;

    @Test
    public void insertSuccessKulled() {
        int i = successSecKillDao.insertSuccessKilled(1000L, 15279278673L);
        System.out.println(i);
    }

    @Test
    public void queryByIdWithSeckill() {
        SuccessSecKilled successSecKilled = successSecKillDao.queryByGoodsIdAndUserPhone(1000,12222222222L);
        System.out.println(successSecKilled);
    }
}