package site.iurysouza.cinefilo.model.entities.mapper;

import io.realm.RealmList;
import java.util.List;
import site.iurysouza.cinefilo.model.entities.pojo.Network;
import site.iurysouza.cinefilo.model.entities.realm.RealmNetwork;

public class NetworkDataMapper {
  static RealmNetwork map(Network network) {
    RealmNetwork realmNetwork = new RealmNetwork();
    realmNetwork.setId(network.getId());
    realmNetwork.setName(network.getName());
    return realmNetwork;
  }
  public static RealmList<RealmNetwork> map(List<Network> networkList) {
    if (networkList == null) {
      return null;
    }
    RealmList<RealmNetwork> realmNetworkList = new RealmList<>();
    for (Network network :networkList) {
      realmNetworkList.add(map(network));
    }
    return realmNetworkList;
  }
}
