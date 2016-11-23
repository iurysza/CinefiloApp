package site.iurysouza.cinefilo.model.entities.realm;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import lombok.Data;

/**
 * Created by Iury Souza on 12/10/2016.
 */
@Data
@RealmClass
public class RealmProductionCompany implements RealmModel {
  @PrimaryKey
  private Long id;
  private String name;
}
