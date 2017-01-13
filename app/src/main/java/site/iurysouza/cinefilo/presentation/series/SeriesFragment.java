package site.iurysouza.cinefilo.presentation.series;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import javax.inject.Inject;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;
import site.iurysouza.cinefilo.presentation.CineApplication;
import site.iurysouza.cinefilo.presentation.base.BaseFragment;
import timber.log.Timber;

/**
 * Created by Iury Souza on 09/11/2016.
 */

public class SeriesFragment extends BaseFragment implements SeriesView {

  @BindView(R.id.text) TextView text;

  @Inject SeriesPresenter seriesPresenter;

  public static SeriesFragment newInstance() {
    SeriesFragment seriesFragment = new SeriesFragment();
    Bundle args = new Bundle();
    seriesFragment.setArguments(args);
    return seriesFragment;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.content_home_fragment, container, false);
    ButterKnife.bind(this, view);

    seriesPresenter.attachView(this);
    //seriesPresenter.getById(25);

    return view;
  }


  @Override public void onDestroyView() {
    super.onDestroyView();
    seriesPresenter.dettachView();
  }

  @Override public void showLoadingIndicator() {

  }

  @Override public void hideLoadingIndicator() {

  }

  @Override public void showErrorIndicator() {

  }

  @Override public void showRetrievedMovie(RealmMovie realmMovie) {
    text.setText(realmMovie.getOriginalTitle());
    Timber.e("GOT MOVIE: %s", realmMovie.getOriginalTitle());
  }

  @Override protected void setupFragmentComponent() {
    ((CineApplication) getContext().getApplicationContext()).getRepositoryComponent().inject(this);
  }
}
