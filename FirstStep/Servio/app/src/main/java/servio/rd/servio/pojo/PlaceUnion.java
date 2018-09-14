
package servio.rd.servio.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceUnion {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("PlaceGroups")
    @Expose
    private List<PlaceGroup> placeGroups = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PlaceGroup> getPlaceGroups() {
        return placeGroups;
    }

    public void setPlaceGroups(List<PlaceGroup> placeGroups) {
        this.placeGroups = placeGroups;
    }

}
