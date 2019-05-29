package com.xuecheng.manage_cms.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author 何懿
 * @description
 * @create 2019/5/29
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RestTemplateTest {
    @Autowired
    RestTemplate restTemplate;

    @Test
    public void restTemplateTest(){
        String id = "5a791725dd573c3574ee333f";
        String reqUrl = "http://localhost:31001/cms/config/getCmsConfig/" + id ;
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(reqUrl , Map.class);
        Map resMap = responseEntity.getBody();
        System.out.println(resMap.toString());
    }
}
