
package servio.rd.servio.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceGroupSchema {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Places")
    @Expose
    private List<Place> places = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

}
