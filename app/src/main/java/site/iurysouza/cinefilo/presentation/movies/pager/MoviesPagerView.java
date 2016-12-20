package site.iurysouza.cinefilo.presentation.movies.pager;

import java.util.List;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;
import site.iurysouza.cinefilo.presentation.base.mvp.BaseView;

/**
 * Created by Iury Souza on 20/12/2016.
 */
public interface MoviesPagerView extends BaseView {
  void createHeaderChangeListener(List<RealmMovie> popularMoviesRealm);
}
