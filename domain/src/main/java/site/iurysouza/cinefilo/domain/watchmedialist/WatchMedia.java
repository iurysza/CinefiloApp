package site.iurysouza.cinefilo.domain.watchmedialist;

import java.util.Date;
import lombok.Data;
import lombok.experimental.Builder;

/**
 * Created by Iury Souza on 06/01/2017.
 */
@Data
@Builder
public class WatchMedia {
  private long id;
  private String overview;
  private Double voteAverage;
  private String posterPath;
  private String name;
  private Integer genre;
  private Date releaseDate;
  private String backdropPath;


}
