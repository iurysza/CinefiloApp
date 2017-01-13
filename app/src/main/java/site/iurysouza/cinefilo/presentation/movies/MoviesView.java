package site.iurysouza.cinefilo.presentation.movies;

import java.util.List;
import site.iurysouza.cinefilo.domain.entity.WatchMediaValue;
import site.iurysouza.cinefilo.presentation.base.mvp.BaseView;

/**
 * Created by Iury Souza on 12/10/2016.
 */
public interface MoviesView extends BaseView {
  void showLoadingIndicator();

  void hideLoadingIndicator();

  void showErrorIndicator();

  void sendToListView(List<WatchMediaValue> watchMediaValuesList);
}
