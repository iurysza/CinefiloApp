package site.iurysouza.cinefilo.model.entities.mapper;

import io.realm.RealmList;
import site.iurysouza.cinefilo.model.entities.pojo.Movie;
import site.iurysouza.cinefilo.model.entities.pojo.Results;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;
import site.iurysouza.cinefilo.model.entities.realm.RealmMoviesResults;

public class MovieDataMapper {

  public static RealmMovie map(Movie movie) {
    RealmMovie realmMovie = new RealmMovie();
    realmMovie.setAdult(movie.getAdult());
    realmMovie.setBackdropPath(movie.getBackdropPath());
    realmMovie.setId(movie.getId().intValue());
    realmMovie.setOriginalLanguage(movie.getOriginalLanguage());
    realmMovie.setOriginalTitle(movie.getOriginalTitle());
    realmMovie.setOverview(movie.getOverview());
    realmMovie.setPopularity(movie.getPopularity());
    realmMovie.setPosterPath(movie.getPosterPath());
    realmMovie.setVideo(movie.getVideo());
    realmMovie.setVoteAverage(movie.getVoteAverage());
    realmMovie.setVoteCount(movie.getVoteCount());
    realmMovie.setReleaseDate(movie.getReleaseDate());
    realmMovie.setRuntime(movie.getRuntime());
    realmMovie.setStatus(movie.getStatus());
    realmMovie.setTagline(movie.getTagline());
    realmMovie.setTitle(movie.getTitle());
    realmMovie.setRevenue(movie.getRevenue().intValue());
    realmMovie.setGenreList(GenreDataMapper.map(movie.getGenreList()));
    realmMovie.setQueryDate(System.currentTimeMillis());
    realmMovie.setProductionCompanyList(
        ProdCompanyDataMapper.map(movie.getProductionCompanyList()));
    realmMovie.setProductionCountryList(ProdCountryDataMapper.map(movie.getProductionCountryList()));
    realmMovie.setSpokenLanguageList(LanguageDataMapper.map(movie.getSpokenLanguageList()));
    return realmMovie;
  }

  public static RealmMoviesResults mapResultsToRealmResults(Results results, int queryType) {
    RealmList<RealmMovie> realmMovieList = new RealmList<>();
    for (Movie movie : results.getMovieList()) {
      realmMovieList.add(mapMovieResult(movie, queryType));
    }
    RealmMoviesResults realmMoviesResults = new RealmMoviesResults();
    realmMoviesResults.setMovieList(realmMovieList);
    realmMoviesResults.setPage(results.getPage());
    return realmMoviesResults;
  }



  public static RealmMovie mapMovieResult(Movie movie, int queryType) {
    RealmMovie realmMovie = new RealmMovie();
    realmMovie.setAdult(movie.getAdult());
    realmMovie.setBackdropPath(movie.getBackdropPath());
    realmMovie.setId(movie.getId().intValue());
    realmMovie.setOriginalLanguage(movie.getOriginalLanguage());
    realmMovie.setOriginalTitle(movie.getOriginalTitle());
    realmMovie.setOverview(movie.getOverview());
    realmMovie.setPopularity(movie.getPopularity());
    realmMovie.setPosterPath(movie.getPosterPath());
    realmMovie.setVideo(movie.getVideo());
    realmMovie.setVoteAverage(movie.getVoteAverage());
    realmMovie.setVoteCount(movie.getVoteCount());
    realmMovie.setReleaseDate(movie.getReleaseDate());
    realmMovie.setGenreIds(RealmIntegerMapper.map(movie.getGenreIds()));
    realmMovie.setQueryDate(System.currentTimeMillis());
    realmMovie.setQueryType(queryType);
    return realmMovie;
  }
}
