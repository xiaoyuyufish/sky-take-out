package com.sky.mapper;

import com.sky.annotation.Autofill;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FlavorMapper {


    public void insertBatch(List<DishFlavor> flavors);

    @Delete("delete from sky_take_out.dish_flavor where sky_take_out.dish_flavor.dish_id = #{id}")
    void deleteByDishId(Long id);

    @Select("select * from sky_take_out.dish_flavor where dish_id = #{id}")
    List<DishFlavor> getByDishId(Long id);
}
