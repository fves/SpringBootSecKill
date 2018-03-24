package me.fengkmm.seckill.mapper;

import me.fengkmm.seckill.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SuccessKillDaoTest {

    @Resource
    private SuccessKillDao successKillDao;

    @Test
    public void insertSuccessKulled() {
        int i = successKillDao.insertSuccessKulled(1000, -15279278673L);
        System.out.println(i);
    }

    @Test
    public void queryByIdWithSeckill() {
        SuccessKilled successKilled = successKillDao.queryByIdWithSeckill(1000,15279278673L);
        System.out.println(successKilled);
    }
}