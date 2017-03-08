
package site.iurysouza.cinefilo.model.entities.pojo;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.experimental.Builder;
import site.iurysouza.cinefilo.domain.moviedetail.Crew;

@Data
@Builder
public class MovieCrew {
    @SerializedName("credit_id")
    private String creditId;
    @SerializedName("department")
    private String department;
    @SerializedName("id")
    private Long id;
    @SerializedName("job")
    private String job;
    @SerializedName("name")
    private String name;
    @SerializedName("profile_path")
    private String profilePath;

    public static List<Crew> valueOf(List<MovieCrew> movieCrewList) {
        List<Crew> crewList = new ArrayList<>();
        if (movieCrewList != null && !movieCrewList.isEmpty()) {
            for (MovieCrew movieCrew : movieCrewList) {
                crewList.add(valueOf(movieCrew));
            }
        }
        return crewList;
    }

    public static Crew valueOf(MovieCrew movieCrew) {
        return new Crew.CrewBuilder()
            .id(movieCrew.getId())
            .job(movieCrew.getJob())
            .name(movieCrew.getName())
            .creditId(movieCrew.getCreditId())
            .profilePath(movieCrew.getProfilePath())
            .build();
    }
}
