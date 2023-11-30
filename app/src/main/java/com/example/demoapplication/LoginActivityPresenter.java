package com.example.demoapplication;

public class LoginActivityPresenter {

    LoginActivityModel model;
    LoginActivityView view;

    public LoginActivityPresenter(LoginActivityView view, LoginActivityModel model) {
        this.model = model;
        this.view = view;
    }

    public void checkUser(String name, String password) {
        model.checkUserPassword(this, name, password);
    }

    public void loginUser() {
        view.toast("Login successful!");
        view.startStudentActivity();
    }

    public void checkAdmin(String name, String password) {
        model.checkAdminPassword(this, name, password);
    }

    public void loginAdmin() {
        view.toast("Admin login successful!");
        view.startAdminActivity();
    }

    public void loginError(String error) {
        view.toast(error);
    }
}
