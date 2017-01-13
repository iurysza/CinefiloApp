package site.iurysouza.cinefilo.model.entities.mapper;

import io.realm.RealmList;
import site.iurysouza.cinefilo.model.entities.pojo.Series;
import site.iurysouza.cinefilo.model.entities.pojo.SeriesResult;
import site.iurysouza.cinefilo.model.entities.realm.RealmSeries;
import site.iurysouza.cinefilo.model.entities.realm.RealmSeriesResults;

public class SeriesDataMapper {

  public static RealmSeries map(Series series) {
    RealmSeries realmSeries = new RealmSeries();
    realmSeries.setBackdropPath(series.getBackdropPath());
    realmSeries.setFirstAirDate(series.getFirstAirDate());
    realmSeries.setLastAirDate(series.getLastAirDate());
    realmSeries.setHomepage(series.getHomepage());
    realmSeries.setInProduction(series.getInProduction());
    realmSeries.setName(series.getName());
    realmSeries.setId(series.getId());

    realmSeries.setNumberOfEpisodes(series.getNumberOfEpisodes());
    realmSeries.setNumberOfSeasons(series.getNumberOfSeasons());
    realmSeries.setOriginalLanguage(series.getOriginalLanguage());
    realmSeries.setOriginalName(series.getOriginalName());
    realmSeries.setOverview(series.getOverview());
    realmSeries.setPopularity(series.getPopularity());
    realmSeries.setPosterPath(series.getPosterPath());

    realmSeries.setVoteAverage(series.getVoteAverage());
    realmSeries.setVoteCount(series.getVoteCount());
    realmSeries.setStatus(series.getStatus());
    realmSeries.setType(series.getType());
    realmSeries.setQueryDate(System.currentTimeMillis());

    realmSeries.setGenreList(GenreDataMapper.map(series.getGenreList()));
    realmSeries.setCreatedByList(PersonDataMapper.map(series.getCreatedByList()));
    realmSeries.setSeasonList(SeasonDataMapper.map(series.getSeasons()));
    realmSeries.setNetworkList(NetworkDataMapper.map(series.getNetworks()));

    realmSeries.setProductionCompanyList(
        ProdCompanyDataMapper.map(series.getProductionCompanyList()));
    realmSeries.setLanguageList(RealmStringMapper.map(series.getLanguages()));
    realmSeries.setOriginCountryList(RealmStringMapper.map(series.getOriginCountry()));

    return realmSeries;
  }

  public static RealmSeriesResults mapResultsToRealmResults(SeriesResult seriesResult, int queryType) {
    RealmList<RealmSeries> realmSeriesList = new RealmList<>();
    for (Series series : seriesResult.getSeriesList()) {
      realmSeriesList.add(mapSeriesResult(series, queryType));
    }
    RealmSeriesResults realmSeriesSeriesResult = new RealmSeriesResults();
    realmSeriesSeriesResult.setSeriesList(realmSeriesList);
    realmSeriesSeriesResult.setPage(seriesResult.getPage());
    return realmSeriesSeriesResult;
  }

  public static RealmSeries mapSeriesResult(Series series, int queryType) {
    RealmSeries realmSeries = new RealmSeries();
    realmSeries.setPosterPath(series.getPosterPath());
    realmSeries.setId(series.getId());
    realmSeries.setPopularity(series.getPopularity());
    realmSeries.setBackdropPath(series.getBackdropPath());
    realmSeries.setOverview(series.getOverview());
    realmSeries.setFirstAirDate(series.getFirstAirDate());
    realmSeries.setOriginCountryList(RealmStringMapper.map(series.getOriginCountry()));
    realmSeries.setGenreIds(RealmIntegerMapper.map(series.getGenres()));
    realmSeries.setOriginalLanguage(series.getOriginalLanguage());
    realmSeries.setName(series.getName());
    realmSeries.setVoteCount(series.getVoteCount());
    realmSeries.setOriginalName(series.getOriginalName());
    realmSeries.setQueryType(queryType);
    return realmSeries;
  }
}
