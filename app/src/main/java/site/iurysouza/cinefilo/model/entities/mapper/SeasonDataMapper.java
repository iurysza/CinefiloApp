package site.iurysouza.cinefilo.model.entities.mapper;

import io.realm.RealmList;
import java.util.List;
import site.iurysouza.cinefilo.model.entities.pojo.Season;
import site.iurysouza.cinefilo.model.entities.realm.RealmSeason;

public class SeasonDataMapper {
  static RealmSeason map(Season season) {
    RealmSeason realmSeason = new RealmSeason();

    realmSeason.setId(season.getId());
    realmSeason.setAirDate(season.getAirDate());
    realmSeason.setPosterPath(season.getPosterPath());
    realmSeason.setEpisodeCount(season.getEpisodeCount());
    realmSeason.setSeasonNumber(season.getSeasonNumber());
    realmSeason.setQueryDate(System.currentTimeMillis());
    return realmSeason;
  }

  public static RealmList<RealmSeason> map(List<Season> seasonList) {
    if (seasonList == null) {
      return null;
    }
    RealmList<RealmSeason> realmSeasonList = new RealmList<>();
    for (Season season :seasonList) {
      realmSeasonList.add(map(season));
    }
    return realmSeasonList;
  }
}
