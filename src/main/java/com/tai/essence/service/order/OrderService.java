package com.tai.essence.service.order;

import com.tai.essence.dto.AddressDTO;
import com.tai.essence.dto.OrderDTO;
import com.tai.essence.dto.UserDTO;
import com.tai.essence.exception.EntityNotFoundException;
import com.tai.essence.exception.OrderException;

import java.util.List;

public interface OrderService {
    OrderDTO crateOrder(OrderDTO orderDTO) throws EntityNotFoundException;
    OrderDTO findById(Long id) throws OrderException;
    List<OrderDTO> userOrdersHistory(Long user_id);
    OrderDTO placedOrder(Long order_id) throws OrderException;

    OrderDTO confirmedOrder(Long order_id) throws OrderException;

    OrderDTO shippedOrder(Long order_id) throws OrderException;
    OrderDTO deliveredOrder(Long order_id) throws OrderException;
    OrderDTO canceledOrder(Long order_id) throws OrderException;
    List<OrderDTO> getAllOrders();
    void deleteOrder(Long order_id) throws OrderException;
}
