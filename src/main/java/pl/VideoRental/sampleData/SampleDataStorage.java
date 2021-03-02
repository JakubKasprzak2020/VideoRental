package pl.VideoRental.sampleData;

import pl.VideoRental.domain.Genre;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.domain.UserSignInData;

import java.time.LocalDate;

public class SampleDataStorage {

    public static final Movie MOVIE_1 = Movie.builder()
            .title("Inglourious Basterds")
            .genre(Genre.ACTION)
            .description("Second World War in Europe...")
            .releaseDate(LocalDate.of(2009, 1, 1))
            .build();

    public static final Movie MOVIE_2 = Movie.builder()
            .title("E.T.")
            .genre(Genre.SCI_FI)
            .releaseDate(LocalDate.of(1982, 1, 1))
            .description("An Alien appears on the Earth...")
            .build();

    public static final Movie MOVIE_3 = Movie.builder()
            .title("Scream")
            .genre(Genre.HORROR)
            .releaseDate(LocalDate.of(1996, 1, 1))
            .description("A masked murderer attacked!")
            .build();

    public static final Movie MOVIE_4 = Movie.builder()
            .title("Spirited Away")
            .genre(Genre.CARTOON)
            .releaseDate(LocalDate.of(2001, 1, 1))
            .description("Young girl Chihiro starts an amazing journey")
            .build();

    public static final Movie MOVIE_5 = Movie.builder()
            .title("Liar, Liar")
            .genre(Genre.COMEDY)
            .releaseDate(LocalDate.of(1997, 1, 1))
            .description("Jim Carrey as a lawyer who can't lie.")
            .build();

    public static final Movie MOVIE_6 = Movie.builder()
            .title("Sherlock Holmes")
            .genre(Genre.CRIME_STORY)
            .releaseDate(LocalDate.of(2009, 1, 1))
            .description("Adventures of the most famous detective in the world.")
            .build();

    public static final Movie MOVIE_7 = Movie.builder()
            .title("Dogville")
            .genre(Genre.DRAMA)
            .releaseDate(LocalDate.of(2003, 1, 1))
            .description("Peaceful town somewhere in USA.")
            .build();

    public static final Movie MOVIE_8 = Movie.builder()
            .title("Vidocq")
            .genre(Genre.CRIME_STORY)
            .releaseDate(LocalDate.of(2001, 1, 1))
            .description("Mirror masked man overrun in  Paris.")
            .build();

    public static final Movie MOVIE_9 = Movie.builder()
            .title("Barry Lyndon")
            .genre(Genre.HISTORICAL)
            .releaseDate(LocalDate.of(1975, 1, 1))
            .description("Epic story from the XVIII century.")
            .build();

    public static final Movie MOVIE_10 = Movie.builder()
            .title("Friday the 13th")
            .genre(Genre.HORROR)
            .releaseDate(LocalDate.of(1980, 1, 1))
            .description("Jason is coming!")
            .build();

    public static final Movie MOVIE_11 = Movie.builder()
            .title("LA LA LAND")
            .genre(Genre.MUSICAL)
            .releaseDate(LocalDate.of(2016, 1, 1))
            .description("A jazzman falls in love with an actress.")
            .build();

    public static final Movie MOVIE_12 = Movie.builder()
            .title("Rocky")
            .genre(Genre.DRAMA)
            .releaseDate(LocalDate.of(1979, 1, 1))
            .description("Rocky Balboa is an amateur boxer. One day...")
            .build();

    public static final Movie MOVIE_13 = Movie.builder()
            .title("Alien")
            .genre(Genre.SCI_FI)
            .releaseDate(LocalDate.of(1979, 1, 1))
            .description("There are seven members of the Nostromo space ship...")
            .build();

    public static final Movie MOVIE_14 = Movie.builder()
            .title("The Good, the Bad and the Ugly ")
            .genre(Genre.WESTERN)
            .releaseDate(LocalDate.of(1966, 1, 1))
            .description("Story about three cowboys.")
            .build();

    public static final Movie MOVIE_15 = Movie.builder()
            .title("Leon the professional")
            .genre(Genre.ACTION)
            .releaseDate(LocalDate.of(1994, 1, 1))
            .description("Professional murderer met young girl called Matilda.")
            .build();

    public static final Movie MOVIE_16 = Movie.builder()
            .title("Shrek")
            .genre(Genre.CARTOON)
            .releaseDate(LocalDate.of(2001, 1, 1))
            .description("An ugly ogr need to rescue a princess from a tower.")
            .build();

    public static final Movie MOVIE_17 = Movie.builder()
            .title("Big Lebowski")
            .genre(Genre.COMEDY)
            .releaseDate(LocalDate.of(1998, 1, 1))
            .description("Probably the best movie in the world.")
            .build();

    public static final Movie MOVIE_18 = Movie.builder()
            .title("The Maltese Falcon")
            .genre(Genre.CRIME_STORY)
            .releaseDate(LocalDate.of(1941, 1, 1))
            .description("Private detective tries to solve the case of disappearance of precious statue.")
            .build();

    public static final Movie MOVIE_19 = Movie.builder()
            .title("Match point")
            .genre(Genre.DRAMA)
            .releaseDate(LocalDate.of(2005, 1, 1))
            .description("Chris Wilton ends his career as a tennis player...")
            .build();

    public static final Movie MOVIE_20 = Movie.builder()
            .title("Shakespeare in Love")
            .genre(Genre.HISTORICAL)
            .releaseDate(LocalDate.of(1998, 1, 1))
            .description("William Shakespeare works on his new play titled Romeo and Juliet.")
            .build();

   public static final UserSignInData USER_SIGN_IN_DATA_1 = UserSignInData.builder()
            .name("Quentin")
            .lastName("Tarantino")
            .email("quentin@quentin.com")
            .password("password")
            .address("Hollywood")
            .build();

    public static final UserSignInData USER_SIGN_IN_DATA_2 = UserSignInData.builder()
            .name("Queen")
            .lastName("Elizabeth")
            .email("queen@elizabeth.gb")
            .password("longlive")
            .address("London")
            .build();



}
