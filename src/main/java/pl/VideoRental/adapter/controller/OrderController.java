package pl.VideoRental.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.VideoRental.domain.Order;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.exception.OrderDoesNotExistException;
import pl.VideoRental.useCase.port.orderPort.*;
import pl.VideoRental.util.JsonConverter;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final GetOrderFromCatalog getOrderFromCatalog;
    private final GetAllOrders getAllOrders;
    private final CreateOrderFromCartContent createOrderFromCartContent;
    private final DeleteOrder deleteOrder;
    private final UpdateOrder updateOrder;
    private final JsonConverter jsonConverter;


    @GetMapping("/admin/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAll(){
        return getAllOrders.getAll();
    }

    @GetMapping("/admin/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Order get(@PathVariable long id){
       try {
           return getOrderFromCatalog.getById(id);
       } catch (OrderDoesNotExistException exception) {
           System.out.println(exception.getMessage());
           return null;
        }
    }

    @DeleteMapping("/admin/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable long id){
        deleteOrder.deleteById(id);
    }

    @PutMapping("/admin/orders/{id}")
    public void update(@RequestBody String json, @PathVariable long id) {
        Order order = jsonConverter.getOrderFromJson(json);
        updateOrder.update(id, order);
    }

    //TODO - Spring Security first
    public Order create(User user) {
        return null;
    }
}
