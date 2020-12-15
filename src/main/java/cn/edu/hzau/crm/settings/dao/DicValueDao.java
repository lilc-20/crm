package cn.edu.hzau.crm.settings.dao;

import cn.edu.hzau.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueDao {
    List<DicValue> selectByCode(String code);
}
