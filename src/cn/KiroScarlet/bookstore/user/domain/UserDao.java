package cn.KiroScarlet.bookstore.user.domain;

import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;

/**
 * @author KiroScarlet
 * @date 2019-03-25  -21:22
 * User持久层
 */
public class UserDao {
    private QueryRunner qr = new TxQueryRunner();
}
