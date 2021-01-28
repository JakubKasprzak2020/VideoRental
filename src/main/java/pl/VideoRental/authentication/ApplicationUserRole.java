package pl.VideoRental.authentication;

import lombok.*;
import org.springframework.security.core.Transient;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ApplicationUserRole {
    ADMIN("ADMIN"),
    USER("USER");

    private String name;


}
