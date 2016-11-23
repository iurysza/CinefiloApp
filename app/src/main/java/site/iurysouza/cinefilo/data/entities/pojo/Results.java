
package site.iurysouza.cinefilo.data.entities.pojo;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import javax.annotation.Generated;
import lombok.Data;

@Generated("net.hexar.json2pojo")
@Data
@SuppressWarnings("unused")
public class Results {
    @SerializedName("page")
    private Long page;
    @SerializedName("results")
    private List<Movie> movieList;
    @SerializedName("total_pages")
    private Long totalPages;
    @SerializedName("total_results")
    private Long totalResults;
}
