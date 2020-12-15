package cn.edu.hzau.crm.settings.dao;

import cn.edu.hzau.crm.settings.domain.DicType;

import java.util.List;

public interface DicTypeDao {
    List<DicType> selectTypes();
}
