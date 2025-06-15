package lv.venta.cbs.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class MovieReview {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReviewId")
    private int reviewId;

    @Min(1)
    @Max(10)
    @Column(name = "Rating")
    private int rating;
    
    @NotNull
    @Size(min = 5, max = 1000)
    @Column(name = "Comment")
    private String comment;

    @PastOrPresent
    @Column(name = "ReviewDate")
    private LocalDateTime reviewDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "MovieId", nullable = false)
    private Movie movie;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "UserId", nullable = false)
    private User user;
    
    public MovieReview(int rating, String comment, LocalDateTime reviewDate, Movie movie, User user) {
        setRating(rating);
        setComment(comment);
        setReviewDate(reviewDate);
        setMovie(movie);
        setUser(user);
    }
    
    public void setRating(int rating) {
        this.rating = (rating >= 1 && rating <= 10) ? rating : 5; // Default to 5 if invalid
    }
    
    
    public void setComment(String comment) {
        if (comment != null && comment.length() >= 5 && comment.length() <= 1000) {
            this.comment = comment;
        } else {
            this.comment = "No detailed comment provided.";
        }
    }
    
    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = (reviewDate != null && !reviewDate.isAfter(LocalDateTime.now()))
                ? reviewDate
                : LocalDateTime.now();
    }

    public void setMovie(Movie movie) {
    	if(movie != null) {
    		this.movie = movie;
    	} // default movie case?
    	
    }
}
