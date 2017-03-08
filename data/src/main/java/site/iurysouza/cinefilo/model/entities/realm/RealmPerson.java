package site.iurysouza.cinefilo.model.entities.realm;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import java.util.Date;
import java.util.List;
import lombok.Data;
import site.iurysouza.cinefilo.model.entities.pojo.Person;

/**
 * Created by Iury Souza on 12/10/2016.
 */
@Data
@RealmClass
public class RealmPerson implements RealmModel {
  public static final String ID = "id";
  public static final String QUERY_DATE = "queryDate";
  @PrimaryKey
  private Long id;
  private Long queryDate;
  private Boolean adult;
  private RealmList<RealmString> alsoKnownAs = new RealmList<>();
  private String biography;
  private Date birthday;
  private Date deathday;
  private Integer gender;
  private String homepage;
  private String imdbId;
  private String name;
  private String placeOfBirth;
  private Double popularity;
  private String profilePath;

  static RealmPerson valueOf(Person person) {
    RealmPerson realmPerson = new RealmPerson();
    realmPerson.setId(person.getId());
    realmPerson.setName(person.getName());
    realmPerson.setAdult(person.getAdult());
    realmPerson.setAlsoKnownAs(RealmString.valueOf(person.getAlsoKnownAs()));
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

  public static RealmList<RealmPerson> valueOf(List<Person> personList) {
    if (personList == null) {
      return null;
    }
    RealmList<RealmPerson> realmPersonList = new RealmList<>();
    for (Person person :personList) {
      realmPersonList.add(valueOf(person));
    }
    return realmPersonList;
  }
}
