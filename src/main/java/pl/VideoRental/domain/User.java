package pl.VideoRental.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String lastName;
    private String password;
    private UserType userType;

    @OneToOne(cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Copy> copies;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Order> orders;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Delivery> deliveries;

}
