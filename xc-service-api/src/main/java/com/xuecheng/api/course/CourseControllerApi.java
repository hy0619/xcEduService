package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.UpdateCourseResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 何懿
 * @description
 * @create 2019/6/2
 */
public interface CourseControllerApi {
    @ApiOperation("课程计划查询-【tree结构】")
    public TeachplanNode findTeachplanList(String courseId);

    @ApiOperation("课程计划添加")
    public ResponseResult addTeachplan( Teachplan teachplan);

    @ApiOperation("课程基础信息添加")
    public ResponseResult addCourseBase(CourseBase courseBase);

    @ApiOperation("课程基础信息获取")
    CourseBase get( String courseId);

    @ApiOperation("分页查询课程基础信息")
    @GetMapping("/list/{page}/{size}")
    QueryResponseResult findList( int page, int size, CourseListRequest request);

    @ApiOperation("课程基础信息更新")
    UpdateCourseResult updateCourseBase(String courseId,  CourseBase courseBase);

    @ApiOperation("课程营销信息查询")
    CourseMarket getCourseMarketById( String courseId);

    @ApiOperation("课程基础信息更新")
    ResponseResult updateCourseMarket(String courseId,  CourseMarket courseMarket);
}
