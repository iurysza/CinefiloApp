package site.iurysouza.cinefilo.data.entities.mapper;

import io.realm.RealmList;
import java.util.List;
import site.iurysouza.cinefilo.data.entities.pojo.ProductionCountry;
import site.iurysouza.cinefilo.data.entities.realm.RealmProductionCountry;

class ProdCountryDataMapper {
  static RealmProductionCountry map(ProductionCountry prodCountry) {
    RealmProductionCountry realmProdCountry = new RealmProductionCountry();
    realmProdCountry.setIso31661(prodCountry.getIso31661());
    realmProdCountry.setName(prodCountry.getName());
    return realmProdCountry;
  }
  static RealmList<RealmProductionCountry> map(List<ProductionCountry> prodCountryList) {
    RealmList<RealmProductionCountry> realmProdCountryList = new RealmList<>();
    for (ProductionCountry prodCountry :prodCountryList) {
      realmProdCountryList.add(map(prodCountry));
    }
    return realmProdCountryList;
  }
}
