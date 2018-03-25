package me.fengkmm.seckill.mapper;

import me.fengkmm.seckill.entity.SecKillGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

/**
 * 秒杀商品接口
 * @author Administrator
 */
@Mapper
@Repository
public interface SecKillGoodsDao {
    /**
     * 减库存
     * @param goodsId 秒杀id
     * @param secKillTime 秒杀时间
     * @return 如果影响行数>1，表示成功修改的行数
     */
    int reduceQuantity(@Param("goodsId") long goodsId, @Param("secKillTime") Date secKillTime);

    /**
     *根据ID查询秒杀接口
     * @param goodsId 秒杀id
     * @return 通过id查询的秒杀商品
     */
    SecKillGoods queryById(long goodsId);

    /**
     * 根据偏移量查询秒杀商品列表
     * @param offet 查询偏移量
     * @param limit 查询限制
     * @return 查询结果
     */
    List<SecKillGoods> queryAll(@Param("offet") int offet, @Param("limit") int limit);
}
