package com.xuecheng.framework.domain.test;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author 何懿
 * @description
 * @create 2019/6/6
 */
@Data
@ToString
public class Test {
    private Integer id ;
    private String name ;
}
