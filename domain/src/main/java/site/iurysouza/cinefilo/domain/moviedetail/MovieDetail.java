package site.iurysouza.cinefilo.domain.moviedetail;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
  private Integer budget;
  private List<Integer> genreIdList;
  private String homepage;
  private Integer id;
  private String imdbId;
  private String originalLanguage;
  private String originalTitle;
  private String overview;
  private Double popularity;
  private String posterPath;
  private Date releaseDate;
  private Integer revenue;
  private Integer runtime;
  private String status;
  private String tagline;
  private String title;
  private Boolean video;
  private Double voteAverage;
  private Integer voteCount;
  private String trailerId;
  private HashMap<String, Integer> productionCompanyList;
  private HashMap<String, String> productionCountryList;
  private HashMap<String, String> spokenLanguageList;
  private HashMap<String, Integer> genreList;
}
