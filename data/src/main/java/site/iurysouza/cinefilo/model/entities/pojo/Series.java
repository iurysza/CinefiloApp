package site.iurysouza.cinefilo.model.entities.pojo;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.List;
import javax.annotation.Generated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Builder;

@Generated("net.hexar.json2pojo")
@Data
@Builder
@SuppressWarnings("unused")
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Series {

  @SerializedName("backdrop_path")
  private String backdropPath;
  @SerializedName("created_by")
  private List<Person> createdByList = null;
  @SerializedName("episode_run_time")
  private List<Integer> episodeRunTime = null;
  @SerializedName("first_air_date")
  private Date firstAirDate;
  @SerializedName("genre_ids")
  private Integer[] genres = null;
  @SerializedName("homepage")
  private String homepage;
  @SerializedName("id")
  private long id;
  @SerializedName("in_production")
  private Boolean inProduction;
  @SerializedName("languages")
  private List<String> languages = null;
  @SerializedName("last_air_date")
  private Date lastAirDate;
  @SerializedName("name")
  private String name;
  @SerializedName("networks")
  private List<Network> networks = null;
  @SerializedName("number_of_episodes")
  private Integer numberOfEpisodes;
  @SerializedName("number_of_seasons")
  private Integer numberOfSeasons;
  @SerializedName("origin_country")
  private List<String> originCountry = null;
  @SerializedName("original_language")
  private String originalLanguage;
  @SerializedName("original_name")
  private String originalName;
  @SerializedName("overview")
  private String overview;
  @SerializedName("popularity")
  private Double popularity;
  @SerializedName("poster_path")
  private String posterPath;
  @SerializedName("seasons")
  private List<Season> seasons = null;
  @SerializedName("status")
  private String status;
  @SerializedName("type")
  private String type;
  @SerializedName("vote_average")
  private Double voteAverage;
  @SerializedName("vote_count")
  private Integer voteCount;
  @SerializedName("genres")
  private List<Genre> genreList;
  @SerializedName("production_companies")
  private List<ProductionCompany> productionCompanyList;
}
