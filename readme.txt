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
流程：/jsps/user/regist.jsp->UserServlet#regist()->msg.jsp
页面：
    regist.jsp
        表单页面，请求UserServlet#regist()方法
        参数：整个表单数据
    msg.jsp
Servlet:
    UserServlet#regist()
        一键封装表单数据到User form对象
        补全：uid、激活码
        输入校验：校验失败时：
            保存错误信息到request
            保存当前表单数据（form）到request（回显）
            转发回到regist.jsp
        调用service的regist（）方法，传递form过去
            如果抛出异常：保存错误信息（异常信息）、保存表单数据（回显）、转发到regist.jsp
            如果没有抛出异常：
                发邮件（发件人、收件人、标题、内容），内容中包含超链接，超链接指向可完成激活的Servlet地址
                保存成功信息到request
                转发到msg.jsp
Service:
    UserService#regist(User form)
        校验用户名是否重复，如果重复，抛出UserException
        校验邮箱是否被注册，如果被注册，抛出UserException
        把User插入到数据库中
Dao:
    User findByUsername（String username）：按用户名查询用户
    User findByEmail（String email）：按email查询用户
    void add（User form）：插入用户到数据库中


3.用户激活
用户邮件：点击超链接
Servlet：
    UserServlet#active()
        获取参数：激活码
        使用激活码调用service#active(String code)方法
            出错时保存异常信息到request
            转发到msg.jsp
        保存激活成功信息到request
        转发到msg.jsp
Service：
    UserService#active(String code)
        使用code查询数据库，得到User对象
            如果返回null，抛出异常
        查看用户状态
            true：抛出异常
            false：修改用户状态为true
Dao：
    UserDao
        User findByCode(String code)
        void updateState(String uid,boolean state)

页面：
    msg.jsp
由于没有写发送邮件的功能，测试激活的话就手动填地址栏吧。code值为数据库中查询的值
http://localhost:8080/UserServlet?method=active&code=22E218A806D14E1B99888170644D358AE548C5BB627041338A6C96676D119E7C

4.用户登录
流程：login.jsp->UserServelt#login()->index.jsp
Servlet：
    UserServlet#login()
        一键封装（只有用户名和密码）
        调用Service方法完成登录
            失败时保存异常信息、保存form、转发回login.jsp
        保存用户信息到session中
        重定向到index.jsp
Service：
    UserService#login（User form）
        使用username查询数据库，得到user对象
            如果user为null，抛出异常（用户名不存在）
        比较form与user的状态是否相同
            如果不同，抛出密码错误异常
        查看用户状态：未激活状态抛出异常
        返回user
Dao：
    UserDao
        User findByUsername()


