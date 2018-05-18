package com.shoppinglist.rdproject.shoppinglist;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Objects;

public class SharedList implements Serializable {
    private String sharedListName;
    private String fromUserName;
    private String fromUserEmail;
    private String fromUserId;

    public SharedList() {
    }

    public SharedList(String sharedListName, String fromUserName, String fromUserEmail, String fromUserId) {
        this.sharedListName = sharedListName;
        this.fromUserName = fromUserName;
        this.fromUserEmail = fromUserEmail;
        this.fromUserId = fromUserId;
    }

    public String getSharedListName() {
        return sharedListName;
    }

    public void setSharedListName(String sharedListName) {
        this.sharedListName = sharedListName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getFromUserEmail() {
        return fromUserEmail;
    }

    public void setFromUserEmail(String fromUserEmail) {
        this.fromUserEmail = fromUserEmail;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SharedList that = (SharedList) o;
        return Objects.equals(sharedListName, that.sharedListName) &&
                Objects.equals(fromUserName, that.fromUserName) &&
                Objects.equals(fromUserEmail, that.fromUserEmail) &&
                Objects.equals(fromUserId, that.fromUserId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(sharedListName, fromUserName, fromUserEmail, fromUserId);
    }

}
