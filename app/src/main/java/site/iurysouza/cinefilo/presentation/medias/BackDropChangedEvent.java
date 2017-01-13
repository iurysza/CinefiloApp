package site.iurysouza.cinefilo.presentation.medias;

import site.iurysouza.cinefilo.domain.entity.WatchMediaValue;

/**
 * Created by Iury Souza on 05/01/2017.
 */
public class BackDropChangedEvent {
  public final WatchMediaValue featuredMovie;

  BackDropChangedEvent(WatchMediaValue featuredMovie) {
    this.featuredMovie = featuredMovie;
  }
}
