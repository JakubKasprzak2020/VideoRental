package pl.VideoRental.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String address;

    private boolean isDelivered = false;

    @OneToOne
    @JsonManagedReference
    private Order order;


}
