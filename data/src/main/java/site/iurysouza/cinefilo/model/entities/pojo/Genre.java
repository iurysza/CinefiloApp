
package site.iurysouza.cinefilo.model.entities.pojo;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Genre {
    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;
}
