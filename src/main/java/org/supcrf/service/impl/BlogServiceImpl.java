package org.supcrf.service.impl;

import org.supcrf.entity.Blog;
import org.supcrf.mapper.BlogMapper;
import org.supcrf.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 博客信息 服务实现类
 * </p>
 *
 * @author supcrf
 * @since 2021-02-06
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
