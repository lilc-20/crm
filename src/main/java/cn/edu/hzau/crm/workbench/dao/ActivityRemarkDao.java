package cn.edu.hzau.crm.workbench.dao;

import cn.edu.hzau.crm.workbench.domain.Activity;
import cn.edu.hzau.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {
    int selectByActId(String[] ids);

    int deleteByActId(String[] ids);

    List<ActivityRemark> selectRemarks(String id);
}
