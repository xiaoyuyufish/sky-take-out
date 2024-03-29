package com.sky.service;


import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.OrderDetail;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);
    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);

    /**
     * 查询指定用户的历史订单
     * @param ordersPageQueryDTO
     * @return
     * */
    PageResult pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 查询指定订单详情
     * @param id
     * @return
     * */
    OrderDetail getOrderDetail(Long id);

    /**
     * 取消订单
     * @param id
     * */
    void cancelOrder(Long id);

    /**
     * 再来一单
     * @param id
     * */
    void reputation(Long id);

    /**
     * 订单统计
     * */
    OrderStatisticsVO statics();

    OrderVO detail(Long id);
}
