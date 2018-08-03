package rd.declarationtest.pojo;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NazkGovResult {

    @SerializedName("page")
    @Expose
    private Page page;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}