package site.iurysouza.cinefilo.model.entities.realm;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.RealmClass;
import java.util.List;
import lombok.Data;

/**
 * Created by Iury Souza on 12/10/2016.
 */
@Data
@RealmClass
public class RealmString implements RealmModel {
  private String value;
  public static RealmString valueOf(String value) {
    RealmString realmString = new RealmString();
    realmString.setValue(value);
    return realmString;
  }

  public static RealmList<RealmString> valueOf(List<String> stringList) {
    RealmList<RealmString> realmStringList = new RealmList<>();
    if (stringList == null  || stringList.isEmpty()) {
      return realmStringList;
    }
    for (String string : stringList) {
      realmStringList.add(valueOf(string));
    }
    return realmStringList;
  }
}
