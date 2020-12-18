package cn.edu.hzau.crm.workbench.dao;

import cn.edu.hzau.crm.workbench.domain.Customer;

import java.util.List;

public interface CustomerDao {

    Customer selectByName(String company);

    int save(Customer customer);

    List<String> selectNames(String name);
}
