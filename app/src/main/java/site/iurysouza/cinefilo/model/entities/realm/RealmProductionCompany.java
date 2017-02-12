package site.iurysouza.cinefilo.model.entities.realm;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import java.util.List;
import lombok.Data;
import site.iurysouza.cinefilo.model.entities.pojo.ProductionCompany;

/**
 * Created by Iury Souza on 12/10/2016.
 */
@Data
@RealmClass
public class RealmProductionCompany implements RealmModel {
  @PrimaryKey
  private Long id;
  private String name;

  public static RealmProductionCompany valueOf(ProductionCompany prodCompany) {
    RealmProductionCompany realmProdCompany = new RealmProductionCompany();
    realmProdCompany.setId(prodCompany.getId());
    realmProdCompany.setName(prodCompany.getName());
    return realmProdCompany;
  }

  public static RealmList<RealmProductionCompany> valueOf(List<ProductionCompany> prodCompanyList) {
    RealmList<RealmProductionCompany> realmProdCompanyList = new RealmList<>();
    if (prodCompanyList == null || prodCompanyList.isEmpty()) {
      return realmProdCompanyList;
    }
    for (ProductionCompany prodCompany : prodCompanyList) {
      realmProdCompanyList.add(valueOf(prodCompany));
    }
    return realmProdCompanyList;
  }
}
