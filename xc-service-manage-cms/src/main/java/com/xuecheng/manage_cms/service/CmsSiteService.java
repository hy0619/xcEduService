package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 何懿
 * @description
 * @create 2019/5/25
 */
@Service
public class CmsSiteService {
    @Autowired
    CmsSiteRepository cmsSiteRepository;

    /**
     * @title 根据条件查询所有站点
     * @author hy [CmsSite]
     * @updateTime 2019/5/25 
     */
    public QueryResponseResult findAll(CmsSite cmsSite){
        if(null == cmsSite) cmsSite = new CmsSite();
        Example<CmsSite> ex = Example.of(cmsSite);
       List all = cmsSiteRepository.findAll(ex);
        QueryResult queryResult = new QueryResult();
        queryResult.setList(all);//数据列表
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS,queryResult);
        return queryResponseResult;
    }
}
