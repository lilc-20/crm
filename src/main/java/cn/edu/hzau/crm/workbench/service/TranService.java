package cn.edu.hzau.crm.workbench.service;

import cn.edu.hzau.crm.workbench.domain.Tran;
import cn.edu.hzau.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranService {

    boolean addTran(Tran tran, String customerName);

    Tran selectById(String id);

    List<TranHistory> selectByTranId(String tranId);

    boolean changeStage(Tran tran);
}
