package cn.KiroScarlet.bookstore.book.dao;

import cn.KiroScarlet.bookstore.book.domain.Book;
import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author KiroScarlet
 * @date 2019-03-26  -13:27
 */
public class BookDao {
    private QueryRunner qr = new TxQueryRunner();

    public List<Book> findAll() {
        String sql = "select * from book";
        try {
            return qr.query(sql, new BeanListHandler<>(Book.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //按分类查询
    public List<Book> findByCategory(String cid) {
        String sql = "select * from book where cid=?";
        try {
            return qr.query(sql, new BeanListHandler<>(Book.class),cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Book findByBid(String bid) {
        String sql = "select * from book where bid=?";
        try {
            return qr.query(sql, new BeanHandler<>(Book.class),bid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
