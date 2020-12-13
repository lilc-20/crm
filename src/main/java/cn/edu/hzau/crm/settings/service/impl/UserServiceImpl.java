package cn.edu.hzau.crm.settings.service.impl;

import cn.edu.hzau.crm.settings.dao.UserDao;
import cn.edu.hzau.crm.settings.domain.User;
import cn.edu.hzau.crm.settings.exception.LoginException;
import cn.edu.hzau.crm.settings.service.UserService;
import cn.edu.hzau.crm.utils.DateTimeUtil;
import cn.edu.hzau.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {
        HashMap<String, String> loginMap = new HashMap<>();
        loginMap.put("loginAct", loginAct);
        loginMap.put("loginPwd", loginPwd);
        User user = userDao.selectUser(loginMap);

        if (user == null){
            throw new LoginException("用户名或密码错误");
        }

        String expireTime = user.getExpireTime();
        String sysTime = DateTimeUtil.getSysTime();
        if (expireTime.compareTo(sysTime) < 0){
            throw new LoginException("账号已过期");
        }

        if ("0".equals(user.getLockState())){
            throw new LoginException("账号已锁定");
        }

        String allowIps = user.getAllowIps();
        if (null == allowIps || !allowIps.contains(ip)){
            throw new LoginException("当前ip禁止访问");
        }

        return user;

    }

    @Override
    public List<User> selectUsers() {
        return userDao.selectUsers();
    }
}
