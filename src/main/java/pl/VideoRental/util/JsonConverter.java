package pl.VideoRental.util;

import com.google.gson.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.Delivery;
import pl.VideoRental.domain.Order;
import pl.VideoRental.domain.User;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class JsonConverter {

    private Gson getGson() {
        return new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(LocalDate.class,
                        (JsonSerializer<LocalDate>) (localDate, type, context)
                                -> new JsonPrimitive(localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                .registerTypeAdapter(LocalDate.class,
                        (JsonDeserializer<LocalDate>) (jsonElement, type, context)
                                -> LocalDate.parse(jsonElement.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .create();
    }

    public User getUserFromJson(String json) {
      return getGson().fromJson(json, User.class);
    }

    public Copy getCopyFromJson(String json){
        return getGson().fromJson(json, Copy.class);
    }

    public Order getOrderFromJson(String json) {
        return getGson().fromJson(json, Order.class);
    }


    //TODO problem with value of isDelivered - temporary solution
    public Delivery getDeliveryFromJson(String json) {
        Delivery delivery = getGson().fromJson(json, Delivery.class);
       if (json.contains("true")){
           delivery.setDelivered(true);
       }
        return delivery;
    }

}
