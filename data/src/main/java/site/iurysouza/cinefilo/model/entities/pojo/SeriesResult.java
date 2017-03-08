
package site.iurysouza.cinefilo.model.entities.pojo;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data
public class SeriesResult {
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<Series> seriesList;
    @SerializedName("total_pages")
    private Long totalPages;
    @SerializedName("total_results")
    private Long totalResults;
}
