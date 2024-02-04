package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    List<ShoppingCart> get(ShoppingCart shoppingCart);

    void update(ShoppingCart cart);

    @Insert("insert into sky_take_out.shopping_cart (sky_take_out.shopping_cart.name, " +
            "sky_take_out.shopping_cart.image, sky_take_out.shopping_cart.user_id, " +
            "sky_take_out.shopping_cart.dish_id, sky_take_out.shopping_cart.setmeal_id, " +
            "sky_take_out.shopping_cart.dish_flavor, sky_take_out.shopping_cart.amount, " +
            "sky_take_out.shopping_cart.create_time) " +
            "values (#{name}, #{image}, #{userId}, #{dishId}, #{setmealId}, #{dishFlavor}, #{amount}, #{createTime})"
    )
    void insert(ShoppingCart shoppingCart);

    void delete(ShoppingCart shoppingCart);
}
