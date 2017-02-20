package site.iurysouza.cinefilo.domain.moviedetail;

import java.util.Date;
import lombok.Data;
import lombok.experimental.Builder;

/**
 * Created by Iury Souza on 20/02/2017.
 */
@Data
@Builder
public class MovieDetail {
  private Boolean adult;
  private String backdropPath;
  private Long budget;
  private Integer[] genreIds;
  private String homepage;
  private Long id;
  private String imdbId;
  private String originalLanguage;
  private String originalTitle;
  private String overview;
  private Double popularity;
  private String posterPath;
  private Date releaseDate;
  private Long revenue;
  private Long runtime;
  private String status;
  private String tagline;
  private String title;
  private Boolean video;
  private Double voteAverage;
  private Long voteCount;


}
