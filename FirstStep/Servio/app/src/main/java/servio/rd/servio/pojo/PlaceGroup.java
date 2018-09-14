
package servio.rd.servio.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceGroup {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("PlaceGroupSchemas")
    @Expose
    private List<PlaceGroupSchema> placeGroupSchemas = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PlaceGroupSchema> getPlaceGroupSchemas() {
        return placeGroupSchemas;
    }

    public void setPlaceGroupSchemas(List<PlaceGroupSchema> placeGroupSchemas) {
        this.placeGroupSchemas = placeGroupSchemas;
    }

}
