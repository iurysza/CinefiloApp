package site.iurysouza.cinefilo.model.entities.realm;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import java.util.List;
import lombok.Data;
import site.iurysouza.cinefilo.model.entities.pojo.Genre;

/**
 * Created by Iury Souza on 12/10/2016.
 */
@Data
@RealmClass
public class RealmGenre implements RealmModel {
  public static final String ID = "id";
  public static final String QUERY_DATE = "queryDate";
  @PrimaryKey
  private Long id;
  private String name;
  private Long queryDate;

  public static RealmList<RealmGenre> map(List<Genre> genreList) {
    RealmList<RealmGenre> realmGenreList = new RealmList<>();
    if (genreList == null) {
      return realmGenreList;
    }
    for (Genre genre : genreList) {
      RealmGenre realmGenre = RealmGenre.valueOf(genre);
      if (realmGenre != null) {
        realmGenreList.add(realmGenre);
      }
    }
    return realmGenreList;
  }

  private static RealmGenre valueOf(Genre genre) {
    if (genre == null) {
      return null;
    }
    RealmGenre realmGenre = new RealmGenre();
    realmGenre.setName(genre.getName());
    realmGenre.setId(genre.getId());
    return realmGenre;
  }
}
