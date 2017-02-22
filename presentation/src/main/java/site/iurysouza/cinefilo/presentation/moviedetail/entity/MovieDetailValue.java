package site.iurysouza.cinefilo.presentation.moviedetail.entity;

import android.os.Parcelable;
import com.google.auto.value.AutoValue;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Nullable;
import site.iurysouza.cinefilo.domain.moviedetail.MovieDetail;
import site.iurysouza.cinefilo.model.entities.realm.RealmGenre;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;
import site.iurysouza.cinefilo.model.entities.realm.RealmProductionCompany;
import site.iurysouza.cinefilo.model.entities.realm.RealmProductionCountry;
import site.iurysouza.cinefilo.model.entities.realm.RealmSpokenLanguage;

/**
 * Created by Iury Souza on 31/01/2017.
 */
@AutoValue
public abstract class MovieDetailValue implements Parcelable {

  private static Builder builder() {
    return new $AutoValue_MovieDetailValue.Builder();
  }

  public static MovieDetailValue mapToValueMedia(RealmMovie movie) {
    HashMap<String, Integer> detailGenres = new HashMap<>();
    for (RealmGenre genre : movie.getGenreList()) {
      detailGenres.put(genre.getName(), genre.getId().intValue());
    }
    HashMap<String, Integer> detailProdComp = new HashMap<>();
    for (RealmProductionCompany prodComp : movie.getProductionCompanyList()) {
      detailProdComp.put(prodComp.getName(), prodComp.getId().intValue());
    }
    HashMap<String, String> detailProdCountry = new HashMap<>();
    for (RealmProductionCountry prodCountry : movie.getProductionCountryList()) {
      detailProdCountry.put(prodCountry.getName(), prodCountry.getIso31661());
    }

    HashMap<String, String> spokenLanguage = new HashMap<>();
    for (RealmSpokenLanguage spokenLang : movie.getSpokenLanguageList()) {
      spokenLanguage.put(spokenLang.getName(), spokenLang.getIso31661());
    }

    if (movie.getBackdropPath().isEmpty()) {
      movie.setBackdropPath(movie.getPosterPath());
    }

    return MovieDetailValue
            .builder()
            .adult(movie.getAdult())
            .backdropPath(movie.getBackdropPath())
            .budget(movie.getBudget())
            .homePage(movie.getHomepage())
            .id(movie.getId())
            .imdbId(movie.getImdbId())
            .originalLanguage(movie.getOriginalLanguage())
            .originalTitle(movie.getOriginalTitle())
            .overview(movie.getOverview())
            .popularity(movie.getPopularity())
            .posterPath(movie.getPosterPath())
            .releaseDate(movie.getReleaseDate())
            .revenue(movie.getRevenue())
            .runTime(movie.getRuntime())
            .status(movie.getStatus())
            .tagLine(movie.getTagline())
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

  public static MovieDetailValue mapToValueMedia(MovieDetail movie) {
    if (movie.getBackdropPath().isEmpty()) {
      movie.setBackdropPath(movie.getPosterPath());
    }
    return MovieDetailValue
            .builder()
            .adult(movie.getAdult())
            .backdropPath(movie.getBackdropPath())
            .budget(movie.getBudget())
            .homePage(movie.getHomepage())
            .id(movie.getId())
            .imdbId(movie.getImdbId())
            .originalLanguage(movie.getOriginalLanguage())
            .originalTitle(movie.getOriginalTitle())
            .overview(movie.getOverview())
            .popularity(movie.getPopularity())
            .posterPath(movie.getPosterPath())
            .releaseDate(movie.getReleaseDate())
            .revenue(movie.getRevenue())
            .runTime(movie.getRuntime())
            .status(movie.getStatus())
            .tagLine(movie.getTagline())
            .title(movie.getTitle())
            .video(movie.getVideo())
            .voteAverage(movie.getVoteAverage())
            .voteCount(movie.getVoteCount())
            .spokenLanguageList(movie.getSpokenLanguageList())
            .genreList(movie.getGenreList())
            .productionCompanyList(movie.getProductionCompanyList())
            .productionCountryList(movie.getProductionCountryList())
            .build();
  }

  public static List<MovieDetailValue> mapToValueMedia(List<RealmMovie> movieList) {
    List<MovieDetailValue> valueMediaList = new ArrayList<>();

    if (movieList.isEmpty()) {
      return valueMediaList;
    }

    for (RealmMovie movie : movieList) {
      valueMediaList.add(mapToValueMedia(movie));
    }
    return valueMediaList;
  }

  public abstract int id();

  public abstract Double voteAverage();

  public abstract String overview();

  public abstract Date releaseDate();

  public abstract String homePage();

  public abstract String posterPath();

  public abstract String imdbId();

  public abstract Boolean adult();

  public abstract Double popularity();

  public abstract String backdropPath();

  public abstract Integer budget();

  public abstract String originalLanguage();

  public abstract Integer revenue();

  public abstract Integer runTime();

  public abstract String originalTitle();

  public abstract String status();

  public abstract String tagLine();

  @Nullable public abstract String title();

  @Nullable public abstract Boolean video();

  public abstract Integer voteCount();

  public abstract HashMap<String, Integer> genreList();

  public abstract HashMap<String, Integer> productionCompanyList();

  public abstract HashMap<String, String> productionCountryList();

  public abstract HashMap<String, String> spokenLanguageList();

  @Override public String toString() {
    return originalTitle();
  }

  @AutoValue.Builder
  abstract static class Builder {
    abstract Builder voteAverage(Double voteAverage);

    abstract Builder id(int id);

    abstract Builder adult(Boolean adult);

    abstract Builder video(Boolean video);

    abstract Builder originalTitle(String name);

    abstract Builder imdbId(String imdbId);

    abstract Builder overview(String overview);

    abstract Builder releaseDate(Date releaseDate);

    abstract Builder posterPath(String overview);

    abstract Builder backdropPath(String overview);

    abstract Builder title(String title);

    abstract Builder tagLine(String tagline);

    abstract Builder homePage(String homePage);

    abstract Builder popularity(Double popularity);

    abstract Builder budget(Integer budget);

    abstract Builder originalLanguage(String originalLanguage);

    abstract Builder revenue(Integer revenue);

    abstract Builder runTime(Integer runtime);

    abstract Builder status(String status);

    abstract Builder voteCount(Integer voteCount);

    abstract Builder genreList(HashMap<String, Integer> genreList);

    abstract Builder productionCompanyList(HashMap<String, Integer> productionCompanyList);

    abstract Builder productionCountryList(HashMap<String, String> productionCountryList);

    abstract Builder spokenLanguageList(HashMap<String, String> spokenLanguageList);

    abstract MovieDetailValue build();
  }
}