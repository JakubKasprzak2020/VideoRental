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


    @Id
    @GeneratedValue
    private long id;
    private String name;

   ApplicationUserRole(String name) {
        this.name = name;
    }

    public String hello(){
       return "hello";
    }

}
