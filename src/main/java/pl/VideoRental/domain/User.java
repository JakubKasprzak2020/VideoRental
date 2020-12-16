package pl.VideoRental.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
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
    private String email;
    private String address;
    private UserType userType;

    @OneToOne //(mappedBy = "user")
    @JoinColumn(name = "fk_user")
    @Transient //TODO is it correct?
    private Cart cart = new Cart();

    @OneToMany(mappedBy = "user", fetch=FetchType.EAGER)
    private List<Copy> copies = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Order> orders = new ArrayList<>();

}
