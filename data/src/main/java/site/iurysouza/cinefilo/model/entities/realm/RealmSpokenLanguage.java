package site.iurysouza.cinefilo.model.entities.realm;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import java.util.List;
import lombok.Data;
import site.iurysouza.cinefilo.model.entities.pojo.SpokenLanguage;

/**
 * Created by Iury Souza on 12/10/2016.
 */
@Data
@RealmClass
public class RealmSpokenLanguage implements RealmModel {
  @PrimaryKey
  private String iso31661;
  private String name;

  public static RealmList<RealmSpokenLanguage> valueOf(List<SpokenLanguage> spokenLang) {
    RealmList<RealmSpokenLanguage> realmSpokenLang = new RealmList<>();

    if (spokenLang == null || spokenLang.isEmpty()) {
      return realmSpokenLang;
    }
    for (SpokenLanguage language : spokenLang) {
      realmSpokenLang.add(valueOf(language));
    }
    return realmSpokenLang;
  }

  public static RealmSpokenLanguage valueOf(SpokenLanguage spokenLanguage) {
    RealmSpokenLanguage realmSpokenLanguage = new RealmSpokenLanguage();
    if (spokenLanguage != null) {
    realmSpokenLanguage.setName(spokenLanguage.getName());
    realmSpokenLanguage.setIso31661(spokenLanguage.getIso6391());
    }
    return realmSpokenLanguage;
  }
}
