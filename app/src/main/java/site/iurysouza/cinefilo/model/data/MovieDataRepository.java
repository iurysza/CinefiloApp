package site.iurysouza.cinefilo.model.data;

import android.support.annotation.UiThread;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import site.iurysouza.cinefilo.model.data.storage.CloudMovieDataStore;
import site.iurysouza.cinefilo.model.data.storage.LocalMovieDataStore;
import site.iurysouza.cinefilo.model.entities.realm.RealmGenre;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;
import site.iurysouza.cinefilo.util.Constants;
import timber.log.Timber;

/**
 * Created by Iury Souza on 12/10/2016.
 */

@UiThread
public class MovieDataRepository implements MovieRepository {

  public static final int MONTHS_STALED = 2;
  private LocalMovieDataStore localDataStore;
  private CloudMovieDataStore cloudDataStore;
  private Realm realm;
  private InputStream genresFromJson;

  @Inject
  public MovieDataRepository(LocalMovieDataStore localDataStore,
      CloudMovieDataStore cloudDataStore, Realm realm, InputStream genresFromJson) {
    this.localDataStore = localDataStore;
    this.cloudDataStore = cloudDataStore;
    this.realm = realm;
    this.genresFromJson = genresFromJson;
  }

  @Override public Observable<RealmMovie> getMovieById(int movieId) {
    cloudDataStore.movieById(movieId);
    return localDataStore.movieById(movieId);
  }

  @Override
  public Observable<RealmResults<RealmMovie>> getMoviesByPopulariy(int page, boolean forceRemote) {
    if (forceRemote || isDataStaled(System.currentTimeMillis())) {
      cloudDataStore.getMostPopularMovies(page);
    }
    return localDataStore.getMostPopularMovies();
  }

  @Override
  public Observable<RealmResults<RealmMovie>> getTopRatedMovies(int page, boolean forceRemote) {
    if (forceRemote || isDataStaled(System.currentTimeMillis())) {
      cloudDataStore.getTopRatedMovies(page);
    }
    return localDataStore.getTopRatedMovies();
  }

  @Override
  public Observable<RealmResults<RealmMovie>> getNowPlayingMovies(int page, boolean forceRemote) {
    if (forceRemote || isDataStaled(System.currentTimeMillis())) {
      cloudDataStore.getNowPlayingMovies(page);
    }
    return localDataStore.getNowPlayingMovies();
  }

  @Override
  public Observable<List<RealmMovie>> getShowCaseMovies() {
    return localDataStore.getShowCaseMovies();
  }

  @Override
  public void getGenreList() {
    RealmGenre first = realm
        .where(RealmGenre.class)
        .isNotNull(RealmGenre.QUERY_DATE)
        .findFirstAsync();

    RealmObject.asObservable(first)
        .filter(RealmObject::isLoaded)
        .subscribe(realmGenre -> {
          try {
            if (!RealmObject.isValid(realmGenre)) {
              insertGenresToRealm(readJsonStream(genresFromJson));
            } else {
              if (isDataStaled(realmGenre.getQueryDate())) {
                //cloudDataStore.getUpdateGenreList();
              }
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
  }

  private List<RealmGenre> readJsonStream(InputStream in) throws IOException {
    JsonReader reader = new JsonReader(new InputStreamReader(in, Constants.JSON_CHAR_SET));
    List<RealmGenre> genreList = new ArrayList<>();
    reader.beginObject();
    reader.nextName();
    reader.beginArray();
    while (reader.hasNext()) {
      RealmGenre genre = new Gson().fromJson(reader, RealmGenre.class);
      genre.setQueryDate(System.currentTimeMillis());
      genreList.add(genre);
    }
    reader.endArray();
    reader.close();
    return genreList;
  }

  private boolean isDataStaled(Long timeStamp) {
    if (timeStamp == null) {
      return false;
    }
    Calendar timeStampCalendar = Calendar.getInstance();
    timeStampCalendar.setTimeInMillis(timeStamp);
    int stampMonth = timeStampCalendar.get(Calendar.MONTH);

    Calendar currentCalendar = Calendar.getInstance();
    currentCalendar.setTimeInMillis(System.currentTimeMillis());
    int currentMonth = currentCalendar.get(Calendar.MONTH);

    //return (Math.abs(stampMonth - currentMonth) > MONTHS_STALED);
    return false;
  }

  private void insertGenresToRealm(List<RealmGenre> genreList) {
    realm.executeTransactionAsync(realm -> {
          realm.insertOrUpdate(genreList);
        },
        throwable -> {
          Timber.e(throwable, "Could not save data");
        });
  }

  @Override public void close() {
    realm.close();
  }
}
