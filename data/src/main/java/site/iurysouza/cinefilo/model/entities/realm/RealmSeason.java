package site.iurysouza.cinefilo.model.entities.realm;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import java.util.Date;
import java.util.List;
import lombok.Data;
import site.iurysouza.cinefilo.model.entities.pojo.Season;

/**
 * Created by Iury Souza on 12/10/2016.
 */
@Data
@RealmClass
public class RealmSeason implements RealmModel {
  public static final String ID = "id";
  public static final String QUERY_DATE = "queryDate";
  @PrimaryKey
  private Long id;
  private Long queryDate;
  private Date airDate;
  private Integer episodeCount;
  private String posterPath;
  private Integer seasonNumber;
  static RealmSeason valueOf(Season season) {
    RealmSeason realmSeason = new RealmSeason();

    realmSeason.setId(season.getId());
    realmSeason.setAirDate(season.getAirDate());
    realmSeason.setPosterPath(season.getPosterPath());
    realmSeason.setEpisodeCount(season.getEpisodeCount());
    realmSeason.setSeasonNumber(season.getSeasonNumber());
    realmSeason.setQueryDate(System.currentTimeMillis());
    return realmSeason;
  }

  public static RealmList<RealmSeason> valueOf(List<Season> seasonList) {
    if (seasonList == null) {
      return null;
    }
    RealmList<RealmSeason> realmSeasonList = new RealmList<>();
    for (Season season :seasonList) {
      realmSeasonList.add(valueOf(season));
    }
    return realmSeasonList;
  }

}
