package pl.VideoRental.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(unique = true)
    private String title;

    private String description;
    private LocalDate releaseDate;
    private Genre genre;

    @OneToMany(mappedBy = "movie", orphanRemoval = true, cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
    @JsonIgnore
    private List<Copy> copies = new ArrayList<>();


}
