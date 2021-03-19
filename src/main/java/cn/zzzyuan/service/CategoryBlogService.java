package cn.zzzyuan.service;

import cn.zzzyuan.entity.Category;
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
 * @since 2020-12-02
 */
public interface CategoryBlogService extends IService<CategoryBlog> {

    public IPage<Category> selectCategoryByTagIPage(IPage<Category> page, QueryWrapper<CategoryBlog> wrappers);

    public void addAndUpdateCategory(CategoryService categoryService, CategoryBlogService categoryBlogService, Long blogId, String cate);

}
