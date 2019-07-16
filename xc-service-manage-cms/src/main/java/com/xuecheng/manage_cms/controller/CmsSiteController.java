package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsSiteControllerApi;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_cms.service.CmsSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 何懿
 * @description
 * @create 2019/5/25
 */
@RestController
@RequestMapping("/cms/site")
public class CmsSiteController implements CmsSiteControllerApi {
    @Autowired
    CmsSiteService cmsSiteService;
    @Override
    @GetMapping("/list")
    public QueryResponseResult findAllSite(CmsSite cmsSite) {
        return cmsSiteService.findAll(cmsSite);
    }
}
