package site.iurysouza.cinefilo.model.entities.realm;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import lombok.Data;
import site.iurysouza.cinefilo.domain.moviedetail.MovieDetail;
import site.iurysouza.cinefilo.domain.medialist.WatchMedia;
import site.iurysouza.cinefilo.model.entities.pojo.Movie;
import site.iurysouza.cinefilo.util.MappingUtils;

import static site.iurysouza.cinefilo.model.entities.pojo.Movie.isMovieInvalid;
import static site.iurysouza.cinefilo.util.MappingUtils.getIntValueSafely;

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
  public static final String QUERY_TYPE = "queryType";

  public static final int POP_QUERY = 1;
  public static final int TOP_QUERY = 0;
  public static final int NOW_QUERY = 2;
  public static final int GENRE_QUERY = 3;
  public static final int DEFAULT_QUERY = 4;

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
  private Integer runtime;
  private String status;
  private String tagline;
  private String title;
  private Boolean video;
  private Integer voteCount;
  private Long queryDate;

  private RealmList<RealmInteger> genreIds = new RealmList<>();
  private RealmList<RealmGenre> genreList = new RealmList<>();
  private RealmList<RealmSpokenLanguage> spokenLanguageList = new RealmList<>();
  private RealmList<RealmProductionCompany> productionCompanyList = new RealmList<>();
  private RealmList<RealmProductionCountry> productionCountryList = new RealmList<>();

  public static RealmMovie valueOf(Movie movie, int queryType) {
    RealmMovie realmMovie = new RealmMovie();

    if (isMovieInvalid(movie)) {
      return realmMovie;
    }
    String backdropPath = movie.getBackdropPath();
    if (backdropPath == null) {
      movie.setBackdropPath("");
    }

    realmMovie.setId((getIntValueSafely(movie.getId())));
    realmMovie.setVoteCount(getIntValueSafely(movie.getVoteCount()));
    realmMovie.setBudget(getIntValueSafely(movie.getBudget()));
    realmMovie.setRevenue(getIntValueSafely(movie.getRevenue()));
    realmMovie.setRuntime(getIntValueSafely(movie.getRuntime()));
    realmMovie.setAdult(movie.getAdult());
    realmMovie.setBackdropPath(backdropPath);
    realmMovie.setOriginalTitle(movie.getOriginalTitle());
    realmMovie.setOverview(movie.getOverview());
    realmMovie.setPosterPath(movie.getPosterPath());
    realmMovie.setVoteAverage(movie.getVoteAverage());
    realmMovie.setImdbId(movie.getImdbId());
    realmMovie.setStatus(movie.getStatus());
    realmMovie.setHomepage(movie.getHomepage());
    realmMovie.setTagline(movie.getTagline());
    realmMovie.setOriginalLanguage(movie.getOriginalLanguage());
    realmMovie.setPopularity(movie.getPopularity());
    realmMovie.setVideo(movie.getVideo());
    realmMovie.setReleaseDate(movie.getReleaseDate());

    realmMovie.setSpokenLanguageList(RealmSpokenLanguage.valueOf(movie.getSpokenLanguageList()));
    realmMovie.setProductionCompanyList(
            RealmProductionCompany.valueOf(movie.getProductionCompanyList()));
    realmMovie.setProductionCountryList(
            RealmProductionCountry.valueOf(movie.getProductionCountryList()));
    realmMovie.setGenreIds(RealmInteger.map(movie.getGenreIds()));
    realmMovie.setGenreList(RealmGenre.valueOf(movie.getGenreList()));
    realmMovie.setQueryDate(System.currentTimeMillis());

    if (queryType != DEFAULT_QUERY) {
      realmMovie.setQueryType(queryType);
    }

    return realmMovie;
  }


  private static Integer getGenreIdValue(RealmMovie movie) {
    RealmList<RealmInteger> genreIds = movie.getGenreIds();
    Integer genreValue = -1;
    if (!genreIds.isEmpty()) {
      genreValue = genreIds.first().getValue();
    }
    return genreValue;
  }

  private static List<Integer> getGenreIdList(RealmList<RealmInteger> movieGenreIds) {
    List<Integer> genreIdList = new ArrayList<>();
    for (RealmInteger genreId : movieGenreIds) {
      genreIdList.add(genreId.getValue());
    }
    return genreIdList;
  }

  public static WatchMedia valueOf(RealmMovie movie) {
    Integer genreValue = getGenreIdValue(movie);
    return WatchMedia
            .builder()
            .id(movie.getId())
            .voteAverage(movie.getVoteAverage())
            .overview(movie.getOverview())
            .releaseDate(movie.getReleaseDate())
            .backdropPath(movie.getBackdropPath())
            .posterPath(movie.getPosterPath())
            .name(movie.getOriginalTitle())
            .genre(genreValue)
            .build();
  }

  public static List<WatchMedia> valueOf(List<RealmMovie> movieResults) {
    List<WatchMedia> mediaList = new ArrayList<>();
    if (movieResults.isEmpty()) {
      return mediaList;
    }
    for (RealmMovie movie : movieResults) {
      WatchMedia watchMedia = valueOf(movie);
      mediaList.add(watchMedia);
    }
    return mediaList;
  }

  public static MovieDetail mapToValueMedia(RealmMovie movie) {
    HashMap<String, Integer> detailGenres = new HashMap<>();
    for (RealmGenre genre : movie.getGenreList()) {
      detailGenres.put(genre.getName(), getIntValueSafely(genre.getId()));
    }
    HashMap<String, Integer> detailProdComp = new HashMap<>();
    for (RealmProductionCompany prodComp : movie.getProductionCompanyList()) {
      detailProdComp.put(prodComp.getName(), getIntValueSafely(prodComp.getId()));
    }
    HashMap<String, String> detailProdCountry = new HashMap<>();
    for (RealmProductionCountry prodCountry : movie.getProductionCountryList()) {
      detailProdCountry.put(prodCountry.getName(), prodCountry.getIso31661());
    }

    HashMap<String, String> spokenLanguage = new HashMap<>();
    for (RealmSpokenLanguage spokenLang : movie.getSpokenLanguageList()) {
      spokenLanguage.put(spokenLang.getName(), spokenLang.getIso31661());
    }

    if (MappingUtils.isEmptyString(movie.getBackdropPath())) {
      movie.setBackdropPath(movie.getPosterPath());
    }

    return MovieDetail
            .builder()
            .adult(movie.getAdult())
            .backdropPath(movie.getBackdropPath())
            .budget(movie.getBudget())
            .genreIdList(getGenreIdList(movie.getGenreIds()))
            .homepage(movie.getHomepage())
            .id(movie.getId())
            .imdbId(movie.getImdbId())
            .originalTitle(movie.getOriginalTitle())
            .originalLanguage(movie.getOriginalLanguage())
            .overview(movie.getOverview())
            .posterPath(movie.getPosterPath())
            .popularity(movie.getPopularity())
            .releaseDate(movie.getReleaseDate())
            .revenue(movie.getRevenue())
            .runtime(movie.getRuntime())
            .status(movie.getStatus())
            .tagline(movie.getTagline())
            .title(movie.getTitle())
            .video(movie.getVideo())
            .voteAverage(movie.getVoteAverage())
            .voteCount(movie.getVoteCount())
            .spokenLanguageList(spokenLanguage)
            .genreList(detailGenres)
            .productionCompanyList(detailProdComp)
            .productionCountryList(detailProdCountry)
            .build();
  }
}
