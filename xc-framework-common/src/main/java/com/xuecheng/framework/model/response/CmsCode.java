package com.xuecheng.framework.model.response;

import lombok.ToString;

/**
 * @author 何懿
 * @description
 * @create 2019/5/28
 */
@ToString
public enum CmsCode implements ResultCode {
    CMS_ADDPAGE_EXISTS(false,24001,"页面已存在！"),
    CMS_TEMPLATE_NOTEXISTS(false,24002,"cms模板不存在！"),
    CMS_PAGE_NOTEXISTS(false, 24003, "页面不存在" ),
    CMS_GENERATEHTML_DATAISNULL(false, 24004,"页面模型数据为空" ),
    CMS_GENERATEHTML_TEMPLATEISNULL(false,24005 , "页面模板为空"),
    CMS_DATAURL_ISNULL(false,24006 , "页面模型数据地址为空"),
    CMS_GENERATEHTML_HTMLISNULL(false,24007 , "模板生成静态页面失败，生成的html为空");
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;
    private CmsCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }
    @Override
    public boolean success() {
        return success;
    }
    @Override
    public int code() {
        return code;
    }
    @Override
    public String message() {
        return message;
    }
}
