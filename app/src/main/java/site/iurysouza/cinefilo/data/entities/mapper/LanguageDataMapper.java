package site.iurysouza.cinefilo.data.entities.mapper;

import io.realm.RealmList;
import java.util.List;
import site.iurysouza.cinefilo.data.entities.pojo.SpokenLanguage;
import site.iurysouza.cinefilo.data.entities.realm.RealmSpokenLanguage;

class LanguageDataMapper {
  static RealmSpokenLanguage map(SpokenLanguage genre) {
    RealmSpokenLanguage realmSpokenLanguage = new RealmSpokenLanguage();
    realmSpokenLanguage.setIso31661(genre.getIso6391());
    realmSpokenLanguage.setName(genre.getName());
    return realmSpokenLanguage;
  }
  static RealmList<RealmSpokenLanguage> map(List<SpokenLanguage> genreList) {
    RealmList<RealmSpokenLanguage> realmGenreList = new RealmList<>();
    for (SpokenLanguage genre :genreList) {
      realmGenreList.add(map(genre));
    }
    return realmGenreList;
  }
}
