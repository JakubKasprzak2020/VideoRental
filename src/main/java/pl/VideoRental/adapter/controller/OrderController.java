package pl.VideoRental.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import pl.VideoRental.domain.Order;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.port.orderPort.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final GetOrderFromCatalog getOrderFromCatalog;
    private final GetAllOrders getAllOrders;
    private final CreateOrderFromCartContent createOrderFromCartContent;
    private final DeleteOrder deleteOrder;
    private final UpdateOrder updateOrder;


    public List<Order> getAll(){
        return null;
    }

    public Order get(long id){
        return null;
    }

    public void delete(long id){

    }

    public void update(long id, Order order){

    }

    //TODO - Spring Security first
    public Order create(User user) {
        return null;
    }
}
