package site.iurysouza.cinefilo.domain;

import java.util.Date;
import java.util.List;
import lombok.Data;
import site.iurysouza.cinefilo.presentation.medias.filter.GenderEnum;
import site.iurysouza.cinefilo.presentation.medias.filter.SortingMethod;

/**
 * Created by Iury Souza on 22/01/2017.
 */
@Data
public class MediaFilter {

  //initialize default data
  public static final int START_YEAR = 1990;
  private Integer startDate = START_YEAR;
  private Integer endDate = new Date().getYear();
  private Integer minScore = 0;
  private SortingMethod sortBy = null;
  private List<GenderEnum> genderList = null;

  MediaFilter(int startDate, int endDate, int minScore, List<GenderEnum> genderList,
      SortingMethod sortBy) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.minScore = minScore;
    this.genderList = genderList;
    this.sortBy = sortBy;
  }

  public static MediaFilterBuilder builder() {
    return new MediaFilterBuilder();
  }



  public static class MediaFilterBuilder {
    private int startDate = START_YEAR;
    private int endDate = new Date().getYear();
    private Integer minScore = 0;
    private SortingMethod sortBy = null;
    private List<GenderEnum> genderList = null;

    MediaFilterBuilder() {
    }

    public MediaFilter.MediaFilterBuilder startDate(Integer startDate) {
      if (startDate != null) {
        this.startDate = startDate;
      }
      return this;
    }

    public MediaFilter.MediaFilterBuilder sortBy(SortingMethod sortBy) {
      if (sortBy != null) {
        this.sortBy = sortBy;
      }
      return this;
    }

    public MediaFilter.MediaFilterBuilder endDate(Integer endDate) {
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
      return new MediaFilter(startDate, endDate, minScore, genderList, sortBy);
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
