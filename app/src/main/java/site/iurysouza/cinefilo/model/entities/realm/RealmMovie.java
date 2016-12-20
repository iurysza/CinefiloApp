package site.iurysouza.cinefilo.model.entities.realm;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import java.util.Date;
import lombok.Data;

/**
 * Created by Iury Souza on 12/10/2016.
 */
@Data
@RealmClass
public class RealmMovie implements RealmModel {

  public static final String ID = "id";
  public static final String POPULARITY = "popularity";
  public static final String RATING = "voteAverage";
  public static final String RELEASE_DATE = "releaseDate";

  @PrimaryKey
  private Integer id;

  private Boolean adult;

  private String backdropPath;

  private Integer budget;

  private String homepage;

  private String imdbId;

  private String originalLanguage;

  private String originalTitle;

  private String overview;

  private Double popularity;

  private String posterPath;

  private Date releaseDate;

  private Integer revenue;

  private Long runtime;

  private String status;

  private String tagline;

  private String title;

  private Boolean video;

  private Double voteAverage;

  private Long voteCount;

  private Long queryDate;

  private RealmList<RealmInteger> genreIds = new RealmList<>();

  private RealmList<RealmGenre> genreList = new RealmList<>();

  private RealmList<RealmSpokenLanguage> spokenLanguageList = new RealmList<>();

  private RealmList<RealmProductionCompany> productionCompanyList = new RealmList<>();

  private RealmList<RealmProductionCountry> productionCountryList = new RealmList<>();
}
