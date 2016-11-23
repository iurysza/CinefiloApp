package site.iurysouza.cinefilo.data.entities.realm;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import lombok.Data;

/**
 * Created by Iury Souza on 12/10/2016.
 */
@Data
@RealmClass
public class RealmProductionCountry implements RealmModel {
  @PrimaryKey
  private String iso31661;
  private String name;
}
