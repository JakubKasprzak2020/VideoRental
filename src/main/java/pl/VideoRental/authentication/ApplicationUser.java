package pl.VideoRental.authentication;

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
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username; //TODO - it should be unique

    private String password;

    @ElementCollection (fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();



}
