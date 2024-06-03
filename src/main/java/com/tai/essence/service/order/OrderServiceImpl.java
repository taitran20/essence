package com.tai.essence.service.order;

import com.tai.essence.dto.AddressDTO;
import com.tai.essence.dto.OrderDTO;
import com.tai.essence.dto.UserDTO;
import com.tai.essence.entity.Order;
import com.tai.essence.entity.OrderStatus;
import com.tai.essence.entity.User;
import com.tai.essence.exception.EntityNotFoundException;
import com.tai.essence.exception.OrderException;
import com.tai.essence.repository.AddressRepository;
import com.tai.essence.repository.OrderRepository;
import com.tai.essence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderDTO crateOrder(OrderDTO orderDTO) throws EntityNotFoundException {
        User user = userRepository.findByEmail(orderDTO.getUserDTO().getEmail());
        if(user == null){
            throw new EntityNotFoundException("Not found user");
        }
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(modelMapper -> modelMapper.skip(Order::setId));
        Order order = modelMapper.map(orderDTO, Order.class);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        LocalDateTime shippingDate = orderDTO.getShippingDate() == null ?
                LocalDateTime.now() : orderDTO.getShippingDate();
        if (shippingDate.isBefore(LocalDateTime.now())){
            throw new EntityNotFoundException("Date must be at least today");
        }
        order.setActive(true);
        order.setShippingDate(shippingDate);
        return null;
    }

    @Override
    public OrderDTO findById(Long id) throws OrderException {
        return null;
    }

    @Override
    public List<OrderDTO> userOrdersHistory(Long user_id) {
        return null;
    }

    @Override
    public OrderDTO placedOrder(Long order_id) throws OrderException {
        return null;
    }

    @Override
    public OrderDTO confirmedOrder(Long order_id) throws OrderException {
        return null;
    }

    @Override
    public OrderDTO shippedOrder(Long order_id) throws OrderException {
        return null;
    }

    @Override
    public OrderDTO deliveredOrder(Long order_id) throws OrderException {
        return null;
    }

    @Override
    public OrderDTO canceledOrder(Long order_id) throws OrderException {
        return null;
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return null;
    }

    @Override
    public void deleteOrder(Long order_id) throws OrderException {

    }
}
