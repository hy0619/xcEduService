package com.xuecheng.framework.domain.cms.response;

import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 何懿
 * @description
 * @create 2019/5/29
 */
@Data
@ToString
@NoArgsConstructor
public class TemplateUploadResult extends ResponseResult {
    String templateFileId;
    public TemplateUploadResult(ResultCode resultCode, String templateFileId) {
        super(resultCode);
        this.templateFileId = templateFileId;
    }
}
