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