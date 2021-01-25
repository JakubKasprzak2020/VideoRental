package pl.VideoRental.security;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ApplicationUserRole {
    ADMIN("ADMIN"),
    USER("USER");


    ApplicationUserRole(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue
    private long id;
    private String name;
}
