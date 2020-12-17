package cn.edu.hzau.crm.workbench.service;

import cn.edu.hzau.crm.settings.domain.User;
import cn.edu.hzau.crm.workbench.domain.Activity;
import cn.edu.hzau.crm.workbench.domain.Clue;
import cn.edu.hzau.crm.workbench.domain.ClueActivityRelation;
import cn.edu.hzau.crm.workbench.domain.Tran;

import java.util.List;

public interface ClueService {
    List<User> selectUsers();

    boolean addClue(Clue clue);

    Clue selectById(String id);

    List<Activity> selectByClueId(String id);

    boolean deleteRelation(ClueActivityRelation relation);

    List<Activity> relationList(String id, String name);

    boolean addRelation(String clueId, String[] ids);

    List<Activity> searchWithName(String clueId, String name);

    boolean convert(String clueId, Tran tran, String createBy);
}
