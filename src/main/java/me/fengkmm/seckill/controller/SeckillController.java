package me.fengkmm.seckill.controller;


import lombok.extern.slf4j.Slf4j;
import me.fengkmm.seckill.dto.Exposer;
import me.fengkmm.seckill.dto.SeckillExecution;
import me.fengkmm.seckill.dto.SeckillResult;
import me.fengkmm.seckill.entity.SeckillGoods;
import me.fengkmm.seckill.exception.SecKillException;
import me.fengkmm.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 邻居  2018-3-24 11:05
 */

@Slf4j
@Controller
@RequestMapping("/seckill/")
public class SeckillController {

    private final SeckillService seckillService;


    @GetMapping("/list")
    public String list(Model model) {
        List<SeckillGoods> seckillGoodsList = seckillService.getSeckillList();
        model.addAttribute("seckillList", seckillGoodsList);
        return "list";
    }

    @GetMapping("/{seckillId}/detail")
    public String detail(@PathVariable long seckillId, Model model) {
        SeckillGoods seckillGoods = seckillService.getById(seckillId);
        if (seckillGoods == null) {
            return "redirect:/seckill/list";
        }
        model.addAttribute("seckill", seckillGoods);
        return "detail";
    }

    @PostMapping(value = "/{seckillId}/exposer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") long seckillId) {
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<>(true, exposer);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new SeckillResult<>(false, e.getMessage());
        }
        return result;
    }

    @PostMapping("/{seckillId}/{md5}/execution")
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") long seckillId, @PathVariable("md5") String md5, @CookieValue("userPhone") Long userPhone) {
        if (userPhone == null) {
            return new SeckillResult<>(false, "未注册");
        }
        SeckillResult<SecKillException> result;
        try {
            SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
            return new SeckillResult<>(true, seckillExecution);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            return new SeckillResult<>(true, e.getMessage());
        }
    }

    @GetMapping("/time/now")
    @ResponseBody
    public SeckillResult<Long> time() {
        return new SeckillResult<>(true, System.currentTimeMillis());
    }


    @Autowired
    public SeckillController(SeckillService seckillService) {
        this.seckillService = seckillService;
    }
}
