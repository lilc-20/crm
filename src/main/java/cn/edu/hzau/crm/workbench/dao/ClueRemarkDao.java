package cn.edu.hzau.crm.workbench.dao;

import cn.edu.hzau.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {

    List<ClueRemark> selectByClueId(String clueId);

    void deleteByClueId(String clueId);
}
