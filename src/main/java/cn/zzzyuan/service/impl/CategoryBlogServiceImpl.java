package cn.zzzyuan.service.impl;

import cn.zzzyuan.entity.Category;
import cn.zzzyuan.entity.CategoryBlog;
import cn.zzzyuan.mapper.CategoryBlogMapper;
import cn.zzzyuan.service.CategoryBlogService;
import cn.zzzyuan.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 杂货店的阿猿
 * @since 2020-12-02
 */
@Service
public class CategoryBlogServiceImpl extends ServiceImpl<CategoryBlogMapper, CategoryBlog> implements CategoryBlogService {
    @Override
    public IPage<Category> selectCategoryByTagIPage(IPage<Category> page, QueryWrapper<CategoryBlog> wrappers) {
        return baseMapper.selectCategoryByTagIPage(page,wrappers);
    }
    @Override
    public void addAndUpdateCategory(CategoryService categoryService, CategoryBlogService categoryBlogService, Long blogId, String cate){
        QueryWrapper<Category> wrapper;
        QueryWrapper<CategoryBlog> blogQueryWrapper;

        String[] cates = cate.split("，");

        for (int i = 0; i < cates.length; i++) {
            wrapper = new QueryWrapper<Category>();
            wrapper.eq("category_name", cates[i]);
            Category one = categoryService.getOne(wrapper);
            if(one==null){
                Category category = new Category(null,cates[i]);
                categoryService.save(category);
                one = categoryService.getOne(wrapper);
            }
            blogQueryWrapper = new QueryWrapper<CategoryBlog>();
            blogQueryWrapper.eq("article_id", blogId).eq("category_id",one.getCategory());
            if(categoryBlogService.getOne(blogQueryWrapper) == null){
                categoryBlogService.saveOrUpdate(new CategoryBlog(blogId,one.getCategory()));
            }
        }

    }
}
