package cn.edu.hzau.crm.workbench.service.impl;

import cn.edu.hzau.crm.utils.SqlSessionUtil;
import cn.edu.hzau.crm.workbench.dao.CustomerDao;
import cn.edu.hzau.crm.workbench.service.CustomerService;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Override
    public List<String> selectNames(String name) {
        return customerDao.selectNames(name);
    }
}
