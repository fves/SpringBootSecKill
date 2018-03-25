package me.fengkmm.seckill.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecKillGoodsDaoTest {
    @Autowired
    private SecKillGoodsDao secKillGoodsDao;

    @Test
    public void reduceNumber() {
        System.out.println(secKillGoodsDao.reduceQuantity(1000, new Date()));
    }

    @Test
    public void queryById() {
        System.out.println(secKillGoodsDao.queryById(1000));
    }

    @Test
    public void queryAll() {
        System.out.println(secKillGoodsDao.queryAll(0, 3));
    }
}