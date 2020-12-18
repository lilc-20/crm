package cn.edu.hzau.crm.web.listener;

import cn.edu.hzau.crm.settings.domain.DicValue;
import cn.edu.hzau.crm.settings.service.DicService;
import cn.edu.hzau.crm.settings.service.impl.DicServiceImpl;
import cn.edu.hzau.crm.utils.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

public class SysInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
//        System.out.println("--init--");
        ServletContext servletContext = servletContextEvent.getServletContext();

//        System.out.println("--dictionary--");
        DicService dicService = (DicService) ServiceFactory.getService(new DicServiceImpl());
        HashMap<String, List<DicValue>> values = dicService.getDicValues();
        Set<String> types = values.keySet();
        for (String type : types) {
            List<DicValue> dicValues = values.get(type);
            servletContext.setAttribute(type, dicValues);
        }
//        System.out.println("--dic-end--");

//        System.out.println("--Stage2Possibility--");
        ResourceBundle bundle = ResourceBundle.getBundle("Stage2Possibility");
        Enumeration<String> keys = bundle.getKeys();
        HashMap<String, String> s2pMap = new HashMap<>();
        while (keys.hasMoreElements()){
            String key = keys.nextElement();
            String string = bundle.getString(key);
            s2pMap.put(key, string);
        }
        servletContext.setAttribute("s2pMap", s2pMap);
//        System.out.println("--s2p-end--");

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
