package site.iurysouza.cinefilo.data.entities.mapper;

import io.realm.RealmList;
import java.util.List;
import site.iurysouza.cinefilo.data.entities.pojo.ProductionCompany;
import site.iurysouza.cinefilo.data.entities.realm.RealmProductionCompany;

class ProdCompanyDataMapper {
  static RealmProductionCompany map(ProductionCompany prodCompany) {
    RealmProductionCompany realmProdCompany = new RealmProductionCompany();
    realmProdCompany.setId(prodCompany.getId());
    realmProdCompany.setName(prodCompany.getName());
    return realmProdCompany;
  }
  static RealmList<RealmProductionCompany> map(List<ProductionCompany> prodCompanyList) {
    RealmList<RealmProductionCompany> realmProdCompanyList = new RealmList<>();
    for (ProductionCompany prodCompany :prodCompanyList) {
      realmProdCompanyList.add(map(prodCompany));
    }
    return realmProdCompanyList;
  }
}
