package site.iurysouza.cinefilo.presentation.main;

import site.iurysouza.cinefilo.domain.watchmedialist.MediaFilter;

/**
 * Created by Iury Souza on 16/01/2017.
 */
public class FilterEvent {
  public final MediaFilter filter;
  public FilterEvent(MediaFilter filter) {
    this.filter = filter;
  }
}
