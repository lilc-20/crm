package cn.edu.hzau.crm.workbench.web.controller;

import cn.edu.hzau.crm.settings.domain.User;
import cn.edu.hzau.crm.settings.service.UserService;
import cn.edu.hzau.crm.settings.service.impl.UserServiceImpl;
import cn.edu.hzau.crm.utils.DateTimeUtil;
import cn.edu.hzau.crm.utils.PrintJson;
import cn.edu.hzau.crm.utils.ServiceFactory;
import cn.edu.hzau.crm.utils.UUIDUtil;
import cn.edu.hzau.crm.workbench.domain.*;
import cn.edu.hzau.crm.workbench.service.ClueService;
import cn.edu.hzau.crm.workbench.service.CustomerService;
import cn.edu.hzau.crm.workbench.service.TranService;
import cn.edu.hzau.crm.workbench.service.impl.ClueServiceImpl;
import cn.edu.hzau.crm.workbench.service.impl.CustomerServiceImpl;
import cn.edu.hzau.crm.workbench.service.impl.TranServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class TranController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();

        if ("/workbench/transaction/users.do".equals(servletPath)){
            getUsers(request, response);
        }

        if ("/workbench/transaction/getCustomerName.do".equals(servletPath)){
            getCustomerName(request, response);
        }

        if ("/workbench/transaction/createTran.do".equals(servletPath)){
            createTran(request, response);
        }

        if ("/workbench/transaction/detail.do".equals(servletPath)){
            detail(request, response);
        }

        if ("/workbench/transaction/selectHisByTranId.do".equals(servletPath)){
            selectHisByTranId(request, response);
        }

        if ("/workbench/transaction/changeStage.do".equals(servletPath)){
            changeStage(request, response);
        }
    }

    private void changeStage(HttpServletRequest request, HttpServletResponse response) {
        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImpl());

        String id = request.getParameter("id");
        String stage = request.getParameter("stage");
        String money = request.getParameter("money");
        String expectedDate = request.getParameter("expectedDate");
        String editBy = ((User) request.getSession().getAttribute("user")).getName();
        String editTime = DateTimeUtil.getSysTime();

        Tran tran = new Tran();
        tran.setId(id);
        tran.setEditBy(editBy);
        tran.setEditTime(editTime);
        tran.setMoney(money);
        tran.setStage(stage);
        tran.setExpectedDate(expectedDate);

        boolean success = tranService.changeStage(tran);
        HashMap<String,Object> map = new HashMap<>();
        map.put("success", success);
        map.put("tran", tran);
        HashMap<String,String> s2pMap = (HashMap<String, String>) this.getServletContext().getAttribute("s2pMap");
        String possibility = s2pMap.get(tran.getStage());
        tran.setPossibility(possibility);

        PrintJson.printJsonObj(response, map);
    }

    private void selectHisByTranId(HttpServletRequest request, HttpServletResponse response) {
        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImpl());

        String tranId = request.getParameter("tranId");

        List<TranHistory> histories = tranService.selectByTranId(tranId);
        HashMap<String, String> s2pMap = (HashMap<String, String>) this.getServletContext().getAttribute("s2pMap");
        for (TranHistory history : histories) {
            String stage = history.getStage();
            String possibility = s2pMap.get(stage);
            history.setPossibility(possibility);
        }

        PrintJson.printJsonObj(response, histories);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImpl());

        String id = request.getParameter("id");

        Tran tran = tranService.selectById(id);
        String stage = tran.getStage();
        HashMap<String, String> s2pMap = (HashMap<String, String>) this.getServletContext().getAttribute("s2pMap");
        String possibility = s2pMap.get(stage);

        request.setAttribute("possibility", possibility);
        request.setAttribute("tran", tran);
        request.getRequestDispatcher("/workbench/transaction/detail.jsp").forward(request, response);
    }

    private void createTran(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImpl());

        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String money = request.getParameter("money");
        String name = request.getParameter("name");
        String expectedDate = request.getParameter("expectedDate");
        String customerName = request.getParameter("customerName");
        String stage = request.getParameter("stage");
        String type = request.getParameter("type");
        String source = request.getParameter("source");
        String activityId = request.getParameter("activityId");
        String contactsId = request.getParameter("contactsId");
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");

        Tran tran = new Tran();
        tran.setId(id);
        tran.setOwner(owner);
        tran.setMoney(money);
        tran.setName(name);
        tran.setExpectedDate(expectedDate);
        tran.setStage(stage);
        tran.setType(type);
        tran.setSource(source);
        tran.setContactsId(contactsId);
        tran.setActivityId(activityId);
        tran.setCreateBy(createBy);
        tran.setCreateTime(createTime);
        tran.setDescription(description);
        tran.setContactSummary(contactSummary);
        tran.setNextContactTime(nextContactTime);
        boolean success = tranService.addTran(tran, customerName);

        if (success){
            response.sendRedirect(request.getContextPath() + "/workbench/transaction/index.jsp");
        }
    }

    private void getCustomerName(HttpServletRequest request, HttpServletResponse response) {
        CustomerService customerService = (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());

        String name = request.getParameter("name");

        List<String> names = customerService.selectNames(name);

        PrintJson.printJsonObj(response, names);
    }

    private void getUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> users = userService.selectUsers();

        request.setAttribute("users", users);
        request.getRequestDispatcher("/workbench/transaction/save.jsp").forward(request, response);
    }
}