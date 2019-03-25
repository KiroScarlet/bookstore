package cn.KiroScarlet.bookstore.user.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.KiroScarlet.bookstore.user.domain.User;
import cn.KiroScarlet.bookstore.user.service.UserException;
import cn.KiroScarlet.bookstore.user.service.UserService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

/**
 * @author KiroScarlet
 * @date 2019-03-25  -21:25
 * User表述层
 */
@WebServlet(name = "UserServlet",urlPatterns = "/UserServlet")
public class UserServlet extends BaseServlet{
    private UserService userService = new UserService();

    public String regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User form = CommonUtils.toBean(request.getParameterMap(), User.class);
        form.setUid(CommonUtils.uuid());
        form.setCode(CommonUtils.uuid() + CommonUtils.uuid());

        //输入校验
        Map<String, String> errors = new HashMap<>();
        String username = form.getUsername();
        if (username == null || username.trim().isEmpty()) {
            errors.put("username", "用户名不能为空！");
        } else if (username.length() < 3 || username.length() > 10) {
            errors.put("username", "用户名长度必须在3~10之间！");
        }

        String password = form.getPassword();
        if (password == null || password.trim().isEmpty()) {
            errors.put("password", "密码不能为空！");
        } else if (password.length() < 3 || password.length() > 10) {
            errors.put("password", "密码长度必须在3~10之间！");
        }

        String email = form.getEmail();
        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "email不能为空！");
        } else if (!email.matches("\\w+@\\w+\\.\\w+")) {
            errors.put("email", "email格式错误！");
        }

        //判断是否存在错误信息
        if (errors.size() > 0) {
            request.setAttribute("errors", errors);
            request.setAttribute("form",form);
            return "/jsps/user/regist.jsp";
        }

        try {
            userService.regist(form);

        } catch (UserException e) {
            //保存异常信息，保存form，转发到regist.jsp
            request.setAttribute("msg", e.getMessage());
            request.setAttribute("form", form);
            return "/jsps/user/regist.jsp";
        }

        //发邮件
        //功能太复杂。暂时不写了



        //保存成功信息，转达到msg.jsp
        request.setAttribute("msg", "恭喜，注册成功！请马上到邮箱激活");
        return "jsps/msg.jsp";
    }
}
