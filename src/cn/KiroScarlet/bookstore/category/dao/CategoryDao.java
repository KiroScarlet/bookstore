package cn.KiroScarlet.bookstore.category.dao;

import cn.KiroScarlet.bookstore.category.domain.Category;
import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author KiroScarlet
 * @date 2019-03-26  -12:48
 */
public class CategoryDao {
    private QueryRunner qr = new TxQueryRunner();

    public List<Category> findAll() {
        String sql = "select * from category";
        try {
            return qr.query(sql, new BeanListHandler<Category>(Category.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
