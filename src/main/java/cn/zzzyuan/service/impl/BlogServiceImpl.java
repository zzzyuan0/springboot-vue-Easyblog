package cn.zzzyuan.service.impl;

import cn.zzzyuan.entity.Blog;
import cn.zzzyuan.entity.CategoryBlog;
import cn.zzzyuan.mapper.BlogMapper;
import cn.zzzyuan.service.BlogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 杂货店的阿猿
 * @since 2020-10-12
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {
    @Override
    public IPage<Blog> selectBlogByTagIPage(IPage<Blog> page, QueryWrapper<CategoryBlog> wrappers) {
        return baseMapper.selectBlogByTagIPage(page,wrappers);
    }


}
