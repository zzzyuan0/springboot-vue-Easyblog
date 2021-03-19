package cn.zzzyuan.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.zzzyuan.common.contant.ResultContant;
import cn.zzzyuan.common.lang.Result;
import cn.zzzyuan.entity.Blog;
import cn.zzzyuan.common.contant.RedisContant;
import cn.zzzyuan.redis.RedisService;
import cn.zzzyuan.service.BlogService;
import cn.zzzyuan.service.CategoryBlogService;
import cn.zzzyuan.service.CategoryService;
import cn.zzzyuan.util.ShiroUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 杂货店的阿猿
 * @since 2020-10-12
 */
@RestController
public class BlogController {

    @Autowired
    BlogService blogService;
    @Autowired
    RedisService redisService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryBlogService categoryBlogService;

    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage,@RequestParam(defaultValue = "1") Integer pageSize){
        Page<Blog> page = new Page<>(currentPage,pageSize);
        IPage<Blog> iPage = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));
        return Result.succ(iPage);
    }

    @GetMapping("/blogs/{id}")
    public Result list(@PathVariable(name = "id")Long id){
        Blog blog = blogService.getById(id);
        Assert.notNull(blog,"该博客已被删除");
        redisService.updateRank(RedisContant.ARTICLE_HEAT.code(),String.valueOf(id));
        return Result.succ(blog);
    }
    @RequiresAuthentication
    @PostMapping("/blogs/edit")
    public Result list(@Validated @RequestBody Blog blog){

        Blog temp = null;
        if(blog.getId() != null){
            temp = blogService.getById(blog.getId());
            Assert.isTrue(temp.getUserId().equals(ShiroUtil.getProfile().getId()),"你没有权限编辑");
        }else{
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        BeanUtil.copyProperties(blog,temp,"id","userId","created","status");

        blogService.saveOrUpdate(temp);
        QueryWrapper<Blog> tWrapper = new QueryWrapper<Blog>();
        tWrapper.eq("title",blog.getTitle());
        categoryBlogService.addAndUpdateCategory(categoryService,categoryBlogService,blogService.getOne(tWrapper).getId(),temp.getCategory());
        return Result.succ(ResultContant.SUCCESS.msg());
    }


}
