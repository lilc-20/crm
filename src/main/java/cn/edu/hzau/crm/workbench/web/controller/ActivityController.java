package cn.edu.hzau.crm.workbench.web.controller;

import cn.edu.hzau.crm.settings.domain.User;
import cn.edu.hzau.crm.settings.service.UserService;
import cn.edu.hzau.crm.workbench.domain.ActivityRemark;
import cn.edu.hzau.crm.workbench.service.impl.ActivityServiceImpl;
import cn.edu.hzau.crm.settings.service.impl.UserServiceImpl;
import cn.edu.hzau.crm.utils.DateTimeUtil;
import cn.edu.hzau.crm.utils.PrintJson;
import cn.edu.hzau.crm.utils.ServiceFactory;
import cn.edu.hzau.crm.utils.UUIDUtil;
import cn.edu.hzau.crm.vo.Pagination;
import cn.edu.hzau.crm.workbench.domain.Activity;
import cn.edu.hzau.crm.workbench.service.ActivityService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
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

        if ("/workbench/activity/pageList.do".equals(servletPath)){
            pageList(request, response);
        }

        if ("/workbench/activity/delete.do".equals(servletPath)){
            delete(request, response);
        }

        if ("/workbench/activity/edit.do".equals(servletPath)){
            edit(request, response);
        }

        if ("/workbench/activity/update.do".equals(servletPath)){
            update(request, response);
        }

        if ("/workbench/activity/detail.do".equals(servletPath)){
            detail(request, response);
        }

        if ("/workbench/activity/remark.do".equals(servletPath)){
            remark(request, response);
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

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Integer pageNo = Integer.valueOf(request.getParameter("pageNo"));
        Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
        Integer skipCount = (pageNo - 1) * pageSize;
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        HashMap map = new HashMap();
        map.put("skipCount", skipCount);
        map.put("pageSize", pageSize);
        map.put("owner", owner);
        map.put("name", name);
        map.put("startDate", startDate);
        map.put("endDate", endDate);

        Pagination<Activity> pagination = activityService.pageList(map);
        int totalPages = pagination.getTotal() % pageSize == 0 ?
                pagination.getTotal() / pageSize : pagination.getTotal() / pageSize + 1;
        pagination.setTotalPages(totalPages);

        PrintJson.printJsonObj(response, pagination);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        String[] ids = request.getParameterValues("id");

        boolean success = activityService.delete(ids);

        PrintJson.printJsonFlag(response, success);
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) {
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        String id = request.getParameter("id");

        HashMap map = activityService.edit(id);

        PrintJson.printJsonObj(response, map);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        String id = request.getParameter("id");
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User) request.getSession().getAttribute("user")).getName();

        Activity activity = new Activity();

        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setEditTime(editTime);
        activity.setEditBy(editBy);

        boolean success = activityService.update(activity);
        PrintJson.printJsonFlag(response, success);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        String id = request.getParameter("id");

        Activity activity = activityService.detail(id);
        request.setAttribute("activity", activity);

        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request, response);
    }

    private void remark(HttpServletRequest request, HttpServletResponse response) {
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        String id = request.getParameter("id");

        List<ActivityRemark> remarks = activityService.selectRemarks(id);

        PrintJson.printJsonObj(response, remarks);
    }

}
