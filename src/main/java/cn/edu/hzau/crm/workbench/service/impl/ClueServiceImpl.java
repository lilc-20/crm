package cn.edu.hzau.crm.workbench.service.impl;

import cn.edu.hzau.crm.settings.dao.UserDao;
import cn.edu.hzau.crm.settings.domain.User;
import cn.edu.hzau.crm.utils.SqlSessionUtil;
import cn.edu.hzau.crm.workbench.service.ClueService;

import java.util.List;

public class ClueServiceImpl implements ClueService {
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public List<User> selectUsers() {
        return userDao.selectUsers();
    }
}
