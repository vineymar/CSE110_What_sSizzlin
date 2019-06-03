package com.example.whatssizzlin;

public class UserPref {
    public boolean peanutAllergy;
    private UserSingleton user;

    public UserPref(UserSingleton user){
        this.user = user;
    }
    public UserPref(){
        this.peanutAllergy = false;
    }
}
