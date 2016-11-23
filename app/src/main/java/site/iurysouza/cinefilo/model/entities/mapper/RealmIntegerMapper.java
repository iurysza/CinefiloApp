package site.iurysouza.cinefilo.model.entities.mapper;

import io.realm.RealmList;
import site.iurysouza.cinefilo.model.entities.realm.RealmInteger;

class RealmIntegerMapper {
  static RealmInteger map(Integer value) {
    RealmInteger realmGenre = new RealmInteger();
    realmGenre.setValue(value);
    return realmGenre;
  }

  static RealmList<RealmInteger> map(Integer[] valueArray) {
    RealmList<RealmInteger> realmGenreList = new RealmList<>();
    for (int index = 0; index < valueArray.length; index++) {
      realmGenreList.add(map(valueArray[index]));
    }
    return realmGenreList;
  }
}
