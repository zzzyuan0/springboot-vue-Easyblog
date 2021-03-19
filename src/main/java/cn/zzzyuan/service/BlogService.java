package cn.zzzyuan.service;

import cn.zzzyuan.entity.Blog;
import cn.zzzyuan.entity.CategoryBlog;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 杂货店的阿猿
 * @since 2020-10-12
 */
public interface BlogService extends IService<Blog> {
    IPage<Blog> selectBlogByTagIPage(IPage<Blog> page, QueryWrapper<CategoryBlog> wrappers);

    /**
     * @param blogId
     * @param cate
     */


}
