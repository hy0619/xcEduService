package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_cms.dao.SysDictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author 何懿
 * @description
 * @create 2019/6/2
 */
@Service
public class SysDictionaryService {
    @Autowired
    SysDictionaryRepository sysDictionaryRepository;

    public SysDictionary getByType(String type) {
        SysDictionary sysDictionary = new SysDictionary().setDType(type);
        ExampleMatcher matcher =ExampleMatcher.matching().withIgnoreNullValues().withIgnorePaths("_id","_class");
        Example<SysDictionary> example = Example.of(sysDictionary ,matcher);
        Optional<SysDictionary> optional = sysDictionaryRepository.findOne(example);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }
}
