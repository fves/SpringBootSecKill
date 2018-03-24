package me.fengkmm.seckill.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.fengkmm.seckill.dto.Exposer;
import me.fengkmm.seckill.dto.SeckillExecution;
import me.fengkmm.seckill.entity.SeckillGoods;
import me.fengkmm.seckill.exception.SecKillException;
import me.fengkmm.seckill.service.SeckillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SeckillGoodsServiceImplTest {

    @Autowired
    SeckillService seckillService;

    @Test
    public void getSeckillList() {
        List<SeckillGoods> seckillGoodsList = seckillService.getSeckillList();
        log.info(seckillGoodsList.toString());
    }

    @Test
    public void getById() {
        SeckillGoods byId = seckillService.getById(1000);
        System.out.println(byId);
    }

    @Test
    public void seckillLogic() throws InterruptedException {
        long seckillId = 1000L;
        long userPhone = 15279278673L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed()) {
            String md5 = exposer.getMd5();
            try {
                SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
            } catch (SecKillException ignored) {

            }
        }
    }

}