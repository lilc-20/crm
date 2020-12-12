package cn.edu.hzau.crm.settings.service.impl;

import cn.edu.hzau.crm.settings.dao.UserDao;
import cn.edu.hzau.crm.settings.service.UserService;
import cn.edu.hzau.crm.utils.SqlSessionUtil;

public class UserServiceImpl implements UserService {

    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

}
