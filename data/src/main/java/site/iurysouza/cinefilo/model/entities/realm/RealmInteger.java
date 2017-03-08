package site.iurysouza.cinefilo.model.entities.realm;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.RealmClass;
import lombok.Data;

/**
 * Created by Iury Souza on 12/10/2016.
 */
@Data
@RealmClass
public class RealmInteger implements RealmModel {
  private Integer value;
  public static RealmInteger map(Integer value) {
    RealmInteger realmGenre = new RealmInteger();
    realmGenre.setValue(value);
    return realmGenre;
  }

  public static RealmList<RealmInteger> map(Integer[] valueArray) {
    RealmList<RealmInteger> realmGenreList = new RealmList<>();
    if (valueArray == null) {
      return realmGenreList;
    }
    for (int index = 0; index < valueArray.length; index++) {
      realmGenreList.add(map(valueArray[index]));
    }
    return realmGenreList;
  }
}
