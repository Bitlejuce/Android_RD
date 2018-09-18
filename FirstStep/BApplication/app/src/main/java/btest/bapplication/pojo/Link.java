package btest.bapplication.pojo;

public class Link {
    public static final int STATUS_LOADED = 1;
    public static final int STATUS_ERROR = 2;
    public static final int STATUS_UNKNOWN = 3;

    private String link;
    private long dateMills;
    private int status;

    public Link(String link, long dateMills, int status) {
        this.link = link;
        this.dateMills = dateMills;
        this.status = status;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public long getDateMills() {
        return dateMills;
    }

    public void setDateMills(long dateMills) {
        this.dateMills = dateMills;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
