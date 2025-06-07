package lv.venta.cbs.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movieId;

    private String title;
    private String genre;
    private int durationInMinutes;
    private String ageRating;
    private String description;
    private LocalDateTime releaseDate;
    private String cast;
    private String posterUrl;

    @OneToMany(mappedBy = "movie")
    private List<Showtime> showtimes;

    @OneToMany(mappedBy = "movie")
    private List<MovieReview> reviews;
}
