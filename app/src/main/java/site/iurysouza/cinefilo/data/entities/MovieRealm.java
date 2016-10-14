package site.iurysouza.cinefilo.data.entities;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by Iury Souza on 12/10/2016.
 */
@RealmClass
public class MovieRealm implements RealmModel {
  public static final String ID = "id";
  private Boolean adult;

  private String backdropPath;

  //private MovieCollectionEntity belongsToCollection;

  private Integer budget;

  //private List<GenreEntity> genreEntities = new ArrayList<GenreEntity>();

  private String homepage;

  @PrimaryKey
  private Integer id;

  private String imdbId;

  private String originalLanguage;

  private String originalTitle;

  private String overview;

  private Double popularity;

  private String posterPath;

  //private List<ProdCompanyEntity> productionCompanies = new ArrayList<ProdCompanyEntity>();
  //
  //private List<ProdCountryEntity> productionCountries = new ArrayList<ProdCountryEntity>();

  private String releaseDate;

  private Integer revenue;

  private Integer runtime;

  //private List<SpokenLangEntity> spokenLangEntities = new ArrayList<SpokenLangEntity>();

  private String status;

  private String tagline;
  private String title;

  private Boolean video;

  private Double voteAverage;

  private Integer voteCount;
  public Boolean getAdult() {
    return adult;
  }

  public void setAdult(Boolean adult) {
    this.adult = adult;
  }

  public String getBackdropPath() {
    return backdropPath;
  }

  public void setBackdropPath(String backdropPath) {
    this.backdropPath = backdropPath;
  }

  //public MovieCollectionEntity getBelongsToCollection() {
  //  return belongsToCollection;
  //}

  //public void setBelongsToCollection(MovieCollectionEntity belongsToCollection) {
  //  this.belongsToCollection = belongsToCollection;
  //}

  public Integer getBudget() {
    return budget;
  }

  public void setBudget(Integer budget) {
    this.budget = budget;
  }

  //public List<GenreEntity> getGenreEntities() {
  //  return genreEntities;
  //}
  //
  //public void setGenreEntities(
  //    List<GenreEntity> genreEntities) {
  //  this.genreEntities = genreEntities;
  //}

  public String getHomepage() {
    return homepage;
  }

  public void setHomepage(String homepage) {
    this.homepage = homepage;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getImdbId() {
    return imdbId;
  }

  public void setImdbId(String imdbId) {
    this.imdbId = imdbId;
  }

  public String getOriginalLanguage() {
    return originalLanguage;
  }

  public void setOriginalLanguage(String originalLanguage) {
    this.originalLanguage = originalLanguage;
  }

  public String getOriginalTitle() {
    return originalTitle;
  }

  public void setOriginalTitle(String originalTitle) {
    this.originalTitle = originalTitle;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public Double getPopularity() {
    return popularity;
  }

  public void setPopularity(Double popularity) {
    this.popularity = popularity;
  }

  public String getPosterPath() {
    return posterPath;
  }

  public void setPosterPath(String posterPath) {
    this.posterPath = posterPath;
  }

  //public List<ProdCompanyEntity> getProductionCompanies() {
  //  return productionCompanies;
  //}
  //
  //public void setProductionCompanies(
  //    List<ProdCompanyEntity> productionCompanies) {
  //  this.productionCompanies = productionCompanies;
  //}
  //
  //public List<ProdCountryEntity> getProductionCountries() {
  //  return productionCountries;
  //}
  //
  //public void setProductionCountries(
  //    List<ProdCountryEntity> productionCountries) {
  //  this.productionCountries = productionCountries;
  //}

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public Integer getRevenue() {
    return revenue;
  }

  public void setRevenue(Integer revenue) {
    this.revenue = revenue;
  }

  public Integer getRuntime() {
    return runtime;
  }

  public void setRuntime(Integer runtime) {
    this.runtime = runtime;
  }

  //public List<SpokenLangEntity> getSpokenLangEntities() {
  //  return spokenLangEntities;
  //}
  //
  //public void setSpokenLangEntities(
  //    List<SpokenLangEntity> spokenLangEntities) {
  //  this.spokenLangEntities = spokenLangEntities;
  //}

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getTagline() {
    return tagline;
  }

  public void setTagline(String tagline) {
    this.tagline = tagline;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Boolean getVideo() {
    return video;
  }

  public void setVideo(Boolean video) {
    this.video = video;
  }

  public Double getVoteAverage() {
    return voteAverage;
  }

  public void setVoteAverage(Double voteAverage) {
    this.voteAverage = voteAverage;
  }

  public Integer getVoteCount() {
    return voteCount;
  }

  public void setVoteCount(Integer voteCount) {
    this.voteCount = voteCount;
  }


}
