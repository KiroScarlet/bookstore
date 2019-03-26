package cn.KiroScarlet.bookstore.category.service;

import cn.KiroScarlet.bookstore.category.dao.CategoryDao;
import cn.KiroScarlet.bookstore.category.domain.Category;

import java.util.List;

/**
 * @author KiroScarlet
 * @date 2019-03-26  -12:48
 */
public class CategoryService {
    private CategoryDao categoryDao = new CategoryDao();

    public List<Category> findAll() {
        return categoryDao.findAll();
    }
}
