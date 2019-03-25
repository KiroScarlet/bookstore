最近把JavaWeb的基础知识重新过了一遍，寻思着写个小项目练习一下
网上找了个图书商城的小项目，从头到尾跟着写一下吧
####1.环境搭建
IDEA+Tomcat+MySQL

直接IDEA新建一个Web项目，具体就不讲了，网上都有
####2.功能分析
 前台
* 用户模块：
  * 注册
  * 激活
  * 登录
  * 退出
* 分类模块：
  * 查看所有分类
* 图书模块：
  * 查询所有图书
  * 按分类查询图书
  * 查询图书详细（按id查）
* 购物车模块：
  * 添加购物车条目；
  * 清空所有条目；
  * 删除指定条目；
  * 我的购物车（按用户查询购物车）
* 订单模块：
  * 生成订单；
  * 我的订单（按用户查询订单）
  * 按id查询订单
  * 确认收货
  * 付款功能（只是跳转到银行页面）
  * 付款回调功能（由银行来调用我们这个方法，表示用户已经付款成功）
后台：

* 管理员：
  * 登录
* 分类管理：
  * 添加分类
  * 查看所有分类
  * 删除分类
  * 按id查询
  * 修改分类
* 图书管理（我的）
  * 查看所有图书
  * 按id查询
  * 删除图书
  * 修改图书
  * 添加图书（上传图片）
* 订单模块
  * 查询所有订单
  * 按状态查询订单
  * 发货

####3.框架的搭建

3.1 导包
* 数据库
  * mysql驱动
  * c3p0
  * dbutils
* javamail
  * mail.jar
  * activation.jar
* 上传
  * commons-fileupload
  * commons-io
* ajax
  * json-lib


3.2 创建Package
* 根：cn.KiroScarlet.bookstore
  * user
  * category
  * book
  * cart
  * order

3.3 创建表

这个SQL语句是别人直接写好的，套进去就行了，再加上点自己的东西就完事了，不过原版教程里面有错误，我稍微修改了一下

MySQL管理工具我用的是SQLyog，实在不行可以手动一个一个表创建呗（笑）

CREATE DATABASE bookstore;

/*用户表*/
CREATE TABLE tb_user(
  uid CHAR(32) PRIMARY KEY,/*主键*/
  username VARCHAR(50) NOT NULL,/*用户名*/
  `password` VARCHAR(50) NOT NULL,/*密码*/
  email VARCHAR(50) NOT NULL,/*邮箱*/
  `code` CHAR(64) NOT NULL,/*激活码*/
  state BOOLEAN/*用户状态，有两种是否激活*/
);

SELECT * FROM tb_user;

/*分类*/
CREATE TABLE category (
  cid CHAR(32) PRIMARY KEY,/*主键*/
  cname VARCHAR(100) NOT NULL/*分类名称*/
);

INSERT  INTO category(cid,cname) VALUES ('1','JavaSE');
INSERT  INTO category(cid,cname) VALUES ('2','JavaEE');
INSERT  INTO category(cid,cname) VALUES ('3','Javascript');

SELECT * FROM category;

/*图书表*/
CREATE TABLE book (
  bid CHAR(32) PRIMARY KEY,/*主键*/
  bname VARCHAR(100),/*图书名*/
  price DECIMAL(5,1),/*单价*/
  author VARCHAR(20),/*作者*/
  image VARCHAR(200),/*图片*/
  cid CHAR(32),/*所属分类*/
  FOREIGN KEY (cid) REFERENCES category(cid)/*建立主外键关系*/
);

INSERT  INTO book VALUES ('1','Java编程思想（第4版）','75.6','qdmmy6','book_img/9317290-1_l.jpg','1');
INSERT  INTO book VALUES ('2','Java核心技术卷1','68.5','qdmmy6','book_img/20285763-1_l.jpg','1');
INSERT  INTO book VALUES ('3','Java就业培训教程','39.9','张孝祥','book_img/8758723-1_l.jpg','1');
INSERT  INTO book VALUES ('4','Head First java','47.5','（美）塞若','book_img/9265169-1_l.jpg','1');
INSERT  INTO book VALUES ('5','JavaWeb开发详解','83.3','孙鑫','book_img/22788412-1_l.jpg','2');
INSERT  INTO book VALUES ('6','Struts2深入详解','63.2','孙鑫','book_img/20385925-1_l.jpg','2');
INSERT  INTO book VALUES ('7','精通Hibernate','30.0','孙卫琴','book_img/8991366-1_l.jpg','2');
INSERT  INTO book VALUES ('8','精通Spring2.x','63.2','陈华雄','book_img/20029394-1_l.jpg','2');
INSERT  INTO book VALUES ('9','Javascript权威指南','93.6','（美）弗兰纳根','book_img/22722790-1_l.jpg','3');

SELECT * FROM book;

/*订单表*/
CREATE TABLE orders (
  oid CHAR(32) PRIMARY KEY,/*主键*/
  ordertime DATETIME,/*订单生成时间*/
  total DECIMAL(10,0),/*订单合计*/
  state SMALLINT(1),/*订单状态：未付款、已付款但未发货、已发货但未确认收货、收货已结束*/
  uid CHAR(32),/*订单的主人*/
  address VARCHAR(200),/*订单的收货地址*/
  FOREIGN KEY (uid) REFERENCES tb_user(uid)/*建立主外键关系*/
);

SELECT * FROM orders;

/*订单项表*/
CREATE TABLE orderitem (
  iid CHAR(32) PRIMARY KEY,/*主键*/
  `count` INT,/*数量*/
  subtotal DECIMAL(10,0),/*小计*/
  oid CHAR(32),/*所属订单*/
  bid CHAR(32),/*订单项所指的商品*/
  FOREIGN KEY (oid) REFERENCES orders (oid),/*建立主外键关系*/
  FOREIGN KEY (bid) REFERENCES book (bid)/*建立主外键关系*/
);

SELECT * FROM orderitem;

用户模块
1.用户模块相关类创建
domain：user
dao：UserDao
service：UserDao
web.servlet：UserServlet

2.用户注册
流程：