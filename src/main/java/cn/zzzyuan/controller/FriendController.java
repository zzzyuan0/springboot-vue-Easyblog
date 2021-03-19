package cn.zzzyuan.controller;


import cn.zzzyuan.common.lang.Result;
import cn.zzzyuan.entity.Friend;
import cn.zzzyuan.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 杂货店的阿猿
 * @since 2021-03-08
 */
@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    FriendService friendService;

    @PostMapping("/add")
    public Result addFriend(@RequestBody Friend friend){

        friendService.save(friend);
        return Result.succ("添加成功");
    }

    @GetMapping("/get")
    public Result addFriend(){
          return Result.succ(friendService.list());
    }


}
