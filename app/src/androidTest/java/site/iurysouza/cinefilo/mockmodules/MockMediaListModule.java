//package site.iurysouza.cinefilo.mockmodules;
//
//import site.iurysouza.cinefilo.di.modules.MediaListModule;
//import site.iurysouza.cinefilo.domain.watchmedialist.MoviesWatchMediaListUseCase;
//import site.iurysouza.cinefilo.domain.watchmedialist.SeriesWatchMediaListUseCase;
//import site.iurysouza.cinefilo.model.data.movies.MoviesRepository;
//import site.iurysouza.cinefilo.model.data.movies.storage.CloudMovieDataSource;
//import site.iurysouza.cinefilo.model.data.movies.storage.ICloudMovieDataSource;
//import site.iurysouza.cinefilo.model.data.movies.storage.ILocalMovieDataSource;
//import site.iurysouza.cinefilo.model.data.movies.storage.LocalMovieDataSource;
//import site.iurysouza.cinefilo.model.data.series.SeriesRepository;
//import site.iurysouza.cinefilo.model.data.series.storage.CloudSeriesDataSource;
//import site.iurysouza.cinefilo.model.data.series.storage.ICloudSeriesDataSource;
//import site.iurysouza.cinefilo.model.data.series.storage.ILocalSeriesDataSource;
//import site.iurysouza.cinefilo.model.data.series.storage.LocalSeriesDataSource;
//import site.iurysouza.cinefilo.model.services.MovieService;
//import site.iurysouza.cinefilo.model.services.SeriesService;
//
//import static org.mockito.Mockito.mock;
//
///**
// * Created by Iury Souza on 13/02/2017.
// */
//
//public class MockMediaListModule extends MediaListModule {
//
//  @Override public ICloudMovieDataSource providesCloudMovieDataSource(MovieService movieService) {
//    return mock(CloudMovieDataSource.class);
//  }
//
//  @Override public ICloudSeriesDataSource providesCloudSeriesDataSource(SeriesService seriesService) {
//    return mock(CloudSeriesDataSource.class);
//  }
//
//  @Override public ILocalMovieDataSource providesLocalMovieDataSource() {
//    return mock(LocalMovieDataSource.class);
//  }
//
//  @Override public ILocalSeriesDataSource providesLocalSeriesDataSource() {
//    return mock(LocalSeriesDataSource.class);
//  }
//
//  @Override public MoviesRepository providesMovieDataRepository(LocalMovieDataSource localMovieDataSource,
//      CloudMovieDataSource cloudMovieDataSource) {
//    return new MoviesRepository(localMovieDataSource, cloudMovieDataSource);
//  }
//
//  @Override public SeriesRepository providesSeriesDataRepository(
//      LocalSeriesDataSource localSeriesDataSource,
//      CloudSeriesDataSource cloudSeriesDataSource) {
//    return new SeriesRepository(localSeriesDataSource, cloudSeriesDataSource);
//  }
//
//  @Override public MoviesWatchMediaListUseCase providesMovieUseCase(MoviesRepository dataRepository) {
//    return new MoviesWatchMediaListUseCase(dataRepository);
//  }
//
//  @Override public SeriesWatchMediaListUseCase providesSeriesUseCase(SeriesRepository dataRepository) {
//    return new SeriesWatchMediaListUseCase(dataRepository);
//  }
//}