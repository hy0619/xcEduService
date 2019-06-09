package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.sys.SysDictControllerApi;
import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_cms.service.SysDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 何懿
 * @description
 * @create 2019/6/5
 */
@RestController
@RequestMapping("/sys/dictionary")
public class SysDictionaryController implements SysDictControllerApi {
    @Autowired
    SysDictionaryService sysDictionaryService;
    @Override
    @GetMapping("/get/{type}")
    public SysDictionary getByType(@PathVariable("type") String type) {
        return sysDictionaryService.getByType(type);
    }
}
