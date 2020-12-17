package cn.edu.hzau.crm.workbench.dao;

import cn.edu.hzau.crm.workbench.domain.Customer;

public interface CustomerDao {

    Customer selectByName(String company);

    int save(Customer customer);
}
