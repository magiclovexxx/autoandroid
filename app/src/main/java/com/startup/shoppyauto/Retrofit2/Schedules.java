package com.startup.shoppyauto.Retrofit2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Schedules {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("uid")
    @Expose
    private String uid;

    @SerializedName("account_id")
    @Expose
    private String accountId;

    @SerializedName("account_name")
    @Expose
    private Object accountName;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("group_id")
    @Expose
    private Object groupId;
    @SerializedName("group_type")
    @Expose
    private String groupType;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("privacy")
    @Expose
    private Object privacy;
    @SerializedName("name")
    @Expose
    private Object name;
    @SerializedName("message")
    @Expose
    private Object message;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("url")
    @Expose
    private Object url;
    @SerializedName("image")
    @Expose
    private Object image;
    @SerializedName("caption")
    @Expose
    private Object caption;
    @SerializedName("count_number")
    @Expose
    private String countNumber;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("time_post")
    @Expose
    private String timePost;
    @SerializedName("time_post_show")
    @Expose
    private Object timePostShow;
    @SerializedName("delete_post")
    @Expose
    private String deletePost;
    @SerializedName("deplay")
    @Expose
    private String deplay;
    @SerializedName("repeat_post")
    @Expose
    private String repeatPost;
    @SerializedName("repeat_time")
    @Expose
    private Object repeatTime;
    @SerializedName("repeat_end")
    @Expose
    private Object repeatEnd;
    @SerializedName("result")
    @Expose
    private Object result;
    @SerializedName("message_error")
    @Expose
    private Object messageError;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("changed")
    @Expose
    private String changed;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("slave")
    @Expose
    private Object slave;
    @SerializedName("version_name")
    @Expose
    private String version_name;


    public int getId() {
        return Integer.parseInt(id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Object getAccountName() {
        return accountName;
    }

    public void setAccountName(Object accountName) {
        this.accountName = accountName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Object getGroupId() {
        return groupId;
    }

    public void setGroupId(Object groupId) {
        this.groupId = groupId;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Object privacy) {
        this.privacy = privacy;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public Object getCaption() {
        return caption;
    }

    public void setCaption(Object caption) {
        this.caption = caption;
    }

    public String getCountNumber() {
        return countNumber;
    }

    public void setCountNumber(String countNumber) {
        this.countNumber = countNumber;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTimePost() {
        return timePost;
    }

    public void setTimePost(String timePost) {
        this.timePost = timePost;
    }

    public Object getTimePostShow() {
        return timePostShow;
    }

    public void setTimePostShow(Object timePostShow) {
        this.timePostShow = timePostShow;
    }

    public String getDeletePost() {
        return deletePost;
    }

    public void setDeletePost(String deletePost) {
        this.deletePost = deletePost;
    }

    public String getDeplay() {
        return deplay;
    }

    public void setDeplay(String deplay) {
        this.deplay = deplay;
    }

    public String getRepeatPost() {
        return repeatPost;
    }

    public void setRepeatPost(String repeatPost) {
        this.repeatPost = repeatPost;
    }

    public Object getRepeatTime() {
        return repeatTime;
    }

    public void setRepeatTime(Object repeatTime) {
        this.repeatTime = repeatTime;
    }

    public Object getRepeatEnd() {
        return repeatEnd;
    }

    public void setRepeatEnd(Object repeatEnd) {
        this.repeatEnd = repeatEnd;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getMessageError() {
        return messageError;
    }

    public void setMessageError(Object messageError) {
        this.messageError = messageError;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChanged() {
        return changed;
    }

    public void setChanged(String changed) {
        this.changed = changed;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Object getSlave() {
        return slave;
    }

    public void setSlave(Object slave) {
        this.slave = slave;
    }

    public String getVersionName() { return version_name; }

    public void setVersionName(String version_name) { this.version_name = version_name; }
}
