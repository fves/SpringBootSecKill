DROP DATABASE IF EXISTS seckilldb;
CREATE DATABASE seckilldb;

USE seckilldb;

# 库存表
CREATE TABLE seckill_goods (
  `goods_id`    BIGINT       NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `name`        VARCHAR(120) NOT NULL COMMENT '商品名称',
  `quantity`    INT          NOT NULL COMMENT '库存数量',
  `start_time`  TIMESTAMP    NOT NULL COMMENT '秒杀开始时间',
  `end_time`    TIMESTAMP    NOT NULL COMMENT '秒杀结束时间',
  `create_time` TIMESTAMP    NOT NULL DEFAULT current_timestamp COMMENT '秒杀创建时间',
  PRIMARY KEY (goods_id),
  KEY idx_start_time(start_time),
  KEY idx_end_time(end_time),
  KEY idx_create_time(create_time)
)
  ENGINE INNODB
  AUTO_INCREMENT = 1000
  DEFAULT CHARSET = utf8
  COMMENT '秒杀库存';
# 初始化数据
INSERT INTO
  seckill_goods (name, quantity, start_time, end_time)
  VALUE
  ('1000元秒杀Iphone6', 100, current_timestamp, current_timestamp),
  ('500元秒杀ipad2', 200, current_timestamp, current_timestamp),
  ('300元秒杀小米4', 300, current_timestamp, current_timestamp),
  ('200元秒杀红米note', 500, current_timestamp, current_timestamp);

# 秒杀成功明细表
# 用户登录认证相关信息
CREATE TABLE seckill_success (
  `goods_id`  BIGINT    NOT NULL COMMENT '秒杀商品id',
  `user_phone`  BIGINT    NOT NULL COMMENT '用户手机号',
  `state`       TINYINT   NOT NULL DEFAULT -1 COMMENT '状态表示：-1:表示无效，0:成功，1:已付款，2:已发货',
  `create_time` TIMESTAMP NOT NULL COMMENT '创建时间',
  PRIMARY KEY (goods_id, user_phone),
  KEY idex_create_time(create_time)
)
  ENGINE InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '秒杀成功明细表';





