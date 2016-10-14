package site.iurysouza.cinefilo.presentation;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import javax.inject.Inject;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.data.entities.MovieRealm;
import site.iurysouza.cinefilo.presentation.base.BaseActivity;
import site.iurysouza.cinefilo.presentation.home.HomePresenter;
import site.iurysouza.cinefilo.presentation.home.HomeView;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements HomeView {

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.text) TextView textView;
  @Inject HomePresenter homePresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    homePresenter.attachView(this);
  }

  @Override protected void setupActivityComponent() {
    appInstance.createRepositoryComponent().inject(this);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    appInstance.releaseRepositoryComponent();
    homePresenter.dettachView();
  }

  @OnClick(R.id.fab) public void onClick(View view) {
    homePresenter.getMovieById(25);
  }

  @Override public void showLoadingIndicator() {

  }

  @Override public void hideLoadingIndicator() {

  }

  @Override public void showErrorIndicator() {

  }

  @Override public void showRetrievedMovie(MovieRealm movieRealm) {
    textView.setText(movieRealm.getTitle());
    Timber.d("movie shown on main activity: %s", movieRealm.getTitle());
  }
}
