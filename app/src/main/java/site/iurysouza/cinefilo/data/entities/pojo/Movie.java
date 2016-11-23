package site.iurysouza.cinefilo.data.entities.pojo;

import com.google.gson.annotations.SerializedName;
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
public class Movie {

  @SerializedName("adult")
  private Boolean adult;
  @SerializedName("backdrop_path")
  private String backdropPath;
  @SerializedName("budget")
  private Long budget;
  @SerializedName("genre_ids")
  private Integer[] genreIds;
  @SerializedName("homepage")
  private String homepage;
  @SerializedName("id")
  private Long id;
  @SerializedName("imdb_id")
  private String imdbId;
  @SerializedName("original_language")
  private String originalLanguage;
  @SerializedName("original_title")
  private String originalTitle;
  @SerializedName("overview")
  private String overview;
  @SerializedName("popularity")
  private Double popularity;
  @SerializedName("poster_path")
  private String posterPath;
  @SerializedName("release_date")
  private String releaseDate;
  @SerializedName("revenue")
  private Long revenue;
  @SerializedName("runtime")
  private Long runtime;
  @SerializedName("status")
  private String status;
  @SerializedName("tagline")
  private String tagline;
  @SerializedName("title")
  private String title;
  @SerializedName("video")
  private Boolean video;
  @SerializedName("vote_average")
  private Double voteAverage;
  @SerializedName("vote_count")
  private Long voteCount;

  @SerializedName("genres")
  private List<Genre> genreList;
  @SerializedName("production_companies")
  private List<ProductionCompany> productionCompanyList;
  @SerializedName("production_countries")
  private List<ProductionCountry> productionCountryList;
  @SerializedName("spoken_languages")
  private List<SpokenLanguage> spokenLanguageList;
}
