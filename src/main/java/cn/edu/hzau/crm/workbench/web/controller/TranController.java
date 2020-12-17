package cn.edu.hzau.crm.workbench.web.controller;

import cn.edu.hzau.crm.settings.domain.User;
import cn.edu.hzau.crm.settings.service.UserService;
import cn.edu.hzau.crm.settings.service.impl.UserServiceImpl;
import cn.edu.hzau.crm.utils.DateTimeUtil;
import cn.edu.hzau.crm.utils.PrintJson;
import cn.edu.hzau.crm.utils.ServiceFactory;
import cn.edu.hzau.crm.utils.UUIDUtil;
import cn.edu.hzau.crm.workbench.domain.Activity;
import cn.edu.hzau.crm.workbench.domain.Clue;
import cn.edu.hzau.crm.workbench.domain.ClueActivityRelation;
import cn.edu.hzau.crm.workbench.domain.Tran;
import cn.edu.hzau.crm.workbench.service.ClueService;
import cn.edu.hzau.crm.workbench.service.TranService;
import cn.edu.hzau.crm.workbench.service.impl.ClueServiceImpl;
import cn.edu.hzau.crm.workbench.service.impl.TranServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TranController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();

        if ("/workbench/transaction/users.do".equals(servletPath)){
            getUsers(request, response);
        }
    }

    private void getUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> users = userService.selectUsers();

        request.setAttribute("users", users);
        request.getRequestDispatcher("/workbench/transaction/save.jsp").forward(request, response);
    }
}