package cn.edu.hzau.crm.web.listener;

import cn.edu.hzau.crm.settings.domain.DicValue;
import cn.edu.hzau.crm.settings.service.DicService;
import cn.edu.hzau.crm.settings.service.impl.DicServiceImpl;
import cn.edu.hzau.crm.utils.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class SysInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
//        System.out.println("--init--");
        ServletContext servletContext = servletContextEvent.getServletContext();

        DicService dicService = (DicService) ServiceFactory.getService(new DicServiceImpl());
        HashMap<String, List<DicValue>> values = dicService.getDicValues();
        Set<String> types = values.keySet();
        for (String type : types) {
            List<DicValue> dicValues = values.get(type);
            servletContext.setAttribute(type, dicValues);
        }

//        System.out.println("--end--");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
