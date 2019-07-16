package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author 何懿
 * @description
 * @create 2019/5/25
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsSiteServiceTest {
    @Autowired
    CmsSiteService cmsSiteService;

    @Test
    public void testFindList(){
        QueryResponseResult list = cmsSiteService.findAll(new CmsSite());
        System.out.println(list.toString());
    }
}
