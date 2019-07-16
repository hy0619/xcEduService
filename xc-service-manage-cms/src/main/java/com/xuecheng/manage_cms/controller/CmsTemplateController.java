package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsTemplateControllerApi;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryTemplateRequest;
import com.xuecheng.framework.domain.cms.response.CmsTemplateResult;
import com.xuecheng.framework.domain.cms.response.TemplateUploadResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.service.CmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-09-12 17:24
 **/
@RestController
@RequestMapping("/cms/template")
public class CmsTemplateController implements CmsTemplateControllerApi {
    @Autowired
    CmsTemplateService cmsTemplateService;


    @Override
    @GetMapping("/all")
    public QueryResponseResult findAllCmsTemplete(CmsTemplate cmsTemplate) {
        return cmsTemplateService.findAll(cmsTemplate);
    }
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findCmsTemplateList(@PathVariable  int page , @PathVariable  int size , QueryTemplateRequest queryTemplateRequest){
        return  cmsTemplateService.findList(page , size , queryTemplateRequest);
    }

    @Override
    @DeleteMapping("/del/{id}")
    public ResponseResult del(@PathVariable("id") String id) {
        return cmsTemplateService.del(id);
    }

    @Override
    @PostMapping("/add")
    public CmsTemplateResult add(@RequestBody CmsTemplate cmsTemplate) {
        return cmsTemplateService.add(cmsTemplate);
    }

    @Override
    @PutMapping("/edit/{id}")
    public CmsTemplateResult edit(@PathVariable("id") String id, @RequestBody CmsTemplate cmsTemplate) {
        return cmsTemplateService.edit(id , cmsTemplate);
    }

    @Override
    @GetMapping("/get/{id}")
    public CmsTemplateResult get(@PathVariable("id") String id) {
        return cmsTemplateService.getWithResult(id);
    }

    @Override
    @PostMapping("/upload")
    public TemplateUploadResult uploadTemplate(MultipartFile file) throws IOException {
        return cmsTemplateService.uploadTemplate(file);
    }

    @Override
    @DeleteMapping("/delFile/{templateFileId}")
    public ResponseResult delFile(@PathVariable String templateFileId){
        return cmsTemplateService.delFile(templateFileId);

    }
}
