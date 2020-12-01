package pl.VideoRental.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Movie {

    @Id
    @GeneratedValue
    private long id;

    private String title;
    private String description;
    private Date relaseDate;
    private Genre genre;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie")
    private List<Copy> copies;


}
