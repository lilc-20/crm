package cn.edu.hzau.crm.settings.web.controller;

import cn.edu.hzau.crm.settings.domain.User;
import cn.edu.hzau.crm.settings.exception.LoginException;
import cn.edu.hzau.crm.settings.service.UserService;
import cn.edu.hzau.crm.settings.service.impl.UserServiceImpl;
import cn.edu.hzau.crm.utils.MD5Util;
import cn.edu.hzau.crm.utils.PrintJson;
import cn.edu.hzau.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String servletPath = request.getServletPath();

        if ("/settings/user/login.do".equals(servletPath)){
            login(request, response);
        }

        if ("/settings/user/logout.do".equals(servletPath)){
            logout(request, response);
        }

    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("user");

        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    private void login(HttpServletRequest request, HttpServletResponse response) {
        //验证码
        String token = (String) request.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        request.getSession().removeAttribute(KAPTCHA_SESSION_KEY);
        String kaptcha = request.getParameter("kaptcha");
        if (token != null && token.equals(kaptcha)){
            String ip = request.getRemoteAddr();
            String loginAct = request.getParameter("loginAct");
            String loginPwd = request.getParameter("loginPwd");
            loginPwd = MD5Util.getMD5(loginPwd);

            UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
            try {
                User user = userService.login(loginAct, loginPwd, ip);
                request.getSession().setAttribute("user", user);
                PrintJson.printJsonFlag(response, true);
            } catch (LoginException e) {
                e.printStackTrace();
                String msg = e.getMessage();
                Map map = new HashMap();
                map.put("flag", false);
                map.put("msg", msg);
                PrintJson.printJsonObj(response, map);
            }
        }else {
            Map map = new HashMap();
            map.put("flag", false);
            map.put("msg", "验证码错误");
            PrintJson.printJsonObj(response, map);
        }
    }
}
