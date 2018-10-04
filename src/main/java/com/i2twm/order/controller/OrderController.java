package com.i2twm.order.controller;

import com.i2twm.order.converter.OrderForm2OrderDTO;
import com.i2twm.order.dataobject.OrderDetail;
import com.i2twm.order.dto.OrderDTO;
import com.i2twm.order.enums.ResultEnum;
import com.i2twm.order.exception.OrderException;
import com.i2twm.order.form.OrderForm;
import com.i2twm.order.service.OrderService;
import com.i2twm.order.utils.ResultVOUtil;
import com.i2twm.order.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确，orderForm={ }",orderForm);
            throw new OrderException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDTO.convert(orderForm);
            if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
                log.error("【创建订单】购物车信息为空");
                throw new OrderException(ResultEnum.CART_EMPTY);
            }
        OrderDTO result = orderService.create(orderDTO);
            Map<String,String> map = new HashMap<>();
            map.put("orderId",result.getOrderId());
            return ResultVOUtil.success(map);

    }
    @Value("${env}")
    private String env;

    @GetMapping("/print")
    public String print(){
        return env;
    }
}
