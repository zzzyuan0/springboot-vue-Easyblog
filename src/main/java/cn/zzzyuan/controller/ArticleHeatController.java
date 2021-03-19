package cn.zzzyuan.controller;

import cn.zzzyuan.common.lang.Result;
import cn.zzzyuan.entity.ArticleHeat;
import cn.zzzyuan.common.contant.RedisContant;
import cn.zzzyuan.entity.Blog;
import cn.zzzyuan.entity.Heat;
import cn.zzzyuan.redis.RedisService;
import cn.zzzyuan.service.ArticleHeatService;
import cn.zzzyuan.service.BlogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/heat")
public class ArticleHeatController {

    @Autowired
    RedisService redisService;
    @Autowired
    ArticleHeatService articleHeatService;
    @Autowired
    BlogService blogService;

    @GetMapping("/list")
    public Result getList(){
        List<ArticleHeat> heats = redisService.getHeat(RedisContant.ARTICLE_HEAT.code(), redisService.size(RedisContant.ARTICLE_HEAT.code()) < 5 ? -1 : 5);
        List<Heat> list = new ArrayList<>();
        QueryWrapper<Blog> queryWrapper ;
        for (ArticleHeat heat : heats) {
        queryWrapper =  new QueryWrapper<>();
        queryWrapper.eq("id",heat.getId());
        list.add(new Heat(heat.getId(),blogService.getOne(queryWrapper).getTitle(),heat.getScore()));
        }
        return Result.succ(list);
    }

    @GetMapping("/{id}")
    public Result getHeat(@PathVariable(name = "id")Long id){
       return Result.succ(redisService.getScore(RedisContant.ARTICLE_HEAT.code(),String.valueOf(id))) ;
    }


}
