package site.iurysouza.cinefilo.presentation.main;

import java.util.List;
import site.iurysouza.cinefilo.presentation.medias.filter.GenderEnum;

/**
 * Created by Iury Souza on 16/01/2017.
 */
public class FilterEvent {
  public final List<GenderEnum> genderEnumList;

  public FilterEvent(List<GenderEnum> genderEnumList) {
    this.genderEnumList = genderEnumList;
  }
}
