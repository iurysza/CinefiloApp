package site.iurysouza.cinefilo.model.data.entity;

import io.realm.RealmList;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.experimental.Builder;
import site.iurysouza.cinefilo.model.entities.pojo.Movie;
import site.iurysouza.cinefilo.model.entities.realm.RealmInteger;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;
import site.iurysouza.cinefilo.model.entities.realm.RealmSeries;

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
  private String backdropPath;

  public static WatchMedia valueOf(RealmMovie movie) {
    Integer genreValue = getGenreIdValue(movie);
    return WatchMedia
        .builder()
        .id(movie.getId())
        .voteAverage(movie.getVoteAverage())
        .overview(movie.getOverview())
        .backdropPath(movie.getBackdropPath())
        .posterPath(movie.getPosterPath())
        .name(movie.getOriginalTitle())
        .genre(genreValue)
        .build();
  }

  public static WatchMedia valueOf(Movie movie) {
    Integer genreValue = getGenreIdValue(movie);
    return WatchMedia
        .builder()
        .id(movie.getId())
        .voteAverage(movie.getVoteAverage())
        .overview(movie.getOverview())
        .backdropPath(movie.getBackdropPath())
        .posterPath(movie.getPosterPath())
        .name(movie.getOriginalTitle())
        .genre(genreValue)
        .build();
  }

  private static Integer getGenreIdValue(Movie movie) {
    Integer[] genreIdList = movie.getGenreIds();
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
