package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @author 何懿
 * @description
 * @create 2019/6/4
 */
@Api(value = "课程分类管理",description = "课程分类管理")
public interface CategoryControllerApi {
    @ApiOperation("查询课程分类")
    public List<CategoryNode> findList();
}
