package cn.edu.hzau.crm.workbench.dao;

import cn.edu.hzau.crm.settings.domain.User;
import cn.edu.hzau.crm.workbench.domain.Tran;

import java.util.List;

public interface TranDao {

    int save(Tran tran);

    Tran selectById(String id);

    int updateById(Tran tran);
}
