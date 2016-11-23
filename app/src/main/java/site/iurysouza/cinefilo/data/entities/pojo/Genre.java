
package site.iurysouza.cinefilo.data.entities.pojo;

import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;
import lombok.Data;

@Generated("net.hexar.json2pojo")
@Data
@SuppressWarnings("unused")
public class Genre {
    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;
}
