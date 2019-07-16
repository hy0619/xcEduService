package com.xuecheng.manage_cms.service;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.*;
import com.xuecheng.manage_cms.config.RabbitmqConfig;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import sun.nio.ch.IOUtil;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-09-12 18:32
 **/
@Service
public class PageService {

    @Autowired
    CmsPageRepository cmsPageRepository;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CmsTemplateService cmsTemplateService;
    @Autowired
    GridFsTemplate gridFsTemplate;
    @Autowired
    RabbitTemplate rabbitTemplate;


    /**
     * 页面查询方法
     * @param page 页码，从1开始记数
     * @param size 每页记录数
     * @param queryPageRequest 查询条件
     * @return
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest){

        //分页参数
        if(page <=0){
            page = 1;
        }
        page = page -1;
        if(size<=0){
            size = 10;
        }
        Pageable pageable = PageRequest.of(page,size);
        CmsPage cmsPage = new CmsPage();
        if(StringUtils.isNotEmpty(queryPageRequest.getSiteId())){
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        if(StringUtils.isNotEmpty(queryPageRequest.getPageAliase())){
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        if(StringUtils.isNotEmpty(queryPageRequest.getPageName()))
            cmsPage.setPageName(queryPageRequest.getPageName());
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        exampleMatcher = exampleMatcher.withMatcher("pageAliase",
                ExampleMatcher.GenericPropertyMatchers.contains());
        exampleMatcher = exampleMatcher.withMatcher("pageName",
                ExampleMatcher.GenericPropertyMatchers.contains());
        Example<CmsPage> example = Example.of(cmsPage , exampleMatcher);
        Page<CmsPage> all = cmsPageRepository.findAll(example , pageable);
        QueryResult queryResult = new QueryResult();
        queryResult.setList(all.getContent());//数据列表
        queryResult.setTotal(all.getTotalElements());//数据总记录数
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS,queryResult);
        return queryResponseResult;
    }

    public CmsPageResult add(CmsPage cmsPage) {
        CmsPageResult result = new CmsPageResult(CommonCode.FAIL , null);
        //根据站点 页面名称  页面路径查询
        CmsPage cmsPageSelect =  new CmsPage().setSiteId(cmsPage.getSiteId())
                .setPageName(cmsPage.getPageName()).setPageId(null)
                .setPageWebPath(cmsPage.getPageWebPath());

        Example<CmsPage> example = Example.of(cmsPageSelect);
        List<CmsPage> list  = cmsPageRepository.findAll(example);
        if(!CollectionUtils.isEmpty(list)){
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTS) ;
        }
            CmsPage cmsPageRtn = cmsPageRepository.save(cmsPage);
            if(null != cmsPageRtn) result = new CmsPageResult(CommonCode.SUCCESS , cmsPageRtn);;
         return result;
    }

    public CmsPageResult edit(String id, CmsPage cmsPage) {
        CmsPageResult result = new CmsPageResult(CommonCode.FAIL , null);
        CmsPage one = this.getById(id);
        if(null != one){
            one.setTemplateId(cmsPage.getTemplateId());
            //更新所属站点
            one.setSiteId(cmsPage.getSiteId());
            //更新页面别名
            one.setPageAliase(cmsPage.getPageAliase());
            //更新页面名称
            one.setPageName(cmsPage.getPageName());
            //更新访问路径
            one.setPageWebPath(cmsPage.getPageWebPath());
            //更新物理路径
            one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            //更新dataUrl
            one.setDataUrl(cmsPage.getDataUrl());

            CmsPage save = cmsPageRepository.save(one);
            if(null != save)
                result = new CmsPageResult(CommonCode.SUCCESS , save);
        }
        return result;
    }

    public CmsPage getById(String id){
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if(null != optional)
            return optional.get();
        else
            return null;
    }

    public ResponseResult del(String id) {
        ResponseResult result = ResponseResult.FAIL();
        CmsPage one = this.getById(id);
        if(null !=one){
            cmsPageRepository.deleteById(id);
            result = ResponseResult.SUCCESS();
        }
        return result;
    }

    public String getPageHtml(String pageId) {
        CmsPage cmsPage = this.getById(pageId);
        if(null == cmsPage)
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        //获取远程数据Map
        Map model = this.getModelByDateUrl(cmsPage.getDataUrl());
        if(model == null) ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);

        //获取模板内容
        String templateContent = cmsTemplateService.getTemplateContentByTemplateId(cmsPage.getTemplateId());

        if(StringUtils.isEmpty(templateContent)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }

        //执行静态化
        String html = this.generateHtml(templateContent , model);
        if(StringUtils.isEmpty(html)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        return html;

    }

    private String generateHtml(String templateContent, Map model) {
        try {
            //配置类生成
            Configuration configuration = new Configuration(Configuration.getVersion());
            //模板加载器
            StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
            stringTemplateLoader.putTemplate("template",templateContent);
            //配置模板加载器
            configuration.setTemplateLoader(stringTemplateLoader);
            // 获取模板
            Template template1 = configuration.getTemplate("template");
            //数据填充生成html
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template1, model);
            return html;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map getModelByDateUrl(String dataUrl){
        if(StringUtils.isEmpty(dataUrl))
            ExceptionCast.cast(CmsCode.CMS_DATAURL_ISNULL);
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(dataUrl , Map.class);
        return  responseEntity.getBody();
    }

    public ResponseResult publishPage(String pageId) throws UnsupportedEncodingException {
        ResponseResult result = ResponseResult.FAIL();

        String html = this.getPageHtml(pageId);

        //将HTML文件保存到mongodb中
        CmsPage cmsPage = this.saveHtml(pageId , html);
        if(cmsPage!= null){
            send2RabbitMq(pageId);
            result = ResponseResult.SUCCESS();
        }
        return result;
    }

    private void send2RabbitMq(String pageId) {
        CmsPage cmsPage = this.getById(pageId);
        if(cmsPage == null)
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        Map<String,String> sendMap = new HashMap<String,String>(1);
        sendMap.put("pageId" , pageId);
        String sendMessage = JSON.toJSONString(sendMap);
        String siteId = cmsPage.getSiteId();

        rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE , siteId ,sendMessage);




    }

    private CmsPage saveHtml(String pageId , String content)  {
        CmsPage cmsPage = this.getById(pageId);
        if(cmsPage == null ) ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        //存储前先删除
        String htmlFileId = cmsPage.getHtmlFileId();
        if(StringUtils.isNotEmpty(htmlFileId))
            gridFsTemplate.delete(Query.query(Criteria.where("_id").is(htmlFileId)));
        InputStream inputStream = null;
        try {
            inputStream = IOUtils.toInputStream(content , "utf-8");
            ObjectId objectId = gridFsTemplate.store(inputStream, cmsPage.getPageName());
            //文件id
            String fileId = objectId.toString();
            //将文件id存储到cmspage中
            cmsPage.setHtmlFileId(fileId);
            cmsPageRepository.save(cmsPage);
            return cmsPage;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                inputStream = null;
                e.printStackTrace();
            }
        }
        return null;
    }
}
