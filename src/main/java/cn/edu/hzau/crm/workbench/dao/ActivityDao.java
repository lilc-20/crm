package cn.edu.hzau.crm.workbench.dao;

import cn.edu.hzau.crm.workbench.domain.Activity;
import cn.edu.hzau.crm.workbench.domain.ClueActivityRelation;

import java.util.HashMap;
import java.util.List;

public interface ActivityDao {
    int insertActivity(Activity activity);

    List<Activity> selectActivities(HashMap map);

    int selectCounts(HashMap map);

    int selectById(String[] ids);

    int deleteById(String[] ids);

    Activity selectActivityById(String id);

    int update(Activity activity);

    Activity detail(String id);

    Activity selectByRelationId(String activityId);

    List<Activity> selectOutOfId(HashMap map);
}
