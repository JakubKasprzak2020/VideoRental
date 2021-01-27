package pl.VideoRental.authentication;

import lombok.*;
import pl.VideoRental.security.ApplicationUserRole;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany
    private Set<ApplicationUserRole> roles = new HashSet<>();





}
