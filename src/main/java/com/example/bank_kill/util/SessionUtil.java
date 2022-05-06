package com.example.bank_kill.util;

import com.example.bank_kill.model.User;

import javax.servlet.http.HttpSession;

public class SessionUtil {

    public static void saveUserToSession(HttpSession session, User user){
        session.setAttribute("user",user);
    }

    public static void removeUserFromSession(HttpSession session){
        session.removeAttribute("user");
    }

}
