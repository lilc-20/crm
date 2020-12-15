package cn.edu.hzau.crm.settings.service;

import cn.edu.hzau.crm.settings.domain.DicValue;

import java.util.HashMap;
import java.util.List;

public interface DicService {
    HashMap<String, List<DicValue>> getDicValues();
}
