package cn.zzzyuan.controller;

import cn.zzzyuan.common.contant.ElasticSeachContant;
import cn.zzzyuan.common.lang.Result;
import cn.zzzyuan.service.ElasticSeachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/select")
public class ElasticSeachController {
    @Autowired
    ElasticSeachService elasticSeachService;

    @GetMapping("/get/{text}")
    public Result getContent(@PathVariable(name = "text")String text) throws IOException {
        List<Map<String, Object>> list = elasticSeachService.getList(ElasticSeachContant.BLOGS_INDEX.code(), text);
        return Result.succ(list);
    }

}
