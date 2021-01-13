package pl.VideoRental.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import pl.VideoRental.domain.Delivery;
import pl.VideoRental.domain.Order;
import pl.VideoRental.useCase.port.deliveryPort.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DeliveryController {

    private final CreateDeliveryFromAnOrder createDeliveryFromAnOrder;
    private final DeleteDelivery deleteDelivery;
    private final Deliver deliver;
    private final GetAllDeliveries getAllDeliveries;
    private final GetDeliveryFromCatalog getDeliveryFromCatalog;
    private final UpdateDelivery updateDelivery;



    public Delivery create(Order order){
        return null;
    }

    public void delete(long id){
    }

    public void deliver(long id){
    }

    public List<Delivery> getAll(){
        return null;
    }

    public Delivery get(long id) {
        return null;
    }

    public void update(long id, Delivery delivery){
        
    }


}
