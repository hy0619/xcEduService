package com.xuecheng.manage_cms.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryTemplateRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsTemplateResult;
import com.xuecheng.framework.domain.cms.response.TemplateUploadResult;
import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.*;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.cms.CMSException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author 何懿
 * @description
 * @create 2019/5/25
 */
@Service
public class CmsTemplateService {
    @Autowired
    CmsTemplateRepository cmsTemplateRepository;
    @Autowired
    GridFsTemplate gridFsTemplate;
    @Autowired
    GridFSBucket gridFSBucket;

    /**
     * @title 根据条件查询所有站点
     * @author hy [CmsSite]
     * @updateTime 2019/5/25 
     */
    public QueryResponseResult findAll(CmsTemplate cmsTemplate){
        if(null == cmsTemplate) cmsTemplate = new CmsTemplate();
        Example<CmsTemplate> ex = Example.of(cmsTemplate);
       List all = cmsTemplateRepository.findAll(ex);
        QueryResult queryResult = new QueryResult();
        queryResult.setList(all);//数据列表
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS,queryResult);
        return queryResponseResult;
    }

    /**
     * @title 分页查询 cms 模板
     * @author hy [page, size, cmsTemplate]
     * @updateTime 2019/5/29 
     */
    public QueryResponseResult findList(int page, int size, QueryTemplateRequest queryTemplateRequest) {
        //分页参数
        if(page <=0){
            page = 1;
        }
        page = page -1;
        if(size<=0){
            size = 10;
        }
        Pageable pageable = PageRequest.of(page,size);

        CmsTemplate template = new CmsTemplate();

        if(StringUtils.isNotEmpty(queryTemplateRequest.getSiteId())){
            template.setSiteId(queryTemplateRequest.getSiteId());
        }
        if(StringUtils.isNotEmpty(queryTemplateRequest.getTemplateName())){
            template.setTemplateName(queryTemplateRequest.getTemplateName());
        }
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        exampleMatcher = exampleMatcher.withMatcher("templateName",
                ExampleMatcher.GenericPropertyMatchers.contains());
        Example<CmsTemplate> example = Example.of(template , exampleMatcher);
        Page<CmsTemplate> all = cmsTemplateRepository.findAll(example , pageable);
        QueryResult queryResult = new QueryResult();
        queryResult.setList(all.getContent());//数据列表
        queryResult.setTotal(all.getTotalElements());//数据总记录数
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS,queryResult);
        return queryResponseResult;

    }

    public ResponseResult del(String id) {
        ResponseResult result = ResponseResult.FAIL();
        //根据id查询
        CmsTemplate one =  this.get(id);
        if(one != null){
            cmsTemplateRepository.deleteById(id);
            result = ResponseResult.SUCCESS();
        }
        return result;
    }

    public CmsTemplateResult add(CmsTemplate cmsTemplate) {
        //cmsTemplate.setTemplateId(null);
        CmsTemplateResult result = new CmsTemplateResult(CommonCode.FAIL , null);
        CmsTemplate save = cmsTemplateRepository.save(cmsTemplate);
        if(null != save){
            result = new CmsTemplateResult(CommonCode.SUCCESS , save);
        }
        return result;

    }

    public CmsTemplateResult edit(String id, CmsTemplate cmsTemplate) {
        CmsTemplateResult result = new CmsTemplateResult(CommonCode.FAIL , null);
        CmsTemplate one = this.get(id);
        if(null != one){
            one.setSiteId(cmsTemplate.getSiteId());
            one.setTemplateName(cmsTemplate.getTemplateName());
            one.setTemplateFileId(cmsTemplate.getTemplateFileId());
            one.setTemplateParameter(cmsTemplate.getTemplateParameter());
            CmsTemplate save = cmsTemplateRepository.save(one);
            if(null != save)
                result = new CmsTemplateResult(CommonCode.SUCCESS , save);

        }
        return result;


    }

    public CmsTemplateResult getWithResult(String id){
        CmsTemplateResult result = new CmsTemplateResult(CommonCode.FAIL , null);
        CmsTemplate template = this.get(id);
        if(template != null)
            result =  new CmsTemplateResult(CommonCode.SUCCESS , null);
        return result;
    }

    public CmsTemplate get(String id) {
        Optional<CmsTemplate> optional = cmsTemplateRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }


    public TemplateUploadResult uploadTemplate(MultipartFile file) throws IOException {
        ObjectId objectId = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename() , "");
        if(null != objectId){
           return new TemplateUploadResult(CommonCode.SUCCESS , objectId.toString()) ;
        }else{
            return new TemplateUploadResult(CommonCode.FAIL ,null) ;
        }
        //return objectId.toString();
    }

    public ResponseResult delFile(String templateFileId) {
        Query qry = Query.query(Criteria.where("_id").is(templateFileId));
        GridFSFile gridFSFile = gridFsTemplate.findOne(qry);
        if(null != gridFSFile){
            gridFsTemplate.delete(qry);
            return  ResponseResult.SUCCESS();
        }else{
            return  ResponseResult.FAIL();
        }

    }

    /**
     * @title 获取模板内容
     * @author hy [templateId]
     * @updateTime 2019/5/30 
     */

    public String getTemplateContentByTemplateId(String templateId) {
        CmsTemplate cmsTemplate = this.get(templateId);
        if(null == cmsTemplate)
            ExceptionCast.cast(CmsCode.CMS_TEMPLATE_NOTEXISTS);
        String templateFileId = cmsTemplate.getTemplateFileId();
        //取出模板文件内容
        GridFSFile gridFSFile =
                gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
        if(gridFSFile == null) ExceptionCast.cast(CmsCode.CMS_TEMPLATE_NOTEXISTS);
        GridFSDownloadStream gridFSDownloadStream =
                gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        //创建GridFsResource
        GridFsResource gridFsResource = new GridFsResource(gridFSFile,gridFSDownloadStream);
        try {
            String content = IOUtils.toString(gridFsResource.getInputStream(), "utf-8");
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
