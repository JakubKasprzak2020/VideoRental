package pl.VideoRental.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
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
    private Date releaseDate;
    private Genre genre;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "movie")
    private List<Copy> copies;


}
