package org.supcrf.service.impl;

import org.supcrf.entity.Course;
import org.supcrf.mapper.CourseMapper;
import org.supcrf.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程信息 服务实现类
 * </p>
 *
 * @author supcrf
 * @since 2021-02-06
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

}
