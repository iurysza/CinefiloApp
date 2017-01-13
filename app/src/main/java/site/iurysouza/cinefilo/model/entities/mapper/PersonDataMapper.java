package site.iurysouza.cinefilo.model.entities.mapper;

import io.realm.RealmList;
import java.util.List;
import site.iurysouza.cinefilo.model.entities.pojo.Person;
import site.iurysouza.cinefilo.model.entities.realm.RealmPerson;

public class PersonDataMapper {
  static RealmPerson map(Person person) {
    RealmPerson realmPerson = new RealmPerson();
    realmPerson.setId(person.getId());
    realmPerson.setName(person.getName());
    realmPerson.setAdult(person.getAdult());
    realmPerson.setAlsoKnownAs(RealmStringMapper.map(person.getAlsoKnownAs()));
    realmPerson.setBiography(person.getBiography());
    realmPerson.setBirthday(person.getBirthday());
    realmPerson.setDeathday(person.getDeathday());
    realmPerson.setHomepage(person.getHomepage());
    realmPerson.setPlaceOfBirth(person.getPlaceOfBirth());
    realmPerson.setImdbId(person.getImdbId());
    realmPerson.setPopularity(person.getPopularity());
    realmPerson.setGender(person.getGender());
    realmPerson.setProfilePath(person.getProfilePath());
    realmPerson.setQueryDate(System.currentTimeMillis());
    return realmPerson;
  }

  public static RealmList<RealmPerson> map(List<Person> personList) {
    if (personList == null) {
      return null;
    }
    RealmList<RealmPerson> realmPersonList = new RealmList<>();
    for (Person person :personList) {
      realmPersonList.add(map(person));
    }
    return realmPersonList;
  }
}
