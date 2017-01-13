package site.iurysouza.cinefilo.model.entities.realm;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import java.util.Date;
import lombok.Data;
import site.iurysouza.cinefilo.model.entities.mapper.RealmIntegerMapper;
import site.iurysouza.cinefilo.model.entities.pojo.Movie;

/**
 * Created by Iury Souza on 12/10/2016.
 */
@Data
@RealmClass
public class RealmMovie implements RealmModel {

  public static final String ID = "id";
  public static final String POPULARITY = "popularity";
  public static final String VOTE_AVG = "voteAverage";
  public static final String RELEASE_DATE = "releaseDate";
  public static final String VOTE_COUNT = "voteCount";
  public static final String QUERY_DATE = "queryDate";
  public static final int NOW_QUERY= 2;
  public static final int POP_QUERY = 1;
  public static final int TOP_QUERY = 0;
  public static final String QUERY_TYPE = "queryType";

  @PrimaryKey
  private Integer id;
  private Boolean adult;
  private String overview;
  private Double voteAverage;
  private Double popularity;
  private String posterPath;
  private String backdropPath;
  private Date releaseDate;
  private int queryType;
  private Integer budget;
  private String homepage;
  private String imdbId;
  private String originalLanguage;
  private String originalTitle;
  private Integer revenue;
  private Long runtime;
  private String status;
  private String tagline;
  private String title;
  private Boolean video;
  private Long voteCount;
  private Long queryDate;

  private RealmList<RealmInteger> genreIds = new RealmList<>();
  private RealmList<RealmGenre> genreList = new RealmList<>();
  private RealmList<RealmSpokenLanguage> spokenLanguageList = new RealmList<>();
  private RealmList<RealmProductionCompany> productionCompanyList = new RealmList<>();
  private RealmList<RealmProductionCountry> productionCountryList = new RealmList<>();

  public static RealmMovie valueOf(Movie movie, int queryType) {

    String originalTitle = movie.getOriginalTitle();
    String posterPath = movie.getPosterPath();
    Integer[] genreIds = movie.getGenreIds();
    Double voteAverage = movie.getVoteAverage();
    String overview = movie.getOverview();

    if (isMovieInvalid(originalTitle, posterPath, genreIds, voteAverage, overview)) return null;

    RealmMovie realmMovie = new RealmMovie();
    realmMovie.setAdult(movie.getAdult());
    realmMovie.setBackdropPath("");

    if (movie.getBackdropPath() != null) {
      realmMovie.setBackdropPath(movie.getBackdropPath());
    }

    realmMovie.setBackdropPath(movie.getBackdropPath());
    realmMovie.setId(movie.getId().intValue());
    realmMovie.setOriginalLanguage(movie.getOriginalLanguage());
    realmMovie.setOriginalTitle(originalTitle);
    realmMovie.setOverview(overview);
    realmMovie.setPopularity(movie.getPopularity());
    realmMovie.setPosterPath(posterPath);
    realmMovie.setVideo(movie.getVideo());
    realmMovie.setVoteAverage(voteAverage);
    realmMovie.setVoteCount(movie.getVoteCount());
    realmMovie.setReleaseDate(movie.getReleaseDate());
    realmMovie.setGenreIds(RealmIntegerMapper.map(genreIds));
    realmMovie.setQueryDate(System.currentTimeMillis());
    realmMovie.setQueryType(queryType);
    return realmMovie;
  }

  private static boolean isMovieInvalid(String originalTitle, String posterPath, Integer[] genreIds,
      Double voteAverage, String overview) {
    return isStringFieldInvalid(originalTitle) ||
        isStringFieldInvalid(posterPath) ||
        isStringFieldInvalid(overview) ||
        genreIds.length == 0 ||
        voteAverage == null;
  }

  private static boolean isStringFieldInvalid(String field) {
    return (field == null || field.isEmpty());
  }
}
