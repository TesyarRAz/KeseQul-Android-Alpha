package com.kesequl.app;

import com.kesequl.app.model.Entity.User;

public class Global {
    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Global.user = user;
    }
}
