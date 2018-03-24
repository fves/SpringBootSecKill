package me.fengkmm.seckill.mapper;

import me.fengkmm.seckill.entity.SuccessKilled;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 秒杀成功接口
 * @author Administrator
 */
@Mapper
@Repository
public interface SuccessKillDao {
    /**
     * 插入成功秒杀用户
     * @param goodsId 秒杀id
     * @param userPhone 秒杀成功用户的手机号码
     * @return 插入的行数
     */
    int insertSuccessKilled(@Param("goodsId") long goodsId, @Param("userPhone") long userPhone);

    /**
     * 根据id查询SuccessKilled并携带秒杀产品对象实体
     * @param goodsId 秒杀id
     * @param userPhone 用户手机号码
     * @return 成功秒杀明细
     */
    SuccessKilled queryByGoodsIdAndUserPhone(@Param("goodsId") long goodsId,@Param("userPhone")long userPhone);

}
