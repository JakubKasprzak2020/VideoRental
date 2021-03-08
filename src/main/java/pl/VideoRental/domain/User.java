package pl.VideoRental.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import pl.VideoRental.authentication.ApplicationUser;

import javax.persistence.*;
import java.math.BigDecimal;
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
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String address;
    private UserType userType;
    private BigDecimal amountSpent;


    @OneToMany(mappedBy = "user", fetch=FetchType.EAGER)
    @JsonBackReference
    private List<Copy> copies = new ArrayList<>();


}
