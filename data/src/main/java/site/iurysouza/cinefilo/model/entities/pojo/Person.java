
package site.iurysouza.cinefilo.model.entities.pojo;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Person {
    @SerializedName("adult")
    private Boolean adult;
    @SerializedName("also_known_as")
    private List<String> alsoKnownAs = null;
    @SerializedName("biography")
    private String biography;
    @SerializedName("birthday")
    private Date birthday;
    @SerializedName("deathday")
    private Date deathday;
    @SerializedName("gender")
    private Integer gender;
    @SerializedName("homepage")
    private String homepage;
    @SerializedName("id")
    private long id;
    @SerializedName("imdb_id")
    private String imdbId;
    @SerializedName("name")
    private String name;
    @SerializedName("place_of_birth")
    private String placeOfBirth;
    @SerializedName("popularity")
    private Double popularity;
    @SerializedName("profile_path")
    private String profilePath;
}
