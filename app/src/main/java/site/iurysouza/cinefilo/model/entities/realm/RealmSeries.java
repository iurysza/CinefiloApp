package site.iurysouza.cinefilo.model.entities.realm;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import java.util.Date;
import lombok.Data;
import site.iurysouza.cinefilo.model.entities.pojo.Series;

/**
 * Created by Iury Souza on 12/10/2016.
 */
@Data
@RealmClass
public class RealmSeries implements RealmModel {

  public static final String ID = "id";
  public static final String POPULARITY = "popularity";
  public static final String VOTE_AVG = "voteAverage";
  public static final String FIRST_AIR_DATE = "firstAirDate";
  public static final String VOTE_COUNT = "voteCount";
  public static final int NOW_QUERY = 2;
  public static final int POP_QUERY = 1;
  public static final int TOP_QUERY = 0;
  public static final String QUERY_TYPE = "queryType";

  @PrimaryKey
  private long id;
  private String backdropPath;
  private Date firstAirDate;
  private String homepage;
  private Boolean inProduction;
  private Date lastAirDate;
  private String name;
  private Integer numberOfEpisodes;
  private Integer numberOfSeasons;
  private String originalLanguage;
  private String originalName;
  private String overview;
  private Double popularity;
  private String posterPath;
  private String status;
  private String type;
  private Double voteAverage;
  private Integer voteCount;
  private Long queryDate;
  private int queryType;

  private RealmList<RealmGenre> genreList = new RealmList<>();
  private RealmList<RealmProductionCompany> productionCompanyList = new RealmList<>();
  private RealmList<RealmInteger> genreIds = new RealmList<>();
  private RealmList<RealmPerson> createdByList = new RealmList<>();
  private RealmList<RealmSeason> seasonList = new RealmList<>();
  private RealmList<RealmString> languageList = new RealmList<>();
  private RealmList<RealmNetwork> networkList = new RealmList<>();
  private RealmList<RealmString> originCountryList = new RealmList<>();

  public static RealmSeries valueOf(Series series, int queryType) {
    RealmSeries realmSeries = new RealmSeries();

    String originalTitle = series.getOriginalName();
    String posterPath = series.getPosterPath();
    Integer[] genreIds = series.getGenres();
    Double voteAverage = series.getVoteAverage();
    String overview = series.getOverview();

    if (isSeriesInvalid(originalTitle, posterPath, genreIds, voteAverage, overview)) return null;
    if (series.getFirstAirDate() == null) {
      return realmSeries;
    }

    String backdropPath = series.getBackdropPath();
    if (backdropPath == null) {
      backdropPath = "";
    }
    realmSeries.setBackdropPath(backdropPath);
    realmSeries.setFirstAirDate(series.getFirstAirDate());
    realmSeries.setLastAirDate(series.getLastAirDate());
    realmSeries.setHomepage(series.getHomepage());
    realmSeries.setInProduction(series.getInProduction());
    realmSeries.setName(series.getName());
    realmSeries.setId(series.getId());
    realmSeries.setNumberOfEpisodes(series.getNumberOfEpisodes());
    realmSeries.setNumberOfSeasons(series.getNumberOfSeasons());
    realmSeries.setOriginalLanguage(series.getOriginalLanguage());
    realmSeries.setOriginalName(series.getOriginalName());
    realmSeries.setOverview(series.getOverview());
    realmSeries.setPopularity(series.getPopularity());
    realmSeries.setPosterPath(series.getPosterPath());
    realmSeries.setVoteAverage(series.getVoteAverage());
    realmSeries.setVoteCount(series.getVoteCount());
    realmSeries.setStatus(series.getStatus());
    realmSeries.setType(series.getType());
    realmSeries.setQueryDate(System.currentTimeMillis());
    realmSeries.setGenreList(RealmGenre.valueOf(series.getGenreList()));
    realmSeries.setCreatedByList(RealmPerson.valueOf(series.getCreatedByList()));
    realmSeries.setSeasonList(RealmSeason.valueOf(series.getSeasons()));
    realmSeries.setNetworkList(RealmNetwork.valueOf(series.getNetworks()));

    realmSeries.setProductionCompanyList(
        RealmProductionCompany.valueOf(series.getProductionCompanyList()));
    realmSeries.setLanguageList(RealmString.valueOf(series.getLanguages()));
    realmSeries.setOriginCountryList(RealmString.valueOf(series.getOriginCountry()));
    realmSeries.setQueryType(queryType);
    return realmSeries;
  }

  private static boolean isSeriesInvalid(String originalTitle, String posterPath,
      Integer[] genreIds,
      Double voteAverage, String overview) {
    return isStringFieldInvalid(originalTitle) ||
        isStringFieldInvalid(posterPath) ||
        isStringFieldInvalid(overview) ||
        genreIds == null || genreIds.length == 0 ||
        voteAverage == null;
  }

  private static boolean isStringFieldInvalid(String field) {
    return (field == null || field.isEmpty());
  }
}
