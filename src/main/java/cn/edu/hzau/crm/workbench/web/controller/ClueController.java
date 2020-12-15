package cn.edu.hzau.crm.workbench.web.controller;

import cn.edu.hzau.crm.settings.domain.User;
import cn.edu.hzau.crm.utils.PrintJson;
import cn.edu.hzau.crm.utils.ServiceFactory;
import cn.edu.hzau.crm.workbench.service.ClueService;
import cn.edu.hzau.crm.workbench.service.impl.ClueServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ClueController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();

        if ("/workbench/clue/getUsers.do".equals(servletPath)){
            ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

            List<User> users = clueService.selectUsers();

            PrintJson.printJsonObj(response, users);
        }

    }
}
