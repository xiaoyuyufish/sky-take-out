package com.sky.mapper;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {
     public void insert(Orders orders);

     /**
      * 根据订单号查询订单
      * @param orderNumber
      */
     @Select("select * from orders where number = #{orderNumber}")
     Orders getByNumber(String orderNumber);

     /**
      * 修改订单信息
      * @param orders
      */
     void update(Orders orders);

     @Select("select * from sky_take_out.orders where id = #{id}")
     Orders getById(Long id);

     List<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

     Integer getCountByStatus(Integer status);
}
