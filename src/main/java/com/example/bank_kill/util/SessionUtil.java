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

    public static User getUserFromSession(HttpSession session){
        if(session.getAttribute("user")==null) return null;
        return (User) session.getAttribute("user");
    }

}
