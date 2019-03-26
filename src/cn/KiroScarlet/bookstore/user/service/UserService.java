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

    //激活功能
    public void active(String code) throws UserException {
        User user = userDao.findByCode(code);

        if (user == null) {
            throw new UserException("激活码无效");
        }
        if (user.isState()) {
            throw new UserException("您已激活，不要再次激活");
        }

        userDao.updateState(user.getUid(),true);
    }

    //登录
    public User login(User form) throws UserException {
        User user = userDao.findByUsername(form.getUsername());
        if (user == null) {
            throw new UserException("用户名不存在");
        }
        if (!user.getPassword().equals(form.getPassword())) {
            throw new UserException("密码错误");
        }
        if (!user.isState()) {
            throw new UserException("尚未激活");
        }
        return user;
    }
}
