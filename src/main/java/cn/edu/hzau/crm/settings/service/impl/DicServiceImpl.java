package cn.edu.hzau.crm.settings.service.impl;

import cn.edu.hzau.crm.settings.dao.DicTypeDao;
import cn.edu.hzau.crm.settings.dao.DicValueDao;
import cn.edu.hzau.crm.settings.domain.DicType;
import cn.edu.hzau.crm.settings.domain.DicValue;
import cn.edu.hzau.crm.settings.service.DicService;
import cn.edu.hzau.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;

public class DicServiceImpl implements DicService {
    private DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);

    @Override
    public HashMap<String, List<DicValue>> getDicValues() {
        HashMap<String, List<DicValue>> map = new HashMap<>();

        List<DicType> types = dicTypeDao.selectTypes();
        String code;
        for (DicType type : types) {
            code = type.getCode();
            List<DicValue> dicValues = dicValueDao.selectByCode(code);
            map.put(code, dicValues);
        }

        return map;
    }
}
