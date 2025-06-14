package lv.venta.cbs.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "movie")
public class Movie {
	// Sheit vajag more validation un parbaudes
	
    public enum MovieStatus {
        UPCOMING,
        HIDDEN,
        BOOKABLE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Integer movieId;

    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 100)
    @Column(name = "Title", nullable = false)
    private String title;
    
    @NotBlank(message = "Genre is required")
    @Pattern(regexp = "[A-Za-z -!?]{2,50}")
    @Column(name = "Genre", nullable = false)
    private String genre;
    
    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Column(name = "DurationInMinutes", nullable = false)
    private int durationInMinutes;
    
    @NotBlank(message = "Age rating is required")
    @Pattern(regexp = "^(G|PG|PG-13|R|NC-17)$", message = "Age rating must be one of: G, PG, PG-13, R, NC-17")
    @Column(name = "AgeRating", nullable = false)
    private String ageRating;
    
    @NotBlank(message = "Description is required")
    @Size(max = 1000)
    @Column(name = "Description", nullable = false, length = 1000)
    private String description;
    
    @NotNull(message = "Release date is required")
    @Column(name = "ReleaseDate", nullable = false)
    private LocalDate releaseDate;
    
    @Column(name = "Cast")
    private String cast;
    
    @Column(name = "PosterUrl")
    private String posterUrl;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    private MovieStatus status = MovieStatus.UPCOMING;

    // "Any operation performed on a Movie will also be automatically cascaded (applied) to its related Showtime objects."
    // - Ja izdzesh movie tad izdzeshas arii showtimes tai movie.
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Showtime> showtimes = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<MovieReview> reviews = new ArrayList<>();
    
    public Movie(String title, String description, int durationInMinutes, String genre, String ageRating, LocalDate releaseDate, String cast, String posterUrl, String status) {
        this.title = title;
        this.description = description;
        this.durationInMinutes = durationInMinutes;
        this.genre = genre;
        this.ageRating = ageRating;
        this.releaseDate = releaseDate;
        this.cast = cast;
        this.posterUrl = posterUrl;
        this.status = status != null ? MovieStatus.valueOf(status) : MovieStatus.UPCOMING;
    }
    

    public void setTitle(String title) {
        this.title = (title != null && title.length() <= 100) ? title : "Untitled";
    }

    public void setGenre(String genre) {
        this.genre = (genre != null && genre.matches("[A-Za-z ]{2,30}")) ? genre : "Unknown";
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = (durationInMinutes > 0) ? durationInMinutes : 90;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = (ageRating != null && ageRating.matches("G|PG|PG-13|R|NC-17")) ? ageRating : "NR";
    }

    public void setDescription(String description) {
        this.description = (description != null && description.length() <= 1000) ? description : "";
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = (releaseDate != null && releaseDate.isAfter(LocalDate.now())) ? releaseDate : LocalDate.now().plusDays(1);
    }

    public void setCast(String cast) {
        this.cast = (cast != null && cast.length() <= 300) ? cast : "Unknown Cast";
    }

    public void setPosterUrl(String posterUrl) {
        //if (posterUrl.matches("https?://.+\\.(jpg|png|jpeg)")) { // realistiski runajot ir parak daudz variantu lai tam izmantotu regex
        this.posterUrl = posterUrl;
    }

    public void toggleStatus() {
        this.status = (this.status == MovieStatus.UPCOMING) ? MovieStatus.BOOKABLE : MovieStatus.UPCOMING;
    }
}
