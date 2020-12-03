package pl.VideoRental.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String lastName;
    private UserType userType;

    @OneToOne(cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany (fetch = FetchType.EAGER, mappedBy = "user")
    private List<Copy> copies;

    @OneToMany (fetch = FetchType.EAGER, mappedBy = "user")
    private List<Order> orders;

    @OneToMany (fetch = FetchType.EAGER, mappedBy = "user")
    private List<Delivery> deliveries;

}
