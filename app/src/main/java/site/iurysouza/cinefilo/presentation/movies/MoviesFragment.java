package site.iurysouza.cinefilo.presentation.movies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import javax.inject.Inject;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.model.entities.realm.RealmPopularMovies;
import site.iurysouza.cinefilo.presentation.CineApplication;
import site.iurysouza.cinefilo.presentation.base.BaseFragment;
import timber.log.Timber;

/**
 * Created by Iury Souza on 09/11/2016.
 */

public class MoviesFragment extends BaseFragment implements MoviesView {

  @BindView(R.id.main_toolbar) Toolbar toolbar;
  @BindView(R.id.text) TextView text;

  @Inject MoviesPresenter moviesPresenter;

  public static MoviesFragment newInstance() {
    MoviesFragment moviesFragment = new MoviesFragment();

    Bundle args = new Bundle();
    moviesFragment.setArguments(args);
    return moviesFragment;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.content_main, container, false);
    ButterKnife.bind(this, view);

    ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    moviesPresenter.attachView(this);
    moviesPresenter.loadMovies();
    return view;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    moviesPresenter.dettachView();
  }

  @Override public void showLoadingIndicator() {

  }

  @Override public void hideLoadingIndicator() {

  }

  @Override public void showErrorIndicator() {

  }

  @Override public void showPopularMovieList(RealmPopularMovies popularMovieList) {
    int size = popularMovieList.getMovieList().size();
    Timber.e("GOT %s MOVIES", size);
    text.setText(String.valueOf(size));
  }


  @Override protected void setupFragmentComponent() {
    ((CineApplication) getContext().getApplicationContext()).getRepositoryComponent().inject(this);
  }
}
