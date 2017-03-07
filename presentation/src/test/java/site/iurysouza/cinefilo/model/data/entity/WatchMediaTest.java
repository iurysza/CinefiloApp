//package site.iurysouza.cinefilo.model.data.entity;
//
//import java.util.Arrays;
//import java.util.List;
//import org.hamcrest.collection.IsCollectionWithSize;
//import org.hamcrest.core.IsNull;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.runners.MockitoJUnitRunner;
//import site.iurysouza.cinefilo.model.entities.pojo.Movie;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static site.iurysouza.cinefilo.model.data.entity.MovieFactory.createStubWithInvalidDate;
//import static site.iurysouza.cinefilo.model.data.entity.MovieFactory.createStubWithInvalidOverView;
//import static site.iurysouza.cinefilo.model.data.entity.MovieFactory.createStubWithInvalidPosterPath;
//import static site.iurysouza.cinefilo.model.data.entity.MovieFactory.createStubWithInvalidTitle;
//import static site.iurysouza.cinefilo.model.data.entity.MovieFactory.createValidMovieStub;
//
///**
// * Created by Iury Souza on 10/02/2017.
// */
//
//@RunWith(MockitoJUnitRunner.class)
//public class WatchMediaTest {
//
//  @Test
//  public void shouldNotMapListOfInvalidMovies() throws Exception {
//    //given
//    List<Movie> movieList =
//        Arrays.asList(
//            createValidMovieStub(),
//            createStubWithInvalidTitle(),
//            createStubWithInvalidOverView(),
//            createStubWithInvalidPosterPath(),
//            createStubWithInvalidDate());
//
//    //when
//    List<WatchMedia> watchMediaList = WatchMedia.valueOfMovieList(movieList);
//
//    //then
//    assertThat(watchMediaList, IsCollectionWithSize.hasSize(1));
//  }
//
//  @Test
//  public void shouldMapValidMovie() throws Exception {
//    //given
//    Movie validMovieStub = createValidMovieStub();
//
//    //when
//    WatchMedia watchMedia = WatchMedia.valueOf(validMovieStub);
//
//    //then
//    assertThat(watchMedia, IsNull.notNullValue());
//  }
//
//  @Test
//  public void shouldNotMapMovieWithInvalidPosterPath() throws Exception {
//    //given
//    Movie movieWithInvalidPosterPath = createStubWithInvalidPosterPath();
//    //when
//    WatchMedia watchMedia = WatchMedia.valueOf(movieWithInvalidPosterPath);
//
//    //then
//    assertThat(watchMedia, IsNull.nullValue());
//  }
//
//  @Test
//  public void shouldNotMapMovieWithInvalidTitle() throws Exception {
//    //given
//    Movie movieWithInvalidTitle = createStubWithInvalidTitle();
//    //when
//    WatchMedia watchMedia = WatchMedia.valueOf(movieWithInvalidTitle);
//
//    //then
//    assertThat(watchMedia, IsNull.nullValue());
//  }
//
//  @Test
//  public void shouldNotMapMovieWithInvalidDate() throws Exception {
//    //given
//    Movie movieWithInvalidDate = createStubWithInvalidDate();
//    //when
//    WatchMedia watchMedia = WatchMedia.valueOf(movieWithInvalidDate);
//
//    //then
//    assertThat(watchMedia, IsNull.nullValue());
//  }
//
//  @Test
//  public void shouldNotMapMovieWithInvalidOverview() throws Exception {
//    //given
//    Movie movieWithInvalidOverview = createStubWithInvalidOverView();
//    //when
//    WatchMedia watchMedia = WatchMedia.valueOf(movieWithInvalidOverview);
//
//    //then
//    assertThat(watchMedia, IsNull.nullValue());
//  }
//}