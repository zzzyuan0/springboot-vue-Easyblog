package cn.zzzyuan.controller;


import cn.zzzyuan.common.contant.ResultContant;
import cn.zzzyuan.common.lang.Result;
import cn.zzzyuan.entity.Blog;
import cn.zzzyuan.entity.Category;
import cn.zzzyuan.entity.CategoryBlog;
import cn.zzzyuan.service.BlogService;
import cn.zzzyuan.service.CategoryBlogService;
import cn.zzzyuan.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 杂货店的阿猿
 * @since 2020-12-02
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryBlogService categoryBlogService;
    @Autowired
    BlogService blogService;


    /**
     * 获取所有的分类
     * @return
     */
    @GetMapping("/all")
    public Result allCategory(){
        List<Category> list = categoryService.list();
        return Result.succ(ResultContant.SUCCESS.code(),list);
    }

    /**
     * 通过分类id查询对应文章
     * @param id
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/blogs")
    public Result byIdBlogs(int id ,@RequestParam(defaultValue = "1")int currentPage,@RequestParam(defaultValue = "5") int pageSize){
        QueryWrapper<CategoryBlog> wrapper;
        wrapper = new QueryWrapper<CategoryBlog>();
        wrapper.eq("category_id", id);
        Page<Blog> page = new Page<>(currentPage,pageSize);
        IPage<Blog> iPage = blogService.selectBlogByTagIPage(page,wrapper);
        return Result.succ(ResultContant.SUCCESS.code(),iPage);
    }
    /**
     * 通过文章id查询对应类别
     */
    @GetMapping("/getCategory")
    public Result categoryById(int id ,@RequestParam(defaultValue = "1")int currentPage,@RequestParam(defaultValue = "5") int pageSize){
        QueryWrapper<CategoryBlog> wrapper;
        wrapper = new QueryWrapper<CategoryBlog>();
        wrapper.eq("article_id", id);
        Page<Category> page = new Page<>(currentPage,pageSize);
        IPage<Category> iPage = categoryBlogService.selectCategoryByTagIPage(page,wrapper);
        return Result.succ(ResultContant.SUCCESS.code(),iPage);
    }
}
