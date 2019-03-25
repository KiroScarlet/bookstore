package cn.KiroScarlet.bookstore.user.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import cn.KiroScarlet.bookstore.user.service.UserService;
import cn.itcast.servlet.BaseServlet;

/**
 * @author KiroScarlet
 * @date 2019-03-25  -21:25
 * User表述层
 */
@WebServlet(name = "UserServlet",urlPatterns = "/UserServlet")
public class UserServlet extends BaseServlet{
    private UserService userService = new UserService();
}
