# Java高并发小型秒杀系统
### [页面展示]()

## 开发工具
Intellij IDEA + Navicat Premium

## 开发环境
SpringBoot + Mybatis + Redis+ Thymeleaf + Maven

##  开发目的
熟悉并掌握一个项目的开发流程，练习已学习的相关技术，了解相关技术的内部实现原理。

在慕课网看到了Java高并发系列课程，并学习相关内容
慕课老师使用的开发工具是: Eclipse  
开发环境是：Tomcat + Spring + SpringMVC + Mybatis + Redis + Maven

[慕课网(Java高并发系列程)](http://www.imooc.com/u/2145618/courses?sort=publish)

该课程分四门课程
1. [Java高并发秒杀API之业务分析与DAO层](http://www.imooc.com/learn/587)
2. [Java高并发秒杀API之web层](http://www.imooc.com/learn/630)
3. [Java高并发秒杀API之Service层](http://www.imooc.com/learn/631)
4. [Java高并发秒杀API之高并发优化](http://www.imooc.com/learn/632)
    
学习完整个系列课程，并用SpringBoot等实现(内容有所修改)。

## 整体流程

<center>__页面流程__</center>
![URL设计](/src/main/resources/流程/页面流程.png)

<center>__详情页流程逻辑__</center>
![URL设计](/src/main/resources/流程/详情页流程逻辑.png)

<center>__URL设计__</center>
![URL设计](/src/main/resources/流程/URL设计.png)


## 数据库
秒杀商品表
```mysql
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
```
秒杀成功表
```mysql
CREATE TABLE seckill_success (
  `goods_id`  BIGINT    NOT NULL COMMENT '秒杀商品id',
  `user_phone`  BIGINT    NOT NULL COMMENT '用户手机号',
  `state`       TINYINT   NOT NULL DEFAULT -1 COMMENT '状态表示：-1:表示无效，0:成功，1:已付款，2:已发货',
  `create_time` TIMESTAMP NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
  PRIMARY KEY (goods_id, user_phone),
  KEY idex_create_time(create_time)
)
  ENGINE InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '秒杀成功明细表';
```
