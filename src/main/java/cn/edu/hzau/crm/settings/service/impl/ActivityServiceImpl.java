package cn.edu.hzau.crm.settings.service.impl;

import cn.edu.hzau.crm.utils.SqlSessionUtil;
import cn.edu.hzau.crm.vo.Pagination;
import cn.edu.hzau.crm.workbench.dao.ActivityDao;
import cn.edu.hzau.crm.workbench.domain.Activity;
import cn.edu.hzau.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;

public class ActivityServiceImpl implements ActivityService {
    ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);

    @Override
    public boolean addActivity(Activity activity) {
        int result = activityDao.insertActivity(activity);
        return result == 1 ? true : false;
    }

    @Override
    public Pagination<Activity> pageList(HashMap map) {
        List<Activity> activities = activityDao.selectActivities(map);
        int total = activityDao.selectCounts(map);
        Pagination<Activity> pagination = new Pagination<>();
        pagination.setTotal(total);
        pagination.setDataList(activities);

        return pagination;
    }
}
