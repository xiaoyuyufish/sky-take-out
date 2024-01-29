package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetMealDishMapper {
    List<Long> getSetMealIdsByDishId(List<Long> ids);

    @Insert("INSERT INTO setmeal_dish(" +
            "sky_take_out.setmeal_dish.setmeal_id, sky_take_out.setmeal_dish.dish_id, " +
            "sky_take_out.setmeal_dish.name, " +
            "sky_take_out.setmeal_dish.price, sky_take_out.setmeal_dish.copies) " +
            "values (#{setmealId}, #{dishId}, #{name}, #{price}, #{copies})")
    void save(SetmealDish setmealDish);

    @Delete("delete from sky_take_out.setmeal_dish where sky_take_out.setmeal_dish.dish_id = sky_take_out.setmeal_dish.dish_id")
    void deleteByDishId(Long dishId);
}
