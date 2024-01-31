package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController("adminShopController")
@Api(tags = "商铺相关接口")
@RequestMapping("/admin/shop")
public class ShopController {

    @Resource
    private RedisTemplate redisTemplate;

    public static String KEY = "SHOP_STATUS";

    @GetMapping("/status")
    @ApiOperation("获取商铺状态")
    public Result<Integer> getStatus() {
        log.info("获取商铺状态");
        Integer status = (Integer)redisTemplate.opsForValue().get(KEY);
        log.info("当前商铺状态为{}", status==1?"营业":"打烊");

        return Result.success(status);
    }
    @PutMapping("/{status}")
    @ApiOperation("修改商铺状态")
    public Result setStatus(@PathVariable Integer status) {

        log.info("修改商铺状态");
        redisTemplate.opsForValue().set(KEY, status);

        return Result.success();
    }
}
