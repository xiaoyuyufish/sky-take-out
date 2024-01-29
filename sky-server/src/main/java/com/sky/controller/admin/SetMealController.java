package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.service.impl.SetMealServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
public class SetMealController {

    @Resource
    private SetMealServiceImpl setMealService;

    @PostMapping
    @ApiOperation("保存套餐信息")
    public Result save(@RequestBody SetmealDTO setmealDTO) {
        log.info("保存套餐信息：{}", setmealDTO);
        setMealService.save(setmealDTO);

        return Result.success();
    }
}
