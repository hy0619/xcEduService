package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @author 何懿
 * @description
 * @create 2019/5/25
 */
@Api(value="cms站点管理接口",description = "cms站点管理接口，提供页面的增、删、改、查")
public interface CmsSiteControllerApi {

    @ApiOperation(value = "根据条件查询所有cms站点")
    public QueryResponseResult findAllSite(CmsSite cmsSite);
}
