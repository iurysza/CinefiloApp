package site.iurysouza.cinefilo.presentation.movies;

import io.realm.RealmModel;
import site.iurysouza.cinefilo.presentation.base.mvp.BaseView;

/**
 * Created by Iury Souza on 12/10/2016.
 */
public interface MoviesView extends BaseView {
  void showLoadingIndicator();

  void hideLoadingIndicator();

  void showErrorIndicator();

  void showPopularMovieList(RealmModel popularMovieList);


}
