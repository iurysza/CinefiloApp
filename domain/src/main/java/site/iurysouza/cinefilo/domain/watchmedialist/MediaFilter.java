package site.iurysouza.cinefilo.domain.watchmedialist;

import java.util.Calendar;
import java.util.List;
import lombok.Data;
import lombok.experimental.Builder;

/**
 * Created by Iury Souza on 22/01/2017.
 */
@Data
@Builder
public class MediaFilter {

  public static final int DEFAULT_STARTING_YEAR = 1990;
  public static final int DEFAULT_RATING_SCORE = 6;

  private Integer startDate;
  private Integer endDate;
  private Integer minScore;
  private SortingMethod sortBy;
  private List<Integer> genderList;

  private MediaFilter(int startDate, int endDate, int minScore, List<Integer> genderList,
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
    private Integer startDate = DEFAULT_STARTING_YEAR;
    private Integer endDate = Calendar.getInstance().get(Calendar.YEAR) + 1000;
    private Integer minScore = DEFAULT_RATING_SCORE;
    private SortingMethod sortBy = null;
    private List<Integer> genderList = null;

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

    public MediaFilter.MediaFilterBuilder genderList(List<Integer> genderList) {
      if (genderList != null) {
        this.genderList = genderList;
      }
      return this;
    }

    public MediaFilter build() {
      return new MediaFilter(startDate, endDate, minScore, genderList, sortBy);
    }

    public String toString() {
      return "MediaFilter.MediaFilterBuilder(startDate="
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
