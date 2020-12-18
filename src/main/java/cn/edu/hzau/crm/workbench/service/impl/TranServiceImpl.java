package cn.edu.hzau.crm.workbench.service.impl;

import cn.edu.hzau.crm.utils.SqlSessionUtil;
import cn.edu.hzau.crm.utils.UUIDUtil;
import cn.edu.hzau.crm.workbench.dao.CustomerDao;
import cn.edu.hzau.crm.workbench.dao.TranDao;
import cn.edu.hzau.crm.workbench.dao.TranHistoryDao;
import cn.edu.hzau.crm.workbench.domain.Customer;
import cn.edu.hzau.crm.workbench.domain.Tran;
import cn.edu.hzau.crm.workbench.domain.TranHistory;
import cn.edu.hzau.crm.workbench.service.TranService;

public class TranServiceImpl implements TranService {
    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Override
    public boolean addTran(Tran tran, String customerName) {
        boolean flag = true;

        Customer customer = customerDao.selectByName(customerName);
        if (customer == null){
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setOwner(tran.getOwner());
            customer.setCreateBy(tran.getCreateBy());
            customer.setCreateTime(tran.getCreateTime());
            customer.setDescription(tran.getDescription());
            customer.setNextContactTime(tran.getNextContactTime());
            customer.setContactSummary(tran.getContactSummary());
            int count1 = customerDao.save(customer);
            if (count1 != 1){
                flag = false;
            }
        }

        tran.setCustomerId(customer.getId());
        int count2 = tranDao.save(tran);
        if (count2 != 1){
            flag = false;
        }

        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setTranId(tran.getId());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setStage(tran.getStage());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setCreateTime(tran.getCreateTime());
        tranHistory.setCreateBy(tran.getCreateBy());
        int count3 = tranHistoryDao.save(tranHistory);
        if (count3 != 1){
            flag = false;
        }

        return flag;
    }
}
