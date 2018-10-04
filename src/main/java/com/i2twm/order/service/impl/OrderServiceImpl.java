package com.i2twm.order.service.impl;

import com.i2twm.order.client.ProductClient;
import com.i2twm.order.dataobject.OrderDetail;
import com.i2twm.order.dataobject.OrderMaster;
import com.i2twm.order.dataobject.ProductInfo;
import com.i2twm.order.dto.CartDTO;
import com.i2twm.order.dto.OrderDTO;
import com.i2twm.order.enums.OrderStatusEnum;
import com.i2twm.order.enums.PayStatusEnum;
import com.i2twm.order.repository.OrderDetailRepository;
import com.i2twm.order.repository.OrderMasterRepository;
import com.i2twm.order.service.OrderService;
import com.i2twm.order.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private ProductClient productClient;

    public OrderDTO create(OrderDTO orderDTO) {
        String OrderId = KeyUtil.genUniqueKey();
        //查询商品信息
        List<String> list = orderDTO.getOrderDetailList().stream().map(OrderDetail::getProductId).collect(Collectors.toList());
        List<ProductInfo> infoList = productClient.listfororder(list);
        //计算总价
        BigDecimal orderAmout = new BigDecimal(BigInteger.ZERO);
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            for (ProductInfo productInfo :infoList){
                if (productInfo.getProductId().equals(orderDetail.getProductId())){
                    orderAmout =   productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmout);
                    BeanUtils.copyProperties(productInfo,orderDetail);
                    orderDetail.setOrderId(OrderId);
                    orderDetail.setDetailId(KeyUtil.genUniqueKey());
                    orderDetailRepository.save(orderDetail);
                }
            }

        }
        //扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e -> new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        productClient.descStock(cartDTOList);
        //订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(OrderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmout);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());

        orderMasterRepository.save(orderMaster);

        return orderDTO;

    }
}
