package cn.edu.hzau.crm.workbench.service;

import cn.edu.hzau.crm.vo.Pagination;
import cn.edu.hzau.crm.workbench.domain.Activity;

import java.util.HashMap;

public interface ActivityService {
    boolean addActivity(Activity activity);

    Pagination<Activity> pageList(HashMap map);

    boolean delete(String[] ids);
}
