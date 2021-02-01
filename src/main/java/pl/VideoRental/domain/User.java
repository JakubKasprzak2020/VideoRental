package pl.VideoRental.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import pl.VideoRental.authentication.ApplicationUser;

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
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String lastName;
    private String email; //TODO - this field should be unique (it's username for ApplicationUser)
    private String address;
    private UserType userType;


    @OneToMany(mappedBy = "user", fetch=FetchType.EAGER)
    @JsonBackReference
    private List<Copy> copies = new ArrayList<>();


}
