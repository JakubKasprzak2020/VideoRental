package pl.VideoRental.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.VideoRental.domain.Delivery;
import pl.VideoRental.useCase.exception.DeliveryDoesNotExistException;
import pl.VideoRental.useCase.port.deliveryPort.*;
import pl.VideoRental.util.JsonConverter;

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
    private final JsonConverter jsonConverter;


    @PostMapping("/admin/deliveries/{orderId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Delivery create(@PathVariable long orderId, @RequestBody String address) {
        return createDeliveryFromAnOrder.makeDelivery(orderId, address);
    }

    @DeleteMapping("/admin/deliveries/{deliveryId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable long deliveryId) {
        deleteDelivery.deleteById(deliveryId);
    }

    @PutMapping("/admin/deliveries/{deliveryId}")
    @ResponseStatus(HttpStatus.OK)
    public void deliver(@PathVariable long deliveryId) {
        deliver.deliverToUser(deliveryId);
    }

    @GetMapping("/admin/deliveries")
    @ResponseStatus(HttpStatus.OK)
    public List<Delivery> getAll() {
        return getAllDeliveries.getAll();
    }

    @GetMapping("/admin/deliveries/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Delivery get(@PathVariable long id) {
        try {
            return getDeliveryFromCatalog.getById(id);
        } catch (DeliveryDoesNotExistException exception){
            System.out.println(exception.getMessage());
            return null;
        }
    }

    @PatchMapping("/admin/deliveries/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable long id, @RequestBody String json) {
        Delivery delivery = jsonConverter.getDeliveryFromJson(json);
        updateDelivery.update(id, delivery);
    }


}
