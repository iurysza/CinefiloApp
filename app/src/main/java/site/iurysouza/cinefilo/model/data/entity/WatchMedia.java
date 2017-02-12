package site.iurysouza.cinefilo.model.data.entity;

import io.realm.RealmList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.experimental.Builder;
import site.iurysouza.cinefilo.model.entities.pojo.Movie;
import site.iurysouza.cinefilo.model.entities.pojo.Series;
import site.iurysouza.cinefilo.model.entities.realm.RealmInteger;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;
import site.iurysouza.cinefilo.model.entities.realm.RealmSeries;

import static site.iurysouza.cinefilo.util.Utils.isEmptyString;

/**
 * Created by Iury Souza on 06/01/2017.
 */
@Data
@Builder
public class WatchMedia {
  private long id;
  private String overview;
  private Double voteAverage;
  private String posterPath;
  private String name;
  private Integer genre;
  private Date releaseDate;
  private String backdropPath;

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

  private static boolean isInvalid(Movie movie) {
    return (isEmptyString(movie.getOriginalTitle()) ||
        isEmptyString(movie.getPosterPath()) ||
        isEmptyString(movie.getOverview()) ||
        movie.getReleaseDate() == null);
  }

  public static WatchMedia valueOf(Series series) {
    Integer genreValue = getGenreIdValue(series);
    return WatchMedia
        .builder()
        .id(series.getId())
        .voteAverage(series.getVoteAverage())
        .releaseDate(series.getFirstAirDate())
        .overview(series.getOverview())
        .backdropPath(series.getBackdropPath())
        .posterPath(series.getPosterPath())
        .name(series.getOriginalName())
        .genre(genreValue)
        .build();
  }

  public static List<WatchMedia> valueOfMovieList(List<Movie> movieList) {
    List<WatchMedia> mediaList = new ArrayList<>();
    if (movieList.isEmpty()) return mediaList;

    for (Movie movie : movieList) {
      WatchMedia watchMedia = valueOf(movie);
      if (watchMedia != null) {
        mediaList.add(watchMedia);
      }
    }
    return mediaList;
  }

  public static List<WatchMedia> valueOfSeriesList(List<Series> seriesList) {
    List<WatchMedia> mediaList = new ArrayList<>();

    if (seriesList.isEmpty()) {
      return mediaList;
    } else {
      for (Series series : seriesList) {
        if (series.getOriginalName() != null &&
            series.getPosterPath() != null &&
            series.getFirstAirDate() != null &&
            series.getOverview() != null) {

          mediaList.add(valueOf(series));
        }
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

  private static Integer getGenreIdValue(Series series) {
    Integer[] genreIdList = series.getGenres();
    Integer genreId = 0;
    if (genreIdList.length > 0) {
      genreId = genreIdList[0];
    }
    return genreId;
  }

  public static WatchMedia valueOf(RealmSeries series) {
    Integer genreValue = getGenreIdValue(series);
    return WatchMedia
        .builder()
        .id(series.getId())
        .voteAverage(series.getVoteAverage())
        .overview(series.getOverview())
        .backdropPath(series.getBackdropPath())
        .releaseDate(series.getFirstAirDate())
        .posterPath(series.getPosterPath())
        .name(series.getOriginalName())
        .genre(genreValue)
        .build();
  }

  private static Integer getGenreIdValue(RealmMovie movie) {
    RealmList<RealmInteger> genreIds = movie.getGenreIds();
    Integer genreValue = -1;
    if (!genreIds.isEmpty()) {
      genreValue = genreIds.first().getValue();
    }
    return genreValue;
  }

  private static Integer getGenreIdValue(RealmSeries series) {
    RealmList<RealmInteger> genreIds = series.getGenreIds();
    Integer genreValue = -1;
    if (!genreIds.isEmpty()) {
      genreValue = genreIds.first().getValue();
    }
    return genreValue;
  }

  public static List<WatchMedia> valueOfRealmMovie(List<RealmMovie> movieResults) {
    List<WatchMedia> mediaList = new ArrayList<>();

    if (movieResults.isEmpty()) {
      return mediaList;
    } else {
      for (RealmMovie movie : movieResults) {
        mediaList.add(WatchMedia.valueOf(movie));
      }
      return mediaList;
    }
  }

  public static List<WatchMedia> valueOfRealmSeries(List<RealmSeries> seriesResults) {
    List<WatchMedia> mediaList = new ArrayList<>();

    if (seriesResults.isEmpty()) {
      return mediaList;
    } else {
      for (RealmSeries series : seriesResults) {
        mediaList.add(WatchMedia.valueOf(series));
      }
      return mediaList;
    }
  }
}
