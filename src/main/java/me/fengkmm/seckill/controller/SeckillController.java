package me.fengkmm.seckill.controller;


import lombok.extern.slf4j.Slf4j;
import me.fengkmm.seckill.dto.Exposer;
import me.fengkmm.seckill.dto.SecKillExecution;
import me.fengkmm.seckill.dto.SecKillResult;
import me.fengkmm.seckill.entity.SecKillGoods;
import me.fengkmm.seckill.exception.RepeatKillException;
import me.fengkmm.seckill.exception.SecKillCloseException;
import me.fengkmm.seckill.exception.SecKillException;
import me.fengkmm.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 邻居  2018-3-24 11:05
 */

@Slf4j
@Validated
@Controller
@RequestMapping("/seckill/")
public class SeckillController {

    private final SeckillService seckillService;


    @GetMapping("/list")
    public String list(Model model) {
        List<SecKillGoods> secKillGoodsList = seckillService.getSeckillList();
        model.addAttribute("seckillGoodsList", secKillGoodsList);
        return "GoodsList";
    }

    @GetMapping("/{goodsId}/detail")
    public String detail(@PathVariable long goodsId, Model model) {
        SecKillGoods secKillGoods = seckillService.getById(goodsId);
        if (secKillGoods == null) {
            return "redirect:/seckill/list";
        }
        model.addAttribute("secKillGoods", secKillGoods);
        return "GoodsDetail";
    }

    @PostMapping(value = "/{goodsId}/exposer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public SecKillResult<Exposer> exposer(@PathVariable("goodsId") long goodsId) {
        Exposer exposer = seckillService.exportSeckillUrl(goodsId);
        return new SecKillResult<>(true, exposer);
    }

    @PostMapping("/{goodsId}/execution")
    @ResponseBody
    public SecKillResult<SecKillExecution> execute(
            @PathVariable("goodsId") long goodsId,
            @RequestParam(value = "md5",required = false) String md5,
            @CookieValue(value = "userPhone",required = false) Long userPhone) {
        if (userPhone == null) {
            return new SecKillResult<>(false, "手机号码为空");
        }
        if (md5 == null) {
            return new SecKillResult<>(false, "验证参数缺失，秒杀无效");
        }
        SecKillExecution secKillExecution = null;
        try {
            secKillExecution = seckillService.executeSeckill(goodsId, userPhone, md5);
            return new SecKillResult<>(true, secKillExecution);
        } catch (SecKillException e) {
            return new SecKillResult<>(false, e.getMessage());
        }
    }

    @GetMapping("/time/now")
    @ResponseBody
    public SecKillResult<Long> time() {
        return new SecKillResult<>(true, System.currentTimeMillis());
    }


    @Autowired
    public SeckillController(SeckillService seckillService) {
        this.seckillService = seckillService;
    }
}
