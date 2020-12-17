package cn.edu.hzau.crm.workbench.web.controller;

import cn.edu.hzau.crm.settings.domain.User;
import cn.edu.hzau.crm.utils.DateTimeUtil;
import cn.edu.hzau.crm.utils.PrintJson;
import cn.edu.hzau.crm.utils.ServiceFactory;
import cn.edu.hzau.crm.utils.UUIDUtil;
import cn.edu.hzau.crm.workbench.domain.Activity;
import cn.edu.hzau.crm.workbench.domain.Clue;
import cn.edu.hzau.crm.workbench.domain.ClueActivityRelation;
import cn.edu.hzau.crm.workbench.domain.Tran;
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
            getUsers(request, response);
        }

        if ("/workbench/clue/save.do".equals(servletPath)){
            save(request, response);
        }

        if ("/workbench/clue/detail.do".equals(servletPath)){
            detail(request, response);
        }

        if ("/workbench/clue/activity.do".equals(servletPath)){
            activity(request, response);
        }

        if ("/workbench/clue/deleteRelation.do".equals(servletPath)){
            deleteRelation(request, response);
        }

        if ("/workbench/clue/relationList.do".equals(servletPath)){
            relationList(request, response);
        }

        if ("/workbench/clue/addRelation.do".equals(servletPath)){
            addRelation(request, response);
        }

        if ("/workbench/clue/searchWithName.do".equals(servletPath)){
            searchWithName(request, response);
        }

        if ("/workbench/clue/convert.do".equals(servletPath)){
            convert(request, response);
        }

    }

    private void convert(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String clueId = request.getParameter("clueId");
        String flag = request.getParameter("flag");

        Tran tran = null;
        String createBy = ((User) request.getSession().getAttribute("user")).getName();

        if ("checked".equals(flag)){
            tran = new Tran();
            String money = request.getParameter("money");
            String name = request.getParameter("name");
            String expectedDate = request.getParameter("expectedDate");
            String stage = request.getParameter("stage");
            String activityId = request.getParameter("activityId");
            String id = UUIDUtil.getUUID();
            String createTime = DateTimeUtil.getSysTime();

            tran.setMoney(money);
            tran.setName(name);
            tran.setExpectedDate(expectedDate);
            tran.setStage(stage);
            tran.setActivityId(activityId);
            tran.setId(id);
            tran.setCreateTime(createTime);
            tran.setCreateBy(createBy);
        }

        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean success = clueService.convert(clueId, tran, createBy);

        if (success){
            response.sendRedirect(request.getContextPath() + "/workbench/clue/index.jsp?");
        }
    }

    private void searchWithName(HttpServletRequest request, HttpServletResponse response) {
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        String clueId = request.getParameter("clueId");
        String name = request.getParameter("name");

        List<Activity> activities = clueService.searchWithName(clueId, name);

        PrintJson.printJsonObj(response, activities);
    }

    private void addRelation(HttpServletRequest request, HttpServletResponse response) {
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        String clueId = request.getParameter("clueId");
        String[] ids = request.getParameterValues("id");

        boolean success = clueService.addRelation(clueId, ids);

        PrintJson.printJsonFlag(response, success);
    }

    private void relationList(HttpServletRequest request, HttpServletResponse response) {
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        String id = request.getParameter("id");
        String name = request.getParameter("name");

        List<Activity> activities = clueService.relationList(id, name);

        PrintJson.printJsonObj(response, activities);
    }

    private void deleteRelation(HttpServletRequest request, HttpServletResponse response) {
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        String id = request.getParameter("id");
        String clueId = request.getParameter("clueId");
        ClueActivityRelation relation = new ClueActivityRelation();
        relation.setActivityId(id);
        relation.setClueId(clueId);

        boolean success = clueService.deleteRelation(relation);

        PrintJson.printJsonFlag(response, success);
    }

    private void activity(HttpServletRequest request, HttpServletResponse response) {
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        String id = request.getParameter("id");

        List<Activity> activities = clueService.selectByClueId(id);

        PrintJson.printJsonObj(response, activities);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        String id = request.getParameter("id");

        Clue clue = clueService.selectById(id);

        request.setAttribute("clue", clue);
        request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request, response);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        String id = UUIDUtil.getUUID();
        String fullname = request.getParameter("fullname");
        String appellation = request.getParameter("appellation");
        String owner = request.getParameter("owner");
        String company = request.getParameter("company");
        String job = request.getParameter("job");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String website = request.getParameter("website");
        String mphone = request.getParameter("mphone");
        String state = request.getParameter("state");
        String source = request.getParameter("source");
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        String address = request.getParameter("address");

        Clue clue = new Clue();
        clue.setId(id);
        clue.setFullname(fullname);
        clue.setAppellation(appellation);
        clue.setOwner(owner);
        clue.setCompany(company);
        clue.setJob(job);
        clue.setEmail(email);
        clue.setPhone(phone);
        clue.setWebsite(website);
        clue.setMphone(mphone);
        clue.setState(state);
        clue.setSource(source);
        clue.setCreateBy(createBy);
        clue.setCreateTime(createTime);
        clue.setDescription(description);
        clue.setContactSummary(contactSummary);
        clue.setNextContactTime(nextContactTime);
        clue.setAddress(address);
        boolean success = clueService.addClue(clue);

        PrintJson.printJsonFlag(response, success);
    }

    private void getUsers(HttpServletRequest request, HttpServletResponse response) {
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        List<User> users = clueService.selectUsers();

        PrintJson.printJsonObj(response, users);
    }
}
