
package site.iurysouza.cinefilo.domain.moviedetail;

import lombok.Data;

@Data
public class Cast {

    private Long castId;
    private String character;
    private String creditId;
    private Long id;
    private String name;
    private Long order;
    private String profilePath;

    @java.beans.ConstructorProperties({
        "castId", "character", "creditId", "id", "name", "order", "profilePath"
    }) public Cast(Long castId, String character, String creditId, Long id, String name, Long order,
        String profilePath) {
        this.castId = castId;
        this.character = character;
        this.creditId = creditId;
        this.id = id;
        this.name = name;
        this.order = order;
        this.profilePath = profilePath;
    }

    public static CastBuilder builder() {
        return new CastBuilder();
    }

    public static class CastBuilder {
        private Long castId;
        private String character;
        private String creditId;
        private Long id;
        private String name;
        private Long order;
        private String profilePath;

        public CastBuilder() {
        }

        public Cast.CastBuilder castId(Long castId) {
            this.castId = castId;
            return this;
        }

        public Cast.CastBuilder character(String character) {
            this.character = character;
            return this;
        }

        public Cast.CastBuilder creditId(String creditId) {
            this.creditId = creditId;
            return this;
        }

        public Cast.CastBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public Cast.CastBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Cast.CastBuilder order(Long order) {
            this.order = order;
            return this;
        }

        public Cast.CastBuilder profilePath(String profilePath) {
            this.profilePath = profilePath;
            return this;
        }

        public Cast build() {
            return new Cast(castId, character, creditId, id, name, order, profilePath);
        }

        public String toString() {
            return "site.iurysouza.cinefilo.domain.moviedetail.Cast.CastBuilder(castId="
                + this.castId
                + ", character="
                + this.character
                + ", creditId="
                + this.creditId
                + ", id="
                + this.id
                + ", name="
                + this.name
                + ", order="
                + this.order
                + ", profilePath="
                + this.profilePath
                + ")";
        }
    }
}
