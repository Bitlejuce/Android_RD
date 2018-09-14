
package servio.rd.servio.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceUnions {

    @SerializedName("PlaceUnions")
    @Expose
    private List<PlaceUnion> placeUnions = null;

    public List<PlaceUnion> getPlaceUnions() {
        return placeUnions;
    }

    public void setPlaceUnions(List<PlaceUnion> placeUnions) {
        this.placeUnions = placeUnions;
    }

}
