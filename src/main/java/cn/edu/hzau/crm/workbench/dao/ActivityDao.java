package cn.edu.hzau.crm.workbench.dao;

import cn.edu.hzau.crm.workbench.domain.Activity;

import java.util.HashMap;
import java.util.List;

public interface ActivityDao {
    int insertActivity(Activity activity);

    List<Activity> selectActivities(HashMap map);

    int selectCounts(HashMap map);

    int selectById(String[] ids);

    int deleteById(String[] ids);
}
