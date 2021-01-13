package pl.VideoRental.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.VideoRental.domain.Order;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.exception.OrderDoesNotExistException;
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


    @GetMapping("/api/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAll(){
        return getAllOrders.getAll();
    }

    @GetMapping("/api/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Order get(@PathVariable long id){
       try {
           return getOrderFromCatalog.getById(id);
       } catch (OrderDoesNotExistException exception) {
           System.out.println(exception.getMessage());
           return null;
        }
    }

    @DeleteMapping("/api/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable long id){
        deleteOrder.deleteById(id);
    }

    @PutMapping("/api/orders/{id}")
    public void update(@PathVariable long id, @RequestBody Order order){
        updateOrder.update(id, order);
    }

    //TODO - Spring Security first
    public Order create(User user) {
        return null;
    }
}
