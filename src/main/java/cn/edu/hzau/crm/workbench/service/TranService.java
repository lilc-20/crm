package cn.edu.hzau.crm.workbench.service;

import cn.edu.hzau.crm.workbench.domain.Tran;

public interface TranService {

    boolean addTran(Tran tran, String customerName);
}
