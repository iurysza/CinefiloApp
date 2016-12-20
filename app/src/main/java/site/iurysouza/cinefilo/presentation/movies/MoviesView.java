package site.iurysouza.cinefilo.presentation.movies;

import io.realm.RealmModel;
import io.realm.RealmResults;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;
import site.iurysouza.cinefilo.presentation.base.mvp.BaseView;

/**
 * Created by Iury Souza on 12/10/2016.
 */
public interface MoviesView extends BaseView {
  void showLoadingIndicator();

  void hideLoadingIndicator();

  void showErrorIndicator();

  void showPopularMovieList(RealmModel popularMovieList);

  void showPopularMovieListNew(RealmResults<RealmMovie> topMoviesRealm);
}
