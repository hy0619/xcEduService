package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author 何懿
 * @description
 * @create 2019/6/5
 */
public interface SysDictionaryRepository extends MongoRepository<SysDictionary,String> {
}
