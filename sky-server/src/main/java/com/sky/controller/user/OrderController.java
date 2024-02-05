package com.sky.controller.user;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.OrderDetail;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.service.impl.OrderServiceImpl;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping("/user/order")
@Api("订单相关接口")
@Slf4j
@RestController
public class OrderController {

    @Resource
    private OrderServiceImpl orderService;

    @PostMapping("/submit")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
        OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);

        return Result.success(orderSubmitVO);

    }

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
        log.info("生成预支付交易单：{}", orderPaymentVO);
        return Result.success(orderPaymentVO);
    }

    @GetMapping("/historyOrders")
    @ApiOperation("历史订单查询")
    public Result<PageResult> page(OrdersPageQueryDTO ordersPageQueryDTO) {
        log.info("历史订单查询：{}", ordersPageQueryDTO);
        PageResult pageResult = orderService.pageQuery(ordersPageQueryDTO);

        return Result.success(pageResult);
    }

    @GetMapping("/orderDetail/{id}")
    @ApiOperation("查询订单详情")
    public Result<OrderDetail> getOrderDetail(@PathVariable Long id) {
        OrderDetail orderDetail = orderService.getOrderDetail(id);

        return Result.success(orderDetail);
    }

    @PostMapping("/cancel/{id}")
    public Result cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);

        return Result.success();
    }
}
