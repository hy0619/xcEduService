package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Api(value="cms页面管理接口",description = "cms页面管理接口，提供页面的增、删、改、查")
public interface CmsPageControllerApi {
    //页面查询
    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "页码",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="size",value = "每页记录数",required=true,paramType="path",dataType="int")
    })
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);


    @ApiOperation("cms页面添加")
    public CmsPageResult addPage(CmsPage cmsPage);


    @ApiOperation("cms页面修改")
    public CmsPageResult editPage(String id , CmsPage cmsPage);

    @ApiOperation("根据id获取cms页面")
    public CmsPageResult getPage( String id );

    @ApiOperation("根据id删除cms页面")
    public ResponseResult del(String id);

    @ApiOperation("cms页面预览")
    public void pagePreview( String pageId , HttpServletResponse response) throws IOException;

    @ApiOperation("cms页面发布")
    public ResponseResult publishPage(String pageId) throws UnsupportedEncodingException;
}
