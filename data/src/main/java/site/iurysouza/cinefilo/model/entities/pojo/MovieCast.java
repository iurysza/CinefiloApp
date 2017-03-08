package site.iurysouza.cinefilo.model.entities.pojo;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.experimental.Builder;
import site.iurysouza.cinefilo.domain.moviedetail.Cast;

@Data
@Builder
public class MovieCast {

  @SerializedName("cast_id")
  private Long castId;
  @SerializedName("character")
  private String character;
  @SerializedName("credit_id")
  private String creditId;
  @SerializedName("id")
  private Long id;
  @SerializedName("name")
  private String name;
  @SerializedName("order")
  private Long order;
  @SerializedName("profile_path")
  private String profilePath;

  public static List<Cast> valueOf(List<MovieCast> movieCastList) {
    List<Cast> castList = new ArrayList<>();
    if (movieCastList != null && !movieCastList.isEmpty()) {
      for (MovieCast movieCast : movieCastList) {
        castList.add(valueOf(movieCast));
      }
    }
    return castList;
  }

  public static Cast valueOf(MovieCast movieCast) {
    return new Cast.CastBuilder()
        .id(movieCast.getId())
        .character(movieCast.getCharacter())
        .castId(movieCast.castId)
        .name(movieCast.getName())
        .order(movieCast.getOrder())
        .creditId(movieCast.getCreditId())
        .profilePath(movieCast.getProfilePath())
        .build();
  }
}
