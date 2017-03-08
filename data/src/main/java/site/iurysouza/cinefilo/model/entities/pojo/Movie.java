package site.iurysouza.cinefilo.model.entities.pojo;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Builder;
import site.iurysouza.cinefilo.domain.medialist.WatchMedia;

import static site.iurysouza.cinefilo.util.MappingUtils.isEmptyString;
import static site.iurysouza.cinefilo.util.MappingUtils.isInvalid;

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
  private Date releaseDate;
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
  @SerializedName("videos")
  private Videos videos;

  public static WatchMedia valueOf(Movie movie) {
    Integer genreValue = getGenreIdValue(movie);
    if (isInvalid(movie)) return null;

    return WatchMedia
            .builder()
            .id(movie.getId())
            .voteAverage(movie.getVoteAverage())
            .releaseDate(movie.getReleaseDate())
            .overview(movie.getOverview())
            .backdropPath(movie.getBackdropPath())
            .posterPath(movie.getPosterPath())
            .name(movie.getOriginalTitle())
            .genre(genreValue)
            .build();
  }

  public static List<WatchMedia> valueOfMovieList(List<Movie> movieList) {
    List<WatchMedia> mediaList = new ArrayList<>();
    if (movieList.isEmpty()) {
      return mediaList;
    }

    for (Movie movie : movieList) {
      WatchMedia watchMedia = valueOf(movie);
      if (watchMedia != null) {
        mediaList.add(watchMedia);
      }
    }
    return mediaList;
  }

  private static Integer getGenreIdValue(Movie movie) {
    Integer[] genreIdList = movie.getGenreIds();
    Integer genreId = 0;
    if (genreIdList.length > 0) {
      genreId = genreIdList[0];
    }
    return genreId;
  }
  public static boolean isMovieInvalid(Movie movie) {
    String originalTitle = movie.getOriginalTitle();
    String posterPath = movie.getPosterPath();
    Integer[] genreIds = movie.getGenreIds();
    Double voteAverage = movie.getVoteAverage();
    String overview = movie.getOverview();
    Date releaseDate = movie.getReleaseDate();
    List<Genre> genreList = movie.getGenreList();

    return isEmptyString(originalTitle) ||
            isEmptyString(posterPath) ||
            isEmptyString(overview) ||
            (genreIds == null && genreList == null) ||
            voteAverage == null ||
            releaseDate == null;
  }

}
