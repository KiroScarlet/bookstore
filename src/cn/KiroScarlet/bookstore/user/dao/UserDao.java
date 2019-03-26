package cn.KiroScarlet.bookstore.user.dao;

import cn.KiroScarlet.bookstore.user.domain.User;
import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

/**
 * @author KiroScarlet
 * @date 2019-03-25  -21:22
 * User持久层
 */
public class UserDao {
    private QueryRunner qr = new TxQueryRunner();

    public User findByUsername(String username) {
        String sql = "select * from tb_user where username=?";
        try {
            return qr.query(sql,new BeanHandler<User>(User.class),username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findByEmail(String email) {
        String sql = "select * from tb_user where email=?";
        try {
            return qr.query(sql,new BeanHandler<User>(User.class),email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(User user) {
        String sql = "insert into tb_user values(?,?,?,?,?,?)";
        try {
            Object[] params = {user.getUid(), user.getUsername(), user.getPassword(),
                    user.getEmail(), user.getCode(), user.isState()};
            qr.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //按激活码查询
    public User findByCode(String code) {
        String sql = "select * from tb_user where code=?";
        try {
            return qr.query(sql,new BeanHandler<User>(User.class),code);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //修改指定用户的指定状态
    public void updateState(String uid,boolean state) {
        String sql = "update tb_user set state=? where uid=?";
        try {
            qr.update(sql, state, uid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
