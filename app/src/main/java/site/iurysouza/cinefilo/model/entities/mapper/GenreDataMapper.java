package site.iurysouza.cinefilo.model.entities.mapper;

import io.realm.RealmList;
import java.util.List;
import site.iurysouza.cinefilo.model.entities.pojo.Genre;
import site.iurysouza.cinefilo.model.entities.realm.RealmGenre;

class GenreDataMapper {
  static RealmGenre map(Genre genre) {
    RealmGenre realmGenre = new RealmGenre();
    realmGenre.setId(genre.getId());
    realmGenre.setName(genre.getName());
    return realmGenre;
  }
  static RealmList<RealmGenre> map(List<Genre> genreList) {
    RealmList<RealmGenre> realmGenreList = new RealmList<>();
    for (Genre genre :genreList) {
      realmGenreList.add(map(genre));
    }
    return realmGenreList;
  }
}
