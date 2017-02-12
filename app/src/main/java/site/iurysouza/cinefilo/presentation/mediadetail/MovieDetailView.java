package site.iurysouza.cinefilo.presentation.mediadetail;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import java.util.List;
import site.iurysouza.cinefilo.domain.entity.WatchMediaValue;
import site.iurysouza.cinefilo.model.data.entity.MovieDetailValue;
import site.iurysouza.cinefilo.presentation.base.mvp.BaseView;

/**
 * Created by Iury Souza on 01/02/2017.
 */
public interface MovieDetailView extends BaseView{
  void updateMovieData(MovieDetailValue movieDetailValue);

  void showErrorWarning();

  void showSimilarMovies(List<WatchMediaValue> mediaValues);

  RxAppCompatActivity getRxAppCompatActivity();
}
