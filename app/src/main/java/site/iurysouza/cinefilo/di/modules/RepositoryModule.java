package site.iurysouza.cinefilo.di.modules;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import retrofit2.Retrofit;
import site.iurysouza.cinefilo.model.data.storage.CloudMovieDataStore;
import site.iurysouza.cinefilo.model.data.storage.LocalMovieDataStore;
import site.iurysouza.cinefilo.model.data.MovieDataRepository;
import site.iurysouza.cinefilo.model.services.MovieService;
import site.iurysouza.cinefilo.domain.MoviesUseCase;
import site.iurysouza.cinefilo.presentation.home.HomePresenter;
import site.iurysouza.cinefilo.presentation.movies.MoviesPresenter;

/**
 * Created by Iury Souza on 12/10/2016.
 */
@Module
public class RepositoryModule {
  @Provides
  MovieService providesMovieService(Retrofit retrofit) {
    return retrofit.create(MovieService.class);
  }

  @Provides CloudMovieDataStore providesCloudMovieDataStore(MovieService movieService,
      Realm realm) {
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

  @Provides MoviesUseCase providesMovieUseCase(MovieDataRepository dataRepository) {
    return new MoviesUseCase(dataRepository);
  }

  @Provides MoviesPresenter providesMoviePresenter(MoviesUseCase useCase) {
    return new MoviesPresenter(useCase);
  }
}
