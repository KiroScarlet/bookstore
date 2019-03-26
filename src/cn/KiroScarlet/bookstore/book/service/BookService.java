package cn.KiroScarlet.bookstore.book.service;

import cn.KiroScarlet.bookstore.book.dao.BookDao;
import cn.KiroScarlet.bookstore.book.domain.Book;

import java.util.List;

/**
 * @author KiroScarlet
 * @date 2019-03-26  -13:28
 */
public class BookService {
    private BookDao bookDao = new BookDao();

    public List<Book> findAll() {
        return bookDao.findAll();
    }

    public List<Book> findByCategory(String cid) {
        return bookDao.findByCategory(cid);
    }

    public Book load(String bid) {
        return bookDao.findByBid(bid);
    }
}
