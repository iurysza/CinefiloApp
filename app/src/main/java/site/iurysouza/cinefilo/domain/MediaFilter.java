package site.iurysouza.cinefilo.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.joda.time.DateTime;
import site.iurysouza.cinefilo.presentation.medias.filter.GenderEnum;

/**
 * Created by Iury Souza on 22/01/2017.
 */
@Data
public class MediaFilter {

  //initialize default data
  private static final int START_DAY = 1;
  private static final int START_MONTH = 1;
  private static final int START_YEAR = 1990;
  private Date startDate = new DateTime()
      .withDate(START_YEAR,START_DAY ,START_MONTH )
      .toDate();
  private Date endDate = new Date();
  private Integer minScore = 0;
  private List<GenderEnum> genderList = new ArrayList<>(Arrays.asList(GenderEnum.NONE_SELECTED));

  MediaFilter(Date startDate, Date endDate, int minScore, List<GenderEnum> genderList) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.minScore = minScore;
    this.genderList = genderList;
  }

  public static MediaFilterBuilder builder() {
    return new MediaFilterBuilder();
  }

  public static class MediaFilterBuilder {
    private Date startDate = new DateTime()
        .withDate(START_YEAR,START_DAY ,START_MONTH )
        .toDate();
    private Date endDate = new Date();
    private Integer minScore = 0;
    private List<GenderEnum> genderList = new ArrayList<>(Arrays.asList(GenderEnum.NONE_SELECTED));

    MediaFilterBuilder() {
    }

    public MediaFilter.MediaFilterBuilder startDate(Date startDate) {
      if (startDate != null) {
        this.startDate = startDate;
      }
      return this;
    }

    public MediaFilter.MediaFilterBuilder endDate(Date endDate) {
      if (endDate != null) {
        this.endDate = endDate;
      }
      return this;
    }

    public MediaFilter.MediaFilterBuilder minScore(Integer minScore) {
      if (minScore != null) {
        this.minScore = minScore;
      }
      return this;
    }

    public MediaFilter.MediaFilterBuilder genderList(List<GenderEnum> genderList) {
      if (genderList != null) {
        this.genderList = genderList;
      }
      return this;
    }

    public MediaFilter build() {
      return new MediaFilter(startDate, endDate, minScore, genderList);
    }

    public String toString() {
      return "site.iurysouza.cinefilo.domain.MediaFilter.MediaFilterBuilder(startDate="
          + this.startDate
          + ", endDate="
          + this.endDate
          + ", minScore="
          + this.minScore
          + ", genderList="
          + this.genderList
          + ")";
    }
  }
}