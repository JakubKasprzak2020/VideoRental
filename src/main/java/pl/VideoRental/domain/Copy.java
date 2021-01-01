package pl.VideoRental.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private int rentalDays;

    private LocalDate rentalDate;

    private boolean isAvailable = true;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    @JsonManagedReference
    private User user;


}
