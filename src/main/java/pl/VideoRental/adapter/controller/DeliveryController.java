package pl.VideoRental.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.VideoRental.domain.Delivery;
import pl.VideoRental.domain.User;
import pl.VideoRental.domain.UserType;
import pl.VideoRental.mail.EmailContent;
import pl.VideoRental.mail.EmailContentCreator;
import pl.VideoRental.mail.EmailService;
import pl.VideoRental.useCase.exception.DeliveryDoesNotExistException;
import pl.VideoRental.useCase.exception.OrderDoesNotExistException;
import pl.VideoRental.useCase.port.deliveryPort.*;
import pl.VideoRental.useCase.port.orderPort.GetOrderFromCatalog;
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
    private final GetOrderFromCatalog getOrderFromCatalog;
    private final EmailContentCreator emailContentCreator;
    private final EmailService emailService;


    @PostMapping("/admin/deliveries/{orderId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Delivery create(@PathVariable long orderId, @RequestBody String address) throws OrderDoesNotExistException {
       UserType userTypeBefore = getOrderFromCatalog.getById(orderId).getUser().getUserType();
       Delivery delivery = createDeliveryFromAnOrder.makeDelivery(orderId, address);
       User user = getOrderFromCatalog.getById(orderId).getUser();
       UserType userTypeAfter = user.getUserType();
        if (userTypeAfter != userTypeBefore){
            EmailContent emailContent = emailContentCreator.getContentForUserTypePromotion(user);
            emailService.sendEmail(user.getEmail(), emailContent);
        }
        return delivery;
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
