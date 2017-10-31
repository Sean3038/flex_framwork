package com.example.ffes.flex_framwork.noteview.Login;
/**
 * Created by user on 2017/10/26.
 */

public interface Login {
    interface Presenter{
        void loginGoogle();
        void login(String account, String password);
    }
}
