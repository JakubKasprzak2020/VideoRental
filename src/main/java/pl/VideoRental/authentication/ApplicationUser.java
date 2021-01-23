package pl.VideoRental.authentication;

import lombok.*;
import pl.VideoRental.security.ApplicationUserRole;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ApplicationUser {

    @Id
    private long id;

    private String username;

    private String password;

    private List<ApplicationUserRole> roles;





}
