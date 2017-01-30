package site.iurysouza.cinefilo.domain.entity;

import java.util.ArrayList;
import java.util.List;
import site.iurysouza.cinefilo.model.data.entity.WatchMedia;

/**
 * Created by Iury Souza on 06/01/2017.
 */

public class WatchMediaValueMapper {

  public static WatchMediaValue mapToValueMedia(WatchMedia media) {
    WatchMediaValue build = WatchMediaValue
        .builder()
        .id(media.getId())
        .voteAverage(media.getVoteAverage())
        .overview(media.getOverview())
        .backdropPath(media.getBackdropPath())
        .posterPath(media.getPosterPath())
        .releaseDate(media.getReleaseDate())
        .name(media.getName())
        .genre(media.getGenre())
        .build();
    return build;
  }

  public static List<WatchMediaValue> mapToValueMedia(List<WatchMedia> mediaList) {
    List<WatchMediaValue> valueMediaList = new ArrayList<>();

    if (mediaList.isEmpty()) {
      return valueMediaList;
    }

    for (WatchMedia media : mediaList) {
      valueMediaList.add(mapToValueMedia(media));
    }
    return valueMediaList;
  }
}
