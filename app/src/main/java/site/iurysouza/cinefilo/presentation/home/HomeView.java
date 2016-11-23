package site.iurysouza.cinefilo.presentation.home;

import site.iurysouza.cinefilo.data.entities.realm.RealmMovie;
import site.iurysouza.cinefilo.presentation.base.mvp.BaseView;

/**
 * Created by Iury Souza on 12/10/2016.
 */
public interface HomeView extends BaseView {
  void showLoadingIndicator();

  void hideLoadingIndicator();

  void showErrorIndicator();

  void showRetrievedMovie(RealmMovie realmMovie);
}
