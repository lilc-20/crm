package cn.edu.hzau.crm.workbench.dao;

import cn.edu.hzau.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranHistoryDao {

    int save(TranHistory tranHistory);

    List<TranHistory> selectByTranId(String tranId);
}
