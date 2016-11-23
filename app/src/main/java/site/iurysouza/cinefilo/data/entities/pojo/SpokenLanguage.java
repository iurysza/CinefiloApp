
package site.iurysouza.cinefilo.data.entities.pojo;

import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;
import lombok.Data;

@Generated("net.hexar.json2pojo")
@Data
public class SpokenLanguage {
    @SerializedName("iso_639_1")
    private String iso6391;
    @SerializedName("name")
    private String name;
}
