package k8Edu.model;

import java.sql.Timestamp; // Sử dụng Timestamp cho kiểu DATETIME trong SQL Server

public class PasswordResetTokenModel {
    private int id;
    private int userId;
    private String token;
    private Timestamp expiryDate;

    public PasswordResetTokenModel() {
    }

    public PasswordResetTokenModel(int userId, String token, Timestamp expiryDate) {
        this.userId = userId;
        this.token = token;
        this.expiryDate = expiryDate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Timestamp expiryDate) {
        this.expiryDate = expiryDate;
    }
}