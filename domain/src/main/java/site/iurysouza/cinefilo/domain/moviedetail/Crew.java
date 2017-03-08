
package site.iurysouza.cinefilo.domain.moviedetail;

import lombok.Data;

@Data
public class Crew {
    private String creditId;
    private String department;
    private Long id;
    private String job;
    private String name;
    private String profilePath;

    @java.beans.ConstructorProperties({
        "creditId", "department", "id", "job", "name", "profilePath"
    }) Crew(String creditId, String department, Long id, String job, String name,
        String profilePath) {
        this.creditId = creditId;
        this.department = department;
        this.id = id;
        this.job = job;
        this.name = name;
        this.profilePath = profilePath;
    }

    public static CrewBuilder builder() {
        return new CrewBuilder();
    }

    public static class CrewBuilder {
        private String creditId;
        private String department;
        private Long id;
        private String job;
        private String name;
        private String profilePath;

        public CrewBuilder() {
        }

        public Crew.CrewBuilder creditId(String creditId) {
            this.creditId = creditId;
            return this;
        }

        public Crew.CrewBuilder department(String department) {
            this.department = department;
            return this;
        }

        public Crew.CrewBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public Crew.CrewBuilder job(String job) {
            this.job = job;
            return this;
        }

        public Crew.CrewBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Crew.CrewBuilder profilePath(String profilePath) {
            this.profilePath = profilePath;
            return this;
        }

        public Crew build() {
            return new Crew(creditId, department, id, job, name, profilePath);
        }

        public String toString() {
            return "site.iurysouza.cinefilo.domain.moviedetail.Crew.CrewBuilder(creditId="
                + this.creditId
                + ", department="
                + this.department
                + ", id="
                + this.id
                + ", job="
                + this.job
                + ", name="
                + this.name
                + ", profilePath="
                + this.profilePath
                + ")";
        }
    }
}
