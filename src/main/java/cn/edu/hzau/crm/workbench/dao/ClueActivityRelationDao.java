package cn.edu.hzau.crm.workbench.dao;

import cn.edu.hzau.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {


    List<ClueActivityRelation> selectByClueId(String id);

    int deleteByActId(ClueActivityRelation relation);

    int addRelation(ClueActivityRelation clueActivityRelation);
}
