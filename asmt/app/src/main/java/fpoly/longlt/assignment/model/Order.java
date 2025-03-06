package fpoly.longlt.assignment.model;

import static fpoly.longlt.assignment.screen.LoginScreen.id;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order {
    @SerializedName("_id")
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    String nameuser;

    public String getNameuser() {
        return nameuser;
    }

    public void setNameuser(String nameuser) {
        this.nameuser = nameuser;
    }

    String userId;
    List<Item> items;
    double totalPrice;
    int quantity;
    String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    String namefruit;
    String createdAt, updatedAt;

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

    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Order() {
    }

    public String getNamefruit() {
        return namefruit;
    }

    public void setNamefruit(String namefruit) {
        this.namefruit = namefruit;
    }

    public Order(String userId, List<Item> items, double totalPrice, String namefruit, String createdAt, String updatedAt, String status) {
        this.userId = userId;
        this.items = items;
        this.totalPrice = totalPrice;
        this.namefruit = namefruit;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }
    @Override
    public String toString() {
        return "Order{" +
                "id='" + userId + '\'' +
                ", items=" + items +
                '}';
    }
}
