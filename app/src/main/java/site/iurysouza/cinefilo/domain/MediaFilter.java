package site.iurysouza.cinefilo.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.experimental.Builder;
import org.joda.time.DateTime;
import site.iurysouza.cinefilo.presentation.medias.filter.GenderEnum;

/**
 * Created by Iury Souza on 22/01/2017.
 */
@Data
@Builder
public class MediaFilter {

  //initialize default data
  private static final int START_DAY = 1;
  private static final int START_MONTH = 1;
  private static final int START_YEAR = 1990;
  private Date startDate = new DateTime()
      .withDate(START_DAY, START_MONTH, START_YEAR)
      .toDate();
  private Date endDate = new Date();
  private int minScore = 0;
  private List<GenderEnum> genderList = new ArrayList<>(Arrays.asList(GenderEnum.NONE_SELECTED));
}
