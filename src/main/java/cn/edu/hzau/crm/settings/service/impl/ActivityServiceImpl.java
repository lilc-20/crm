package cn.edu.hzau.crm.settings.service.impl;

import cn.edu.hzau.crm.utils.SqlSessionUtil;
import cn.edu.hzau.crm.workbench.dao.ActivityDao;
import cn.edu.hzau.crm.workbench.domain.Activity;
import cn.edu.hzau.crm.workbench.service.ActivityService;

public class ActivityServiceImpl implements ActivityService {
    ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);

    @Override
    public boolean addActivity(Activity activity) {
        int result = activityDao.insertActivity(activity);
        return result == 1 ? true : false;
    }
}
