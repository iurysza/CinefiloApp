package site.iurysouza.cinefilo.di.modules;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import retrofit2.Retrofit;
import site.iurysouza.cinefilo.data.Repos.DataStore.CloudMovieDataStore;
import site.iurysouza.cinefilo.data.Repos.DataStore.LocalMovieDataStore;
import site.iurysouza.cinefilo.data.Repos.MovieDataRepository;
import site.iurysouza.cinefilo.data.services.MovieService;
import site.iurysouza.cinefilo.presentation.home.HomePresenter;

/**
 * Created by Iury Souza on 12/10/2016.
 */
@Module
public class RepositoryModule {
  @Provides
  MovieService providesMovieService(Retrofit retrofit) {
    return retrofit.create(MovieService.class);
  }
  @Provides CloudMovieDataStore providesCloudMovieDataStore(MovieService movieService, Realm realm) {
    return new CloudMovieDataStore(movieService, realm);
  }
  @Provides LocalMovieDataStore providesLocalMovieDataStore(Realm realm) {
    return new LocalMovieDataStore(realm);
  }
  @Provides MovieDataRepository providesMovieDataRepository(LocalMovieDataStore localMovieDataStore,
      CloudMovieDataStore cloudMovieDataStore, Realm realm) {
    return new MovieDataRepository(localMovieDataStore, cloudMovieDataStore, realm);
  }
  @Provides HomePresenter providesHomePresenter(MovieDataRepository dataRepository) {
    return new HomePresenter(dataRepository);
  }
}
