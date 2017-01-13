package site.iurysouza.cinefilo.model.data.entity;

import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.List;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;
import site.iurysouza.cinefilo.model.entities.realm.RealmSeries;

/**
 * Created by Iury Souza on 06/01/2017.
 */

public class WatchMediaMapper {

  public static WatchMedia mapMovie(RealmMovie movie) {
    WatchMedia build = WatchMedia
        .builder()
        .id(movie.getId())
        .voteAverage(movie.getVoteAverage())
        .overview(movie.getOverview())
        .backdropPath(movie.getBackdropPath())
        .posterPath(movie.getPosterPath())
        .name(movie.getOriginalTitle())
        .genre(movie.getGenreIds().first().getValue())
        .build();
    return build;
  }
  public static List<WatchMedia> mapMovie(List<RealmMovie> movieResults) {
    List<WatchMedia> mediaList = new ArrayList<>();
    if (movieResults.isEmpty()) {
      return mediaList;
    }
    for (RealmMovie movie : movieResults) {
      mediaList.add(mapMovie(movie));
    }
    return mediaList;
  }
  public static List<WatchMedia> mapSeries(RealmResults<RealmSeries> seriesResults) {
    List<WatchMedia> mediaList = new ArrayList<>();

    if (seriesResults.isEmpty()) {
      return mediaList;
    }
    for (RealmSeries series : seriesResults) {
      mediaList.add(mapSeries(series));
    }
    return mediaList;
  }

  public static WatchMedia mapSeries(RealmSeries series) {
    return WatchMedia
        .builder()
        .id(series.getId())
        .name(series.getOriginalName())
        .voteAverage(series.getVoteAverage())
        .overview(series.getOverview())
        .backdropPath(series.getBackdropPath())
        .posterPath(series.getPosterPath())
        .genre(series.getGenreIds().first().getValue())
        .build();
  }
}
