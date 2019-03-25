package cn.KiroScarlet.bookstore.user.service;

import cn.KiroScarlet.bookstore.user.dao.UserDao;
import cn.KiroScarlet.bookstore.user.domain.User;

/**
 * @author KiroScarlet
 * @date 2019-03-25  -21:23
 * User业务层
 */
public class UserService {
    private UserDao userDao = new UserDao();

//    注册功能
    public void regist(User form) throws UserException{
        User user = userDao.findByUsername(form.getUsername());
        if (user != null) {
            throw new UserException("用户名已注册！");
        }

        user = userDao.findByEmail(form.getEmail());
        if (user != null) {
            throw new UserException("Email已注册！");
        }

        //插入用户到数据库
        userDao.add(form);
    }
}
