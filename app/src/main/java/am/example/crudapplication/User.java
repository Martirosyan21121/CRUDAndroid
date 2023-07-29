package am.example.crudapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "user")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String surname;
    private String email;
    private String phoneNumber;

//    private String picUrl;

//, String picUrl
    public User(int id, String name, String surname, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
//        this.picUrl = picUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

//    public String getPicUrl() {
//        return picUrl;
//    }
//
//    public void setPicUrl(String picUrl) {
//        this.picUrl = picUrl;
//    }
}
