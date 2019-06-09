package com.xuecheng.api.sys;

import com.xuecheng.framework.domain.system.SysDictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 何懿
 * @description 数据字典controller api
 * @create 2019/6/5
 */
@Api(value =  "数据字典接口", description = "提供数据字典的管理 查询功能")
public interface SysDictControllerApi {

    @ApiOperation(value = "数据字典查询接口")
    public SysDictionary getByType(String type);
}
