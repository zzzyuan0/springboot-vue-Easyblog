package cn.zzzyuan.controller;



import cn.zzzyuan.common.lang.Result;
import cn.zzzyuan.entity.Imgurl;
import cn.zzzyuan.service.ImgurlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 杂货店的阿猿
 * @since 2021-01-31
 */
@RestController
@RequestMapping("/imgurl")
public class ImgurlController {
    @Autowired
    ImgurlService imgurlService;

    @GetMapping("/get")
    public Result getUrl(@RequestParam(defaultValue = "5") int num){
           Random random = new Random();
           List<Imgurl> imgurlList = new ArrayList<Imgurl>();
        for (int i = 0; i < num; i++) {
            imgurlList.add(imgurlService.getById(random.nextInt(690)+1));
        }
           return Result.succ(imgurlList);

    }
}
