package cn.edu.hzau.crm.workbench.service.impl;

import cn.edu.hzau.crm.settings.dao.UserDao;
import cn.edu.hzau.crm.settings.domain.User;
import cn.edu.hzau.crm.utils.SqlSessionUtil;
import cn.edu.hzau.crm.workbench.dao.TranDao;
import cn.edu.hzau.crm.workbench.dao.TranHistoryDao;
import cn.edu.hzau.crm.workbench.service.TranService;

import java.util.List;

public class TranServiceImpl implements TranService {
    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);


}
