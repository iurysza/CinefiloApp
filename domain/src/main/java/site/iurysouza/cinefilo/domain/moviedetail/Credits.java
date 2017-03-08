package site.iurysouza.cinefilo.domain.moviedetail;

import java.util.List;
import lombok.Data;

@Data
public class Credits {
  private List<Cast> cast;
  private List<Crew> crew;
  private Long id;

  @java.beans.ConstructorProperties({ "cast", "crew", "id" }) Credits(List<Cast> cast,
      List<Crew> crew, Long id) {
    this.cast = cast;
    this.crew = crew;
    this.id = id;
  }

  public static CreditsBuilder builder() {
    return new CreditsBuilder();
  }

  public static class CreditsBuilder {
    private List<Cast> cast;
    private List<Crew> crew;
    private Long id;

    public CreditsBuilder() {
    }

    public Credits.CreditsBuilder cast(List<Cast> cast) {
      this.cast = cast;
      return this;
    }

    public Credits.CreditsBuilder crew(List<Crew> crew) {
      this.crew = crew;
      return this;
    }

    public Credits.CreditsBuilder id(Long id) {
      this.id = id;
      return this;
    }

    public Credits build() {
      return new Credits(cast, crew, id);
    }

    public String toString() {
      return "site.iurysouza.cinefilo.domain.moviedetail.Credits.CreditsBuilder(cast="
          + this.cast
          + ", crew="
          + this.crew
          + ", id="
          + this.id
          + ")";
    }
  }
}

