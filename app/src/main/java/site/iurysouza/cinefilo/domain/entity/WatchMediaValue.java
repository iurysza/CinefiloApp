package site.iurysouza.cinefilo.domain.entity;

import android.os.Parcelable;
import com.google.auto.value.AutoValue;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Nullable;
import site.iurysouza.cinefilo.model.data.entity.WatchMedia;

/**
 * Created by Iury Souza on 06/01/2017.
 */
@AutoValue
public abstract class WatchMediaValue implements Parcelable{

  static Builder builder() {
    return new AutoValue_WatchMediaValue.Builder();
  }

  public abstract long id();

  public abstract Double voteAverage();

  public abstract Integer genre();

  public abstract String name();

  public abstract String overview();

  public abstract Date releaseDate();

  public abstract String posterPath();

  @Nullable public abstract String backdropPath();

  @Override public String toString() {
    return name();
  }

  @AutoValue.Builder
 public abstract static class Builder {
    abstract Builder voteAverage(Double overview);

    abstract Builder genre(Integer overview);

    abstract Builder id(long id);

    abstract Builder name(String name);

    abstract Builder releaseDate(Date date);

    abstract Builder overview(String overview);

    abstract Builder posterPath(String overview);

    abstract Builder backdropPath(String overview);

    abstract WatchMediaValue build();
  }

  public static WatchMediaValue mapToValueMedia(WatchMedia media) {
    WatchMediaValue build = WatchMediaValue
        .builder()
        .id(media.getId())
        .voteAverage(media.getVoteAverage())
        .overview(media.getOverview())
        .backdropPath(media.getBackdropPath())
        .posterPath(media.getPosterPath())
        .releaseDate(media.getReleaseDate())
        .name(media.getName())
        .genre(media.getGenre())
        .build();
    return build;
  }

  public static List<WatchMediaValue> mapToValueMedia(List<WatchMedia> mediaList) {
    List<WatchMediaValue> valueMediaList = new ArrayList<>();

    if (mediaList.isEmpty()) {
      return valueMediaList;
    }

    for (WatchMedia media : mediaList) {
      valueMediaList.add(mapToValueMedia(media));
    }
    return valueMediaList;
  }
}
