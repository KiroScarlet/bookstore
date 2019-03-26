package cn.KiroScarlet.bookstore.book.web.servlet;

import cn.KiroScarlet.bookstore.book.service.BookService;
import cn.itcast.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author KiroScarlet
 * @date 2019-03-26  -13:26
 */
@WebServlet(name = "BookServlet",urlPatterns = "/BookServlet")
public class BookServlet extends BaseServlet {
    private BookService bookService = new BookService();

    public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("bookList", bookService.findAll());
        return "f:/jsps/book/list.jsp";
    }

    public String findByCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        request.setAttribute("bookList", bookService.findByCategory(cid));
        return "f:/jsps/book/list.jsp";
    }

    public String load(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String bid = request.getParameter("bid");

        request.setAttribute("book", bookService.load(bid));
        return "f:/jsps/book/desc.jsp";
    }


}
