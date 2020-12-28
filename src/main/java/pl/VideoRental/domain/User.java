package pl.VideoRental.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    /**
     * copies - a list of movie copies which are current rented by user
     * orders - a list of all orders that user have ever made
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String lastName;
    private String password;
    private String email;
    private String address;
    private UserType userType;


    @OneToMany(mappedBy = "user", fetch=FetchType.EAGER)
    @JsonBackReference
    private List<Copy> copies = new ArrayList<>();

 /*   @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.PERSIST)
    @JsonBackReference
    private List<Order> orders = new ArrayList<>();*/

}
