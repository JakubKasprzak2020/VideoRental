package pl.VideoRental.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;
    private String description;
    private LocalDate releaseDate;
    private Genre genre;

    @OneToMany(mappedBy = "movie", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Copy> copies = new ArrayList<>();


}
