package com.example.demoapplication;


import static org.mockito.Mockito.verify;

import android.widget.EditText;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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

    @Test
    public void testLoginError() {
        LoginActivityPresenter presenter = new LoginActivityPresenter(view, model);
        view.setCurrentUser("user1");
        presenter.loginError("1");
        verify(view).toast("1");
    }

    @Test
    public void testCheckUser() {
        LoginActivityPresenter presenter = new LoginActivityPresenter(view, model);
        String getName = name.getText().toString();
        String getPassword = password.getText().toString();
        presenter.checkUser(getName, getPassword);
        verify(model).checkUserPassword(presenter, getName, getPassword);
    }
}
