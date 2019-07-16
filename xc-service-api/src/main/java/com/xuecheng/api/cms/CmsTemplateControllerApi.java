package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryTemplateRequest;
import com.xuecheng.framework.domain.cms.response.CmsTemplateResult;
import com.xuecheng.framework.domain.cms.response.TemplateUploadResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 何懿
 * @description
 * @create 2019/5/25
 */
@Api(value="cms模板管理接口",description = "cms模板管理接口，提供模板的增、删、改、查")
public interface CmsTemplateControllerApi {

    @ApiOperation(value = "根据条件查询所有cms模板")
    public QueryResponseResult findAllCmsTemplete(CmsTemplate cmsTemplate);

    @ApiOperation(value = "根据条件分页查询cms模板")
    public QueryResponseResult findCmsTemplateList(int page ,  int size , QueryTemplateRequest queryTemplateRequest);

    @ApiOperation("根据id删除模板")
    public ResponseResult del(String id);

    @ApiOperation("cms模板添加")
    public CmsTemplateResult add(CmsTemplate cmsTemplate);

    @ApiOperation("cms模板修改")
    public CmsTemplateResult edit(String id,CmsTemplate cmsTemplate);

    @ApiOperation("cms模板id查询")
    public CmsTemplateResult get(String id) ;

    @ApiOperation("cms模板上传")
    public TemplateUploadResult uploadTemplate(MultipartFile file) throws IOException;

    @ApiOperation("cms模板文件删除")
    public ResponseResult delFile( String templateFileId);

}
