package cn.edu.hzau.crm.workbench.dao;

public interface ActivityRemarkDao {
    int selectByActId(String[] ids);

    int deleteByActId(String[] ids);
}
