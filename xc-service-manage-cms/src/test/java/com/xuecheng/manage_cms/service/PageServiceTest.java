package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 何懿
 * @description
 * @create 2019/5/25
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PageServiceTest {
    @Autowired
    PageService pageService;

    @Test
    public void testFindList(){
        QueryPageRequest pageRequest = new QueryPageRequest();
        pageRequest.setPageName("index.html");
        QueryResponseResult list = pageService.findList(0, 10, pageRequest);
        System.out.println(list.toString());
    }
}
