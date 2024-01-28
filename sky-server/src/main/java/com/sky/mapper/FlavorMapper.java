package com.sky.mapper;

import com.sky.annotation.Autofill;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FlavorMapper {

    @Autofill(value = OperationType.INSERT)
    public void insertBatch(List<DishFlavor> flavors);
}
