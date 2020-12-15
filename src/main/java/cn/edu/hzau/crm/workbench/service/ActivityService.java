package cn.edu.hzau.crm.workbench.service;

import cn.edu.hzau.crm.settings.domain.User;
import cn.edu.hzau.crm.vo.Pagination;
import cn.edu.hzau.crm.workbench.domain.Activity;
import cn.edu.hzau.crm.workbench.domain.ActivityRemark;

import java.util.HashMap;
import java.util.List;

public interface ActivityService {
    boolean addActivity(Activity activity);

    Pagination<Activity> pageList(HashMap map);

    boolean delete(String[] ids);

    HashMap edit(String id);

    boolean update(Activity activity);

    Activity detail(String id);

    List<ActivityRemark> selectRemarks(String id);

    boolean delRemark(String id);

    boolean saveRemark(ActivityRemark activityRemark);

    boolean updateRemark(ActivityRemark activityRemark);
}
