
package servio.rd.servio.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Place {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("Left")
    @Expose
    private Integer left;
    @SerializedName("Top")
    @Expose
    private Integer top;
    @SerializedName("Width")
    @Expose
    private Integer width;
    @SerializedName("Height")
    @Expose
    private Integer height;
    @SerializedName("Corner")
    @Expose
    private Integer corner;
    @SerializedName("ShapeType")
    @Expose
    private Integer shapeType;
    @SerializedName("ShapeOrient")
    @Expose
    private Integer shapeOrient;
    @SerializedName("Color")
    @Expose
    private Integer color;
    @SerializedName("Style")
    @Expose
    private Integer style;
    @SerializedName("FrameColor")
    @Expose
    private Integer frameColor;
    @SerializedName("FontColor")
    @Expose
    private Integer fontColor;
    @SerializedName("Bills")
    @Expose
    private List<Bill> bills = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getLeft() {
        return left;
    }

    public void setLeft(Integer left) {
        this.left = left;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getCorner() {
        return corner;
    }

    public void setCorner(Integer corner) {
        this.corner = corner;
    }

    public Integer getShapeType() {
        return shapeType;
    }

    public void setShapeType(Integer shapeType) {
        this.shapeType = shapeType;
    }

    public Integer getShapeOrient() {
        return shapeOrient;
    }

    public void setShapeOrient(Integer shapeOrient) {
        this.shapeOrient = shapeOrient;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public Integer getStyle() {
        return style;
    }

    public void setStyle(Integer style) {
        this.style = style;
    }

    public Integer getFrameColor() {
        return frameColor;
    }

    public void setFrameColor(Integer frameColor) {
        this.frameColor = frameColor;
    }

    public Integer getFontColor() {
        return fontColor;
    }

    public void setFontColor(Integer fontColor) {
        this.fontColor = fontColor;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

}
