
package servio.rd.servio.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bill {

    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("Number")
    @Expose
    private Integer number;
    @SerializedName("Opened")
    @Expose
    private String opened;
    @SerializedName("Total")
    @Expose
    private Integer total;
    @SerializedName("OpenUser")
    @Expose
    private String openUser;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getOpened() {
        return opened;
    }

    public void setOpened(String opened) {
        this.opened = opened;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getOpenUser() {
        return openUser;
    }

    public void setOpenUser(String openUser) {
        this.openUser = openUser;
    }

}
