//package site.iurysouza.cinefilo.di.modules;
//
//import retrofit2.Retrofit;
//import retrofit2.mock.BehaviorDelegate;
//import site.iurysouza.cinefilo.model.services.MovieDetailService;
//import site.iurysouza.cinefilo.testhelpers.MockMovieDetailService;
//
///**
// * Created by Iury Souza on 14/02/2017.
// */
//public class MockMediaDetailModule extends MediaDetailModule {
//
//  @Override protected MovieDetailService providesMovieDetailService(Retrofit retrofit,
//      BehaviorDelegate<MovieDetailService> delegate) {
//    return new MockMovieDetailService(delegate);
//  }
//
//
//}
