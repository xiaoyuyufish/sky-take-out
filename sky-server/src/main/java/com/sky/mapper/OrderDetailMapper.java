package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface OrderDetailMapper {
    void insert(ArrayList<OrderDetail> orderDetails);

    @Select("select * from sky_take_out.order_detail where id = #{id}")
    OrderDetail getById(Long id);

    List<OrderDetail> getDetailListByOrderId(Long id);
}
