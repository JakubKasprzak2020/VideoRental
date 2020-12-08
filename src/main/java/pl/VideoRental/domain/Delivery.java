package pl.VideoRental.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String address;

    private boolean wasDelivered;

    @OneToOne
    private Order order;


}
