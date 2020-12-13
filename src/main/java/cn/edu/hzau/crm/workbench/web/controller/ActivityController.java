package cn.edu.hzau.crm.workbench.web.controller;

import cn.edu.hzau.crm.settings.domain.User;
import cn.edu.hzau.crm.settings.service.UserService;
import cn.edu.hzau.crm.settings.service.impl.ActivityServiceImpl;
import cn.edu.hzau.crm.settings.service.impl.UserServiceImpl;
import cn.edu.hzau.crm.utils.DateTimeUtil;
import cn.edu.hzau.crm.utils.PrintJson;
import cn.edu.hzau.crm.utils.ServiceFactory;
import cn.edu.hzau.crm.utils.UUIDUtil;
import cn.edu.hzau.crm.workbench.domain.Activity;
import cn.edu.hzau.crm.workbench.service.ActivityService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();

        if ("/workbench/activity/selectUsers.do".equals(servletPath)){
            selectUsers(request, response);
        }

        if ("/workbench/activity/save.do".equals(servletPath)){
            save(request, response);
        }
    }

    private void selectUsers(HttpServletRequest request, HttpServletResponse response) {
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> users = userService.selectUsers();
        PrintJson.printJsonObj(response, users);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User) request.getSession().getAttribute("user")).getName();

        Activity activity = new Activity();

        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);

        boolean success = activityService.addActivity(activity);
        PrintJson.printJsonFlag(response, success);
    }
}
