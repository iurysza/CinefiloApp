package site.iurysouza.cinefilo.domain.entity;

import com.google.auto.value.AutoValue;
import java.util.Date;
import javax.annotation.Nullable;

/**
 * Created by Iury Souza on 06/01/2017.
 */
@AutoValue
public abstract class WatchMediaValue {

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
  abstract static class Builder {
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
}
