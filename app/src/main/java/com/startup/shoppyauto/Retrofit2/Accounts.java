package com.startup.shoppyauto.Retrofit2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Accounts {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("device")
    @Expose
    private String device;

    @SerializedName("uid")
    @Expose
    private String uid;

    @SerializedName("fbid")
    @Expose
    private String fbid;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("twofa")
    @Expose
    private String twofa;

    @SerializedName("fullname")
    @Expose
    private String fullname;

    @SerializedName("groups")
    @Expose
    private Object groups;

    @SerializedName("pages")
    @Expose
    private Object pages;

    @SerializedName("friends")
    @Expose
    private Object friends;

    @SerializedName("birthday")
    @Expose
    private String birthday;

    @SerializedName("checkbackup")
    @Expose
    private String checkbackup;

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("logout")
    @Expose
    private int logout;

    @SerializedName("update_time")
    @Expose
    private String update_time;

    @SerializedName("slave")
    @Expose
    private String slave;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFbid() {
        return fbid;
    }

    public void setFbid(String fbid) {
        this.fbid = fbid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTwofa() {
        return twofa;
    }

    public void setTwofa(String twofa) {
        this.twofa = twofa;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Object getGroups() {
        return groups;
    }

    public void setGroups(Object groups) {
        this.groups = groups;
    }

    public Object getPages() {
        return pages;
    }

    public void setPages(Object pages) {
        this.pages = pages;
    }

    public Object getFriends() {
        return friends;
    }

    public void setFriends(Object friends) {
        this.friends = friends;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCheckbackup() {
        return checkbackup;
    }

    public void setCheckbackup(String checkbackup) {
        this.checkbackup = checkbackup;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLogout() {
        return logout;
    }

    public void setLogout(int logout) {
        this.logout = logout;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getSlave() {
        return slave;
    }

    public void setSlave(String slave) {
        this.slave = slave;
    }
}
