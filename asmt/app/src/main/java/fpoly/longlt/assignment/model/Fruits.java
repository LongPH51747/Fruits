package fpoly.longlt.assignment.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Fruits implements Serializable {
    @SerializedName("_id")
    private String _id;
    private String namefruit;
    private int price;
    private String img;
    private String description;
    private boolean status;
    private boolean like;
    private String createdAt;
    private String updatedAt;
    int kg;

    public int getKg() {
        return kg;
    }

    public void setKg(int kg) {
        this.kg = kg;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getNamefruit() {
        return namefruit;
    }

    public void setNamefruit(String namefruit) {
        this.namefruit = namefruit;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }


    public Fruits() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Fruits(String _id, String namefruit, int price, String img, String description, boolean status, boolean like, String createdAt, String updatedAt) {
        this._id = _id;
        this.namefruit = namefruit;
        this.price = price;
        this.img = img;
        this.description = description;
        this.status = status;
        this.like = like;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
