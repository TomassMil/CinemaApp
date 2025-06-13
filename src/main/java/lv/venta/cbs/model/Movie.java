package lv.venta.cbs.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Movie {
	// Sheit vajag more validation un parbaudes
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MovieId")
    private int movieId;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Title")
    private String title;
    
    @NotNull
    @Pattern(regexp = "[A-Za-z -!?]{2,50}")
    @Column(name = "Genre")
    private String genre;
    
    @Min(1)
    @Column(name = "DurationInMinutes")
    private int durationInMinutes;
    
    @NotNull
    @Pattern(regexp = "G|PG|PG-13|R|NC-17")
    @Column(name = "AgeRating")
    private String ageRating;
    
    @Size(max = 1000)
    @Column(name = "Description")
    private String description;
    
    @NotNull
    // @Future - jabut nakotnee bet vai sheit vajadzigs?
    @Column(name = "ReleaseDate")
    private LocalDateTime releaseDate;
    
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "Cast")
    private String cast;
    
    @Pattern(regexp = "https?://.+\\.(jpg|png|jpeg)")
    @Column(name = "PosterUrl")
    private String posterUrl;

    // "Any operation performed on a Movie will also be automatically cascaded (applied) to its related Showtime objects."
    // - Ja izdzesh movie tad izdzeshas arii showtimes tai movie.
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private ArrayList<Showtime> showtimes;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private ArrayList<MovieReview> reviews;
    
    public Movie(String title, String genre, int durationInMinutes, String ageRating,
            String description, LocalDateTime releaseDate, String cast, String posterUrl) {
	   setTitle(title);
	   setGenre(genre);
	   setDurationInMinutes(durationInMinutes);
	   setAgeRating(ageRating);
	   setDescription(description);
	   setReleaseDate(releaseDate);
	   setCast(cast);
	   setPosterUrl(posterUrl);
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

    public void setReleaseDate(LocalDateTime releaseDate) {
        this.releaseDate = (releaseDate != null && releaseDate.isAfter(LocalDateTime.now())) ? releaseDate : LocalDateTime.now().plusDays(1);
    }

    public void setCast(String cast) {
        this.cast = (cast != null && cast.length() <= 300) ? cast : "Unknown Cast";
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = (posterUrl != null && posterUrl.matches("https?://.+\\.(jpg|png|jpeg)")) ? posterUrl : null;
    }
    
}
