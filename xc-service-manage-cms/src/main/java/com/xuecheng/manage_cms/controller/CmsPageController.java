package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.service.PageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-09-12 17:24
 **/
@RestController
@RequestMapping("/cms/page")
public class CmsPageController implements CmsPageControllerApi {

    @Autowired
    PageService pageService;

    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size")int size,  QueryPageRequest queryPageRequest) {
        return pageService.findList(page,size,queryPageRequest);
    }

    @Override
    @PostMapping("/add")
    public CmsPageResult addPage(@RequestBody CmsPage cmsPage){
        return pageService.add(cmsPage);
    }
    @Override
    @PutMapping("/edit/{id}")
    public CmsPageResult editPage(@PathVariable("id") String id , @RequestBody CmsPage cmsPage){
        return pageService.edit(id , cmsPage);
    }

    @GetMapping("get/{id}")
    public CmsPageResult getPage(@PathVariable("id") String id ){
        CmsPageResult result = new CmsPageResult(CommonCode.FAIL , null);
        CmsPage one = pageService.getById(id);
        if(null != one)
            result = new CmsPageResult(CommonCode.SUCCESS , one);
        return result;
    }

    @Override
    @DeleteMapping("del/{id}")
    public ResponseResult del(@PathVariable("id") String id) {
        return pageService.del(id);
    }

    @Override
    @GetMapping("preview/{id}")
    public void pagePreview(@PathVariable("id") String pageId, HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = null;
        String pageHtml = pageService.getPageHtml(pageId);
        if(StringUtils.isNotEmpty(pageHtml)){
            try {
                outputStream = response.getOutputStream();
                outputStream.write(pageHtml.getBytes("utf-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(null != outputStream)
                    outputStream.close();
                else
                    outputStream = null;
            }
        }
    }

    @Override
    @PostMapping("/publish/{pageId}")
    public ResponseResult publishPage(@PathVariable("pageId") String pageId) throws UnsupportedEncodingException {
        return pageService.publishPage(pageId);
    }


}
