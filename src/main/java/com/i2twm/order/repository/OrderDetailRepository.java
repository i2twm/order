package com.i2twm.order.repository;

import com.i2twm.order.dataobject.OrderDetail;
import com.i2twm.order.dataobject.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {
    
}
