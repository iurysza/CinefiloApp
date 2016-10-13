package site.iurysouza.cinefilo.data.Repos.DataStore;

import site.iurysouza.cinefilo.data.entities.MovieEntity;
import site.iurysouza.cinefilo.data.entities.MovieRealm;

/**
 * Created by Iury Souza on 12/10/2016.
 */
public class MovieDataMapper {
  public static MovieEntity transform(MovieRealm m) {
    MovieEntity movieEntity = new MovieEntity();
    movieEntity
        .withAdult(m.getAdult())
        .withBackdropPath(m.getBackdropPath())
        //.withBelongsToCollection(m.getBelongsToCollection())
        .withBudget(m.getBudget())
        //.withGenres(m.getGenreEntities())
        .withHomepage(m.getHomepage())
        .withId(m.getId())
        .withImdbId(m.getImdbId())
        .withHomepage(m.getHomepage())
        .withOriginalLanguage(m.getOriginalLanguage())
        .withOriginalTitle(m.getOriginalTitle())
        .withOverview(m.getOverview())
        .withPopularity(m.getPopularity())
        .withPosterPath(m.getPosterPath())
        //.withProductionCompanies(m.getProductionCompanies())
        //.withProductionCountries(m.getProductionCountries())
        .withReleaseDate(m.getReleaseDate())
        .withRevenue(m.getRevenue())
        .withRuntime(m.getRuntime())
        //.withSpokenLanguages(m.getSpokenLangEntities())
        .withStatus(m.getStatus())
        .withTagline(m.getTagline())
        .withTitle(m.getTitle())
        .withVideo(m.getVideo())
        .withVoteAverage(m.getVoteAverage())
        .withVoteCount(m.getVoteCount());
    return movieEntity;
  }
}
