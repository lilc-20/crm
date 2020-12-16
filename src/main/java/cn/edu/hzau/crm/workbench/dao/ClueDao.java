package cn.edu.hzau.crm.workbench.dao;

import cn.edu.hzau.crm.workbench.domain.Clue;
import cn.edu.hzau.crm.workbench.domain.ClueActivityRelation;

public interface ClueDao {


    int insertClue(Clue clue);

    Clue selectById(String id);
}
