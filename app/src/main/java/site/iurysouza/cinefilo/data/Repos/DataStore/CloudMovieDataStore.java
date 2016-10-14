package site.iurysouza.cinefilo.data.Repos.DataStore;

import io.realm.Realm;
import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import site.iurysouza.cinefilo.data.entities.MovieEntity;
import site.iurysouza.cinefilo.data.entities.MovieRealm;
import site.iurysouza.cinefilo.data.services.MovieService;
import site.iurysouza.cinefilo.util.Constants;
import timber.log.Timber;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class CloudMovieDataStore {
  private MovieService movieService;
  private Realm realm;

  @Inject
  public CloudMovieDataStore(MovieService movieService, Realm realm) {
    this.movieService = movieService;
    this.realm = realm;
  }

  public void movieById(int movieId) {
    movieService
        .getMovieById(movieId, Constants.MOVIE_DB_API.API_KEY)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::processAndAddData, throwable -> {
          Timber.e("Failure: Movie Data not with id: %s - error: %s", movieId,
              throwable.toString());
        });
  }

  private void processAndAddData(MovieEntity m) {
    MovieRealm movieRealm = new MovieRealm();
    movieRealm.setAdult(m.getAdult());
    movieRealm.setBackdropPath(m.getBackdropPath());
    //movieRealm.setBelongsToCollection(m.getBelongsToCollection());
    movieRealm.setBudget(m.getBudget());
    //movieRealm.setGenreEntities(m.getGenreEntities());
    movieRealm.setHomepage(m.getHomepage());
    movieRealm.setId(m.getId());
    movieRealm.setImdbId(m.getImdbId());
    movieRealm.setHomepage(m.getHomepage());
    movieRealm.setOriginalLanguage(m.getOriginalLanguage());
    movieRealm.setOriginalTitle(m.getOriginalTitle());
    movieRealm.setOverview(m.getOverview());
    movieRealm.setPopularity(m.getPopularity());
    movieRealm.setPosterPath(m.getPosterPath());
    //movieRealm.setProductionCompanies(m.getProductionCompanies());
    //movieRealm.setProductionCountries(m.getProductionCountries());
    movieRealm.setReleaseDate(m.getReleaseDate());
    movieRealm.setRevenue(m.getRevenue());
    movieRealm.setRuntime(m.getRuntime());
    //movieRealm.setSpokenLangEntities(m.getSpokenLangEntities());
    movieRealm.setStatus(m.getStatus());
    movieRealm.setTagline(m.getTagline());
    movieRealm.setTitle(m.getTitle());
    movieRealm.setVideo(m.getVideo());
    movieRealm.setVoteAverage(m.getVoteAverage());
    movieRealm.setVoteCount(m.getVoteCount());

    realm.executeTransactionAsync(realm -> {
      realm.insertOrUpdate(movieRealm);
    }, throwable -> {
      Timber.e(throwable, "Could not save data");
    });
  }
}
