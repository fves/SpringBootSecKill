package me.fengkmm.seckill.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecKillGoodsDaoTest {
    @Autowired
    private SecKillGoodsDao secKillGoodsDao;

    @Test
    public void reduceNumber() {
        System.out.println(secKillGoodsDao.reduceNumber(1000, new Date()));
    }

    @Test
    public void queryById() {
        System.out.println(secKillGoodsDao.queryById(1000));
    }

    @Test
    public void queryAll() {
        System.out.println(secKillGoodsDao.queryAll(0, 3));
    }

    @Test
    public void queryMap() {
        HashMap hashMap = secKillGoodsDao.queryMap(1000);
        hashMap.forEach((x,y)->{
            System.out.println(x);
            System.out.println(y);
        });
    }
}