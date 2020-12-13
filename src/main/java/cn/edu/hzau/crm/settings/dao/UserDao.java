package cn.edu.hzau.crm.settings.dao;

import cn.edu.hzau.crm.settings.domain.User;

import java.util.HashMap;
import java.util.List;

public interface UserDao {
    User selectUser(HashMap<String, String> map);

    List<User> selectUsers();
}
