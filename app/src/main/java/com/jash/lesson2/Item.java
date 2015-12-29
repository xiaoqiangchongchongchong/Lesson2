package com.jash.lesson2;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jash
 * Date: 15-12-29
 * Time: 上午10:13
 */
public class Item {
    private long userId;
    private String userIcon;
    private String userName;
    private String content;
    private String image;

    public Item(JSONObject object) throws JSONException {
        if (!object.isNull("user")) {
            userIcon = object.getJSONObject("user").getString("icon");
            userName = object.getJSONObject("user").getString("login");
            userId = object.getJSONObject("user").getLong("id");
        }
        content = object.getString("content");
        if (!object.isNull("image")) {
            image = object.getString("image");
        }
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
