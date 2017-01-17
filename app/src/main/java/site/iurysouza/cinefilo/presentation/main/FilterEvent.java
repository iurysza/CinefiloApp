package site.iurysouza.cinefilo.presentation.main;

import site.iurysouza.cinefilo.presentation.medias.filter.GenderEnum;

/**
 * Created by Iury Souza on 16/01/2017.
 */
public class FilterEvent {
  public final GenderEnum genderEnum;



  public FilterEvent(GenderEnum genderEnum) {
    this.genderEnum = genderEnum;
  }
}
