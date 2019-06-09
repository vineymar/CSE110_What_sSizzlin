package com.example.whatssizzlin;

class UserSingleton {
    private static UserSingleton ourInstance = new UserSingleton();

    static synchronized UserSingleton getInstance() {
        if(ourInstance == null){
            ourInstance = new UserSingleton();
        }

        return ourInstance;
    }


    private Integer id;
    private UserInv inv;
    private UserPref prefs;


    private UserSingleton() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserInv getInv() {
        return inv;
    }

    public void setInv(UserInv inv) {
        this.inv = inv;
    }

    public UserPref getPrefs() {
        return prefs;
    }

    public void setPrefs(UserPref prefs) {
        this.prefs = prefs;
    }

    public static void deleteCurrentUser(){
        ourInstance = null;
    }

    //redoes the inventory, preferences, etc
    // use when updating id so we can populate stuff like inventory with whats in the database
    private void updateProperties(){
        inv = new UserInv(this);
        prefs = new UserPref(this);
    }

}
