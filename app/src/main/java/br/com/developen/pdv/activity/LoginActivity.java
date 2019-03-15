package br.com.developen.pdv.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.developen.pdv.R;
import br.com.developen.pdv.room.UserModel;
import br.com.developen.pdv.task.AuthenticateAsyncTask;
import br.com.developen.pdv.utils.Constants;
import br.com.developen.pdv.utils.Messaging;

public class LoginActivity extends AppCompatActivity
        implements AuthenticateAsyncTask.Listener{


    private ProgressDialog progressDialog;

    private EditText loginEditText;

    private EditText passwordEditText;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        loginEditText = findViewById(R.id.activity_login_login_edittext);

        passwordEditText = findViewById(R.id.activity_login_password_edittext);

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {

                boolean handled = false;

                if (id == EditorInfo.IME_ACTION_GO) {

                    InputMethodManager imm = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                    assert imm != null;

                    imm.hideSoftInputFromWindow(textView.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

                    validateFieldsAndAttemptLogin();

                    handled = true;

                }

                return handled;

            }

        });

        Button signInButton = findViewById(R.id.activity_login_sign_in_button);

        signInButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {

                validateFieldsAndAttemptLogin();

            }

        });

    }


    private void validateFieldsAndAttemptLogin() {

        loginEditText.setError(null);

        passwordEditText.setError(null);

        String login = loginEditText.getText().toString();

        String password = passwordEditText.getText().toString();

        boolean cancel = false;

        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {

            passwordEditText.setError(getString(R.string.error_invalid_password));

            focusView = passwordEditText;

            cancel = true;

        }

        if (TextUtils.isEmpty(login)) {

            loginEditText.setError(getString(R.string.error_field_required));

            focusView = loginEditText;

            cancel = true;

        } else if (!isEmailValid(login)) {

            loginEditText.setError(getString(R.string.error_invalid_email));

            focusView = loginEditText;

            cancel = true;

        }

        if (cancel) {

            focusView.requestFocus();

        } else {

            new AuthenticateAsyncTask<>(this).
                    execute(login, password);

        }

    }


    public void onAuthenticatePreExecute() {

        if (!getProgressDialog().isShowing())

            getProgressDialog().show();

    }


    public void onAuthenticateSuccess(UserModel userModel) {

        SharedPreferences preferences = getSharedPreferences(
                Constants.SHARED_PREFERENCES_NAME, 0);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(Constants.USER_IDENTIFIER_PROPERTY, userModel.getIdentifier());

        editor.putString(Constants.USER_NAME_PROPERTY, userModel.getName());

        editor.putString(Constants.USER_LOGIN_PROPERTY, userModel.getLogin());

        editor.putString(Constants.USER_NUMERIC_PASSWORD_PROPERTY, userModel.getNumericPassword());

        editor.putString(Constants.USER_LEVEL_PROPERTY, userModel.getLevel().toString());

        editor.apply();

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

        startActivity(intent);

        finish();

    }


    public void onAuthenticateFailure(Messaging messaging) {

        if (getProgressDialog().isShowing())

            getProgressDialog().dismiss();

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.AppCompatAlertDialogStyle);

        builder.setMessage(TextUtils.join("\n", messaging.getMessages()));

        builder.setTitle(R.string.dlg_title_login_failure);

        builder.setPositiveButton(R.string.try_again,

                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                    }

                });

        AlertDialog alert = builder.create();

        alert.show();

    }


    private boolean isEmailValid(String email) {

        return email.contains("@");

    }


    private boolean isPasswordValid(String password) {

        return password.length() > 4;

    }


    public ProgressDialog getProgressDialog() {

        if (progressDialog==null){

            progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark);

            progressDialog.setIndeterminate(true);

            progressDialog.setMessage("Validando credenciais...");

        }

        return progressDialog;

    }


}