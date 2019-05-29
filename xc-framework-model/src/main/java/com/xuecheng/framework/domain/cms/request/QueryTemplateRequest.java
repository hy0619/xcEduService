package com.xuecheng.framework.domain.cms.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 何懿
 * @description
 * @create 2019/5/29
 */
@Data
public class QueryTemplateRequest {

    @ApiModelProperty("模板id")
    private String id ;
    @ApiModelProperty("站点id")
    private String siteId;
    @ApiModelProperty("模板属性")
    private String templateName;
    @ApiModelProperty("模板参数")
    private String templateParameter;
    @ApiModelProperty("文件id")
    private String fileId;
}
