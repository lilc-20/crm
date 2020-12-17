package cn.edu.hzau.crm.workbench.service.impl;

import cn.edu.hzau.crm.settings.dao.UserDao;
import cn.edu.hzau.crm.settings.domain.User;
import cn.edu.hzau.crm.utils.DateTimeUtil;
import cn.edu.hzau.crm.utils.SqlSessionUtil;
import cn.edu.hzau.crm.utils.UUIDUtil;
import cn.edu.hzau.crm.workbench.dao.*;
import cn.edu.hzau.crm.workbench.domain.*;
import cn.edu.hzau.crm.workbench.service.ClueService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClueServiceImpl implements ClueService {
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);

    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    private ClueActivityRelationDao relationDao = SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);
    private ClueRemarkDao clueRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ClueRemarkDao.class);

    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    private CustomerRemarkDao customerRemarkDao = SqlSessionUtil.getSqlSession().getMapper(CustomerRemarkDao.class);

    private ContactsDao contactsDao = SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    private ContactsRemarkDao contactsRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ContactsRemarkDao.class);
    private ContactsActivityRelationDao contactsActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ContactsActivityRelationDao.class);

    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);


    @Override
    public List<User> selectUsers() {
        return userDao.selectUsers();
    }

    @Override
    public boolean addClue(Clue clue) {
        int result = clueDao.insertClue(clue);
        return result == 1;
    }

    @Override
    public Clue selectById(String id) {
        return clueDao.selectById(id);
    }

    @Override
    public List<Activity> selectByClueId(String id) {
        List<ClueActivityRelation> relations = relationDao.selectByClueId(id);
        List<Activity> activities = new ArrayList<>();
        for (ClueActivityRelation relation : relations) {
            Activity activity = activityDao.selectByRelationId(relation.getActivityId());
            activities.add(activity);
        }

        return activities;
    }

    @Override
    public boolean deleteRelation(ClueActivityRelation relation) {
        int result = relationDao.deleteByActId(relation);

        return result == 1;
    }

    @Override
    public List<Activity> relationList(String id, String name) {
        List<ClueActivityRelation> relations = relationDao.selectByClueId(id);
        List<String> actIds = new ArrayList<>();
        for (ClueActivityRelation relation : relations) {
            String activityId = relation.getActivityId();
            actIds.add(activityId);
        }
        HashMap map = new HashMap<>();
        map.put("ids", actIds);
        map.put("name", name);

        List<Activity> activities = activityDao.selectOutOfId(map);

        return activities;
    }

    @Override
    public boolean addRelation(String clueId, String[] ids) {
        int counts = 0;
        int result = 0;
        for (String id : ids) {
            ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
            clueActivityRelation.setId(UUIDUtil.getUUID());
            clueActivityRelation.setClueId(clueId);
            clueActivityRelation.setActivityId(id);
            result = relationDao.addRelation(clueActivityRelation);
            counts = result == 1 ? ++counts : counts;
        }

        return counts == ids.length;
    }

    @Override
    public List<Activity> searchWithName(String clueId, String name) {
        return activityDao.selectWithName(clueId, name);
    }

    @Override
    public boolean convert(String clueId, Tran tran, String createBy) {
        boolean flag = true;//should be: throw new exception
        String createTime = DateTimeUtil.getSysTime();

        Clue clue = clueDao.getById(clueId);

        String company = clue.getCompany();
        Customer customer = customerDao.selectByName(company);

        if (customer == null){
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setAddress(clue.getAddress());
            customer.setWebsite(clue.getWebsite());
            customer.setOwner(clue.getOwner());
            customer.setPhone(clue.getPhone());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setName(company);
            customer.setDescription(clue.getDescription());
            customer.setCreateTime(createTime);
            customer.setCreateBy(createBy);
            customer.setContactSummary(clue.getContactSummary());

            int count1 = customerDao.save(customer);
            if (count1 != 1){
                flag = false;
            }
        }

        Contacts contacts = new Contacts();
        contacts.setOwner(clue.getOwner());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setId(UUIDUtil.getUUID());
        contacts.setFullname(clue.getFullname());
        contacts.setEmail(clue.getEmail());
        contacts.setDescription(clue.getDescription());
        contacts.setCustomerId(customer.getId());
        contacts.setCreateTime(createTime);
        contacts.setCreateBy(createBy);
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setAppellation(clue.getAppellation());

        int count2 = contactsDao.save(contacts);
        if (count2 != 1){
            flag = false;
        }

        List<ClueRemark> clueRemarks = clueRemarkDao.selectByClueId(clueId);
        for (ClueRemark clueRemark : clueRemarks) {
            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setCreateBy(clueRemark.getCreateBy());
            customerRemark.setCreateTime(clueRemark.getCreateBy());
            customerRemark.setCustomerId(customer.getId());
            customerRemark.setEditFlag("0");
            customerRemark.setNoteContent(clueRemark.getNoteContent());
            int count3 = customerRemarkDao.save(customerRemark);
            if (count3 != 1){
                flag = false;
            }

            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setCreateBy(clueRemark.getCreateBy());
            contactsRemark.setCreateTime(clueRemark.getCreateBy());
            contactsRemark.setEditFlag("0");
            contactsRemark.setContactsId(contacts.getId());
            contactsRemark.setNoteContent(clueRemark.getNoteContent());
            int count4 = contactsRemarkDao.save(contactsRemark);
            if (count4 != 1){
                flag = false;
            }
        }

        List<String> activityIds = relationDao.selectActIds(clueId);
        for (String activityId : activityIds) {
            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setContactsId(contacts.getId());
            contactsActivityRelation.setActivityId(activityId);
            int count5 = contactsActivityRelationDao.save(contactsActivityRelation);
            if (count5 != 1){
                flag = false;
            }
        }

        if (tran != null){
            tran.setOwner(clue.getOwner());
            tran.setCreateBy(createBy);
            tran.setCreateTime(createTime);
            tran.setSource(clue.getSource());
            tran.setDescription(clue.getDescription());
            tran.setCustomerId(customer.getId());
            tran.setContactsId(contacts.getId());
            int count6 = tranDao.save(tran);
            if (count6 != 1){
                flag = false;
            }

            TranHistory tranHistory = new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setCreateBy(createBy);
            tranHistory.setCreateTime(createTime);
            tranHistory.setExpectedDate(tran.getExpectedDate());
            tranHistory.setStage(tran.getStage());
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setTranId(tran.getId());
            int count7 = tranHistoryDao.save(tranHistory);
            if (count7 != 1){
                flag = false;
            }
        }

        relationDao.deleteByClueId(clueId);
        clueRemarkDao.deleteByClueId(clueId);
        clueDao.deleteById(clueId);

        return flag;
    }

}
