package site.iurysouza.cinefilo.model.entities.realm;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import java.util.Date;
import lombok.Data;

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
}
