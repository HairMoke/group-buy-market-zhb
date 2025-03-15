package com.hb.test.infrastructure.dao;


import com.alibaba.fastjson.JSON;
import com.hb.infrastructure.dao.IGroupBuyActivateDao;
import com.hb.infrastructure.dao.IGroupBuyDiscountDao;
import com.hb.infrastructure.dao.po.GroupBuyActivity;
import com.hb.infrastructure.dao.po.GroupBuyDiscount;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class GroupBuyDiscountDaoTest {

    @Resource
    private IGroupBuyDiscountDao groupBuyDiscountDao;

    @Test
    public void testInsertGroupBuyDiscountList() {
        List<GroupBuyDiscount> groupBuyDiscounts = groupBuyDiscountDao.queryGroupBuyDiscountList();
        log.info("测试结果为：{}", JSON.toJSONString(groupBuyDiscounts));
    }
}
