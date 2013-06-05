package com.astrider.sfc.model.vo;

import java.sql.Date;

import com.astrider.sfc.lib.helper.annotation.Column;
import com.astrider.sfc.lib.helper.annotation.Valid;
import com.astrider.sfc.lib.model.vo.BaseVo;

public class UserVo extends BaseVo {
    private static final long serialVersionUID = -2456490578422461383L;

    @Column("user_id")
    @Valid(isNotNull=true, isNotBlank=true, isMaxLength=true, maxLength=8)
    private int userId;
    @Column("user_name")
    @Valid(isNotNull=true, isNotBlank=true, isMaxLength=true, maxLength=64)
    private String userName;
    @Column("email")
    @Valid(isNotNull=true, isNotBlank=true, isMaxLength=true, maxLength=128)
    private String email;
    @Column("auth_token")
    @Valid(isNotNull=true, isNotBlank=true, isMaxLength=true, maxLength=150)
    private String authToken;
    @Column("email_token")
    @Valid(isNotNull=true, isNotBlank=true, isMaxLength=true, maxLength=16)
    private String emailToken;
    @Column("male")
    private boolean male;
    @Column("height")
    @Valid(isMin=true, isMaxLength=true, min=0, maxLength=3)
    private int height;
    @Column("weight")
    @Valid(isMin=true, isMaxLength=true, min=0, maxLength=3)
    private int weight;
    @Column("created_at")
    private Date createdAt;
    @Column("deleted_at")
    private Date deletedAt;
    @Column("is_deleted")
    private boolean isDeleted;
    @Column("is_confirmed")
    private boolean isConfirmed;
    @Column("is_available")
    private boolean isAvailable;

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getAuthToken() {
        return authToken;
    }
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
    public String getEmailToken() {
        return emailToken;
    }
    public void setEmailToken(String emailToken) {
        this.emailToken = emailToken;
    }
    public boolean isMale() {
        return male;
    }
    public void setMale(boolean male) {
        this.male = male;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public Date getDeletedAt() {
        return deletedAt;
    }
    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
    public boolean isDeleted() {
        return isDeleted;
    }
    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    public boolean isConfirmed() {
        return isConfirmed;
    }
    public void setConfirmed(boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }
    public boolean isAvailable() {
        return isAvailable;
    }
    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
