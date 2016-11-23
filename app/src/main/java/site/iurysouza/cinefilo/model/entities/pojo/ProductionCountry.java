
package site.iurysouza.cinefilo.model.entities.pojo;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ProductionCountry {
    @SerializedName("iso_3166_1")
    private String iso31661;
    @SerializedName("name")
    private String name;
}
