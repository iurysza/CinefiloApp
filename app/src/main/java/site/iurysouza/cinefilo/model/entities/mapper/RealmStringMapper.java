package site.iurysouza.cinefilo.model.entities.mapper;

import io.realm.RealmList;
import java.util.List;
import site.iurysouza.cinefilo.model.entities.realm.RealmString;

class RealmStringMapper {
  static RealmString map(String value) {
    RealmString realmString = new RealmString();
    realmString.setValue(value);
    return realmString;
  }

  static RealmList<RealmString> map(List<String> stringList) {
    RealmList<RealmString> realmStringList = new RealmList<>();
    for (String string : stringList) {
      realmStringList.add(map(string));
    }
    return realmStringList;
  }
}
