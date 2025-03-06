package fpoly.longlt.assignment.model;

import com.google.gson.annotations.SerializedName;

public class User {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SerializedName("_id")
    String id;
    String email;
    String name;
    String password;
    String avatar;
    String phonenumber;

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User(String email, String name, String password, String avatar, String phonenumber, String date, int role, boolean available) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.avatar = avatar;
        this.phonenumber = phonenumber;
        this.date = date;
        this.role = role;
        this.available = available;
    }

    String date;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    String address;
    int role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public User() {
    }

    public User(String email, String name, String password, String avatar, int role, boolean available) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.avatar = avatar;
        this.role = role;
        this.available = available;
    }

    boolean available;
}
