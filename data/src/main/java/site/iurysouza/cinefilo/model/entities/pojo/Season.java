
package site.iurysouza.cinefilo.model.entities.pojo;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
import javax.annotation.Generated;
import lombok.Data;

@Generated("net.hexar.json2pojo")
@Data
@SuppressWarnings("unused")
public class Season {
    @SerializedName("air_date")
    private Date airDate;
    @SerializedName("episode_count")
    private Integer episodeCount;
    @SerializedName("id")
    private long id;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("season_number")
    private Integer seasonNumber;
}
