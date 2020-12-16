package pl.VideoRental.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Copy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    int rentalDays;

    LocalDate rentalDate;

    boolean isAvailable = true;

    @ManyToOne
    @JsonManagedReference
    private Movie movie;

    @ManyToOne
    @JsonManagedReference
    private User user;


}
