package com.xuecheng.framework.domain.course.ext;

import com.xuecheng.framework.domain.course.Teachplan;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by admin on 2018/2/7.
 */
@Data
@Accessors(chain = true)
@ToString
public class TeachplanNode extends Teachplan {

    List<TeachplanNode> children  = new LinkedList<>();

}
