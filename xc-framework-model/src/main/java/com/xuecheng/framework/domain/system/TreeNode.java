package com.xuecheng.framework.domain.system;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 何懿
 * @description 封装tree对象
 * @create 2019/6/2
 */
@Data
@ToString
@Accessors(chain = true)
public class TreeNode<T> {
    /**
     * @title 节点id
     * @author hy 
     * @updateTime 2019/6/2 
     */
    private  String  id ;

    /**
     * @title 节点文本
     * @author hy 
     * @updateTime 2019/6/2 
     */
    private String text ;

    /**
     * @title 节点状态
     * @author hy 
     * @updateTime 2019/6/2 
     */
    private Map<String, Object> state;
    /**
     * 节点的子节点
     */
    private List<TreeNode<T>> children = new ArrayList<TreeNode<T>>();

    /**
     * 父ID
     */
    private String pid;

    /**
     * 是否有父节点
     */
    private boolean hasParent = false;

    /**
     * 是否有子节点
     */
    private boolean hasChildren = false;
}
