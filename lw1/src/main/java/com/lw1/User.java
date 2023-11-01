package com.lw1;

public class User {
    String username;
    String password;
    boolean isBlock;
    boolean isPswd;
    public User(String username){
        this.isBlock = false;
        this.isPswd = false;
        this.username = username;
        this.password = "";
    }
    public User(){}
    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.isBlock = false;
        this.isPswd = false;
    }
    public User(String username, String password, boolean block, boolean pswd){
        this.username = username;
        this.password = password;
        this.isBlock = block;
        this.isPswd = pswd;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsBlock() {
        return isBlock;
    }

    public boolean getIsPswd() {
        return isPswd;
    }

    public void setIsBlock(boolean isBlock) {
        this.isBlock = isBlock;
    }
    public void setIsPswd(boolean isPswd) {
        this.isPswd = isPswd;
    }
}
