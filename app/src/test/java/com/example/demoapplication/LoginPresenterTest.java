package com.example.demoapplication;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.text.Editable;
import android.widget.EditText;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    @Mock
    LoginActivityModel model;

    @Mock
    LoginActivityView view;

    @Mock
    EditText name;

    @Mock
    EditText password;

    @Mock
    Editable editName;

    @Mock
    Editable editPassword;


    @Test
    public void testCheckUser() {
        LoginActivityPresenter presenter = new LoginActivityPresenter(view, model);
        when(name.getText()).thenReturn(editName);
        when(editName.toString()).thenReturn("TestName");
        when(password.getText()).thenReturn(editPassword);
        when(editPassword.toString()).thenReturn("TestPassword");
        String getName = name.getText().toString();
        String getPassword = password.getText().toString();
        presenter.checkUser(getName, getPassword);
        verify(model).checkUserPassword(presenter, getName, getPassword);
    }

    @Test
    public void testLoginUser() {
        LoginActivityPresenter presenter = new LoginActivityPresenter(view, model);
        presenter.loginUser();
        verify(view).toast("Login successful!");
        verify(view).startStudentActivity();
    }

    @Test
    public void testCheckAdmin() {
        LoginActivityPresenter presenter = new LoginActivityPresenter(view, model);
        when(name.getText()).thenReturn(editName);
        when(editName.toString()).thenReturn("TestName");
        when(password.getText()).thenReturn(editPassword);
        when(editPassword.toString()).thenReturn("TestPassword");
        String getName = name.getText().toString();
        String getPassword = password.getText().toString();
        presenter.checkAdmin(getName, getPassword);
        verify(model).checkAdminPassword(presenter, getName, getPassword);
    }

    @Test
    public void testLoginAdmin() {
        LoginActivityPresenter presenter = new LoginActivityPresenter(view, model);
        presenter.loginAdmin();
        verify(view).toast("Admin login successful!");
        verify(view).startAdminActivity();
    }

    @Test
    public void testLoginError() {
        LoginActivityPresenter presenter = new LoginActivityPresenter(view, model);
        presenter.loginError("Test");
        verify(view).toast("Test");
    }

}
