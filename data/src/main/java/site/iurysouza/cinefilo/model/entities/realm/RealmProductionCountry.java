package site.iurysouza.cinefilo.model.entities.realm;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import java.util.List;
import lombok.Data;
import site.iurysouza.cinefilo.model.entities.pojo.ProductionCountry;

/**
 * Created by Iury Souza on 12/10/2016.
 */
@Data
@RealmClass
public class RealmProductionCountry implements RealmModel {
  @PrimaryKey
  private String iso31661;
  private String name;

  public static RealmList<RealmProductionCountry> valueOf(List<ProductionCountry> prodCountry) {
    RealmList<RealmProductionCountry> realmProdCountry = new RealmList<>();

    if (prodCountry == null || prodCountry.isEmpty()) {
      return realmProdCountry;
    }
    for (ProductionCountry language : prodCountry) {
      realmProdCountry.add(valueOf(language));
    }
    return realmProdCountry;
  }

  public static RealmProductionCountry valueOf(ProductionCountry prodCountry) {
    RealmProductionCountry realmProdCountryuage = new RealmProductionCountry();
    if (prodCountry != null) {
      realmProdCountryuage.setName(prodCountry.getName());
      realmProdCountryuage.setIso31661(prodCountry.getIso31661());
    }
    return realmProdCountryuage;
  }
}
