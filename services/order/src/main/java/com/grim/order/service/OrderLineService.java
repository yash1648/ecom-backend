package com.grim.order.service;

import com.grim.order.dto.OrderLineRequest;
import com.grim.order.dto.OrderLineResponse;
import com.grim.order.mapper.OrderLineMapper;
import com.grim.order.repository.OrderLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper orderLineMapper;

    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {

        var order=orderLineMapper.toOrderLine(orderLineRequest);

        return orderLineRepository.save(order).getId();


    }

    public List<OrderLineResponse> findByOrderId(Integer orderId) {
        return orderLineRepository.findAllByOrderId(orderId)
                .stream()
                .map(orderLineMapper::toOrderLineResponse)
                .collect(Collectors.toList());
    }
}
