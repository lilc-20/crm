package cn.edu.hzau.crm.workbench.service.impl;

import cn.edu.hzau.crm.settings.dao.UserDao;
import cn.edu.hzau.crm.settings.domain.User;
import cn.edu.hzau.crm.utils.SqlSessionUtil;
import cn.edu.hzau.crm.vo.Pagination;
import cn.edu.hzau.crm.workbench.dao.ActivityDao;
import cn.edu.hzau.crm.workbench.dao.ActivityRemarkDao;
import cn.edu.hzau.crm.workbench.domain.Activity;
import cn.edu.hzau.crm.workbench.domain.ActivityRemark;
import cn.edu.hzau.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;

public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);


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

    @Override
    public boolean delete(String[] ids) {
        boolean success = true;
        //delete activityRemarks
        int remarkCounts = activityRemarkDao.selectByActId(ids);
        int delRemarkNum = activityRemarkDao.deleteByActId(ids);

        if (remarkCounts != delRemarkNum){
            success = false;
        }
        //delete activity
        int actCounts = activityDao.selectById(ids);
        int delActNum = activityDao.deleteById(ids);

        if (actCounts != delActNum){
            success = false;
        }

        return success;
    }

    @Override
    public HashMap edit(String id) {
        List<User> users = userDao.selectUsers();
        Activity activity = activityDao.selectActivityById(id);

        HashMap<String, Object> map = new HashMap<>();
        map.put("users", users);
        map.put("activity", activity);

        return map;
    }

    @Override
    public boolean update(Activity activity) {
        int result = activityDao.update(activity);
        return result == 1 ? true : false;
    }

    @Override
    public Activity detail(String id) {
        return activityDao.detail(id);
    }

    @Override
    public List<ActivityRemark> selectRemarks(String id) {
        return activityRemarkDao.selectRemarks(id);
    }

}
