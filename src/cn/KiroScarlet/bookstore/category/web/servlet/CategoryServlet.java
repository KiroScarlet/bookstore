package cn.KiroScarlet.bookstore.category.web.servlet;

import cn.KiroScarlet.bookstore.category.service.CategoryService;
import cn.itcast.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author KiroScarlet
 * @date 2019-03-26  -12:57
 */
@WebServlet(name = "CategoryServlet",urlPatterns = "/CategoryServlet")
public class CategoryServlet extends BaseServlet {

    private CategoryService categoryService = new CategoryService();

    public String findAll(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setAttribute("categoryList",categoryService.findAll());

        return "f:/jsps/left.jsp";
    }


}
