package cn.edu.hzau.crm.settings.service;

import cn.edu.hzau.crm.settings.domain.User;
import cn.edu.hzau.crm.settings.exception.LoginException;

import java.util.List;

public interface UserService {
    User login(String loginAct, String loginPwd, String ip) throws LoginException;

    List<User> selectUsers();
}
