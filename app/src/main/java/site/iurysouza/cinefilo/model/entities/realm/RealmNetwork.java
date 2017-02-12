package site.iurysouza.cinefilo.model.entities.realm;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import java.util.List;
import lombok.Data;
import site.iurysouza.cinefilo.model.entities.pojo.Network;

/**
 * Created by Iury Souza on 12/10/2016.
 */
@Data
@RealmClass
public class RealmNetwork implements RealmModel {
  public static final String ID = "id";
  public static final String QUERY_DATE = "queryDate";
  @PrimaryKey
  private Long id;
  private String name;
  private Long queryDate;
  static RealmNetwork valueOf(Network network) {
    RealmNetwork realmNetwork = new RealmNetwork();
    realmNetwork.setId(network.getId());
    realmNetwork.setName(network.getName());
    return realmNetwork;
  }
  public static RealmList<RealmNetwork> valueOf(List<Network> networkList) {
    if (networkList == null) {
      return null;
    }
    RealmList<RealmNetwork> realmNetworkList = new RealmList<>();
    for (Network network :networkList) {
      realmNetworkList.add(valueOf(network));
    }
    return realmNetworkList;
  }

}
