package cn.edu.hzau.crm.workbench.service.impl;

import cn.edu.hzau.crm.settings.dao.UserDao;
import cn.edu.hzau.crm.settings.domain.User;
import cn.edu.hzau.crm.utils.SqlSessionUtil;
import cn.edu.hzau.crm.utils.UUIDUtil;
import cn.edu.hzau.crm.workbench.dao.ActivityDao;
import cn.edu.hzau.crm.workbench.dao.ClueActivityRelationDao;
import cn.edu.hzau.crm.workbench.dao.ClueDao;
import cn.edu.hzau.crm.workbench.domain.Activity;
import cn.edu.hzau.crm.workbench.domain.Clue;
import cn.edu.hzau.crm.workbench.domain.ClueActivityRelation;
import cn.edu.hzau.crm.workbench.service.ClueService;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClueServiceImpl implements ClueService {
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    private ClueActivityRelationDao relationDao = SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);


    @Override
    public List<User> selectUsers() {
        return userDao.selectUsers();
    }

    @Override
    public boolean addClue(Clue clue) {
        int result = clueDao.insertClue(clue);
        return result == 1;
    }

    @Override
    public Clue selectById(String id) {
        return clueDao.selectById(id);
    }

    @Override
    public List<Activity> selectByClueId(String id) {
        List<ClueActivityRelation> relations = relationDao.selectByClueId(id);
        List<Activity> activities = new ArrayList<>();
        for (ClueActivityRelation relation : relations) {
            Activity activity = activityDao.selectByRelationId(relation.getActivityId());
            activities.add(activity);
        }

        return activities;
    }

    @Override
    public boolean deleteRelation(ClueActivityRelation relation) {
        int result = relationDao.deleteByActId(relation);

        return result == 1;
    }

    @Override
    public List<Activity> relationList(String id, String name) {
        List<ClueActivityRelation> relations = relationDao.selectByClueId(id);
        List<String> actIds = new ArrayList<>();
        for (ClueActivityRelation relation : relations) {
            String activityId = relation.getActivityId();
            actIds.add(activityId);
        }
        HashMap map = new HashMap<>();
        map.put("ids", actIds);
        map.put("name", name);

        List<Activity> activities = activityDao.selectOutOfId(map);

        return activities;
    }

    @Override
    public boolean addRelation(String clueId, String[] ids) {
        int counts = 0;
        int result = 0;
        for (String id : ids) {
            ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
            clueActivityRelation.setId(UUIDUtil.getUUID());
            clueActivityRelation.setClueId(clueId);
            clueActivityRelation.setActivityId(id);
            result = relationDao.addRelation(clueActivityRelation);
            counts = result == 1 ? ++counts : counts;
        }

        return counts == ids.length;
    }
}
