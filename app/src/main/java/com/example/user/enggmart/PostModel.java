package com.example.user.enggmart;

/**
 * Created by UJJAWAL-KUMAR on 9/20/2018.
 */

public class PostModel {
    private String postUserdpurl = "";
    private String postUserName = "";
    private String postUserEmail = "";
    private String postUrl = "";
    private String postTime = "";
    private String postDescription = "";
    private String postLikesCount = "0";
    private String postCommentsCount = "0";
    private boolean isPostLiked = false;
    private String uid = "";

    public String getPostUserdpurl() {
        return postUserdpurl;
    }

    public void setPostUserdpurl(String postUserdpurl) {
        this.postUserdpurl = postUserdpurl;
    }

    public String getPostUserName() {
        return postUserName;
    }

    public void setPostUserName(String postUserName) {
        this.postUserName = postUserName;
    }

    public String getPostUserEmail() {
        return postUserEmail;
    }

    public void setPostUserEmail(String postUserEmail) {
        this.postUserEmail = postUserEmail;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public String getPostLikesCount() {
        return postLikesCount;
    }

    public void setPostLikesCount(String postLikesCount) {
        this.postLikesCount = postLikesCount;
    }

    public String getPostCommentsCount() {
        return postCommentsCount;
    }

    public void setPostCommentsCount(String postCommentsCount) {
        this.postCommentsCount = postCommentsCount;
    }

    public boolean isPostLiked() {
        return isPostLiked;
    }

    public void setPostLiked(boolean postLiked) {
        isPostLiked = postLiked;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }
}
