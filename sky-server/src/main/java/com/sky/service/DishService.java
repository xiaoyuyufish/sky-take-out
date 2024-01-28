package com.sky.service;

import com.github.pagehelper.Page;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

public interface DishService {
    void saveWithFlavor(DishDTO dishDTO);

    PageResult dishPageQuery(DishPageQueryDTO dishPageQueryDTO);
}
