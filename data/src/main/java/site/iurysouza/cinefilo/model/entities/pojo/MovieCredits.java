package site.iurysouza.cinefilo.model.entities.pojo;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;
import lombok.experimental.Builder;
import site.iurysouza.cinefilo.domain.moviedetail.Credits;

@Data
@Builder
public class MovieCredits {
  @SerializedName("cast")
  private List<MovieCast> movieCast;
  @SerializedName("crew")
  private List<MovieCrew> movieCrew;
  @SerializedName("id")
  private Long id;

  public static Credits valueOf(MovieCredits movieCredits) {
    return new Credits.CreditsBuilder()
        .cast(MovieCast.valueOf(movieCredits.getMovieCast()))
        .crew(MovieCrew.valueOf(movieCredits.getMovieCrew()))
        .id(movieCredits.getId())
        .build();
  }
}

