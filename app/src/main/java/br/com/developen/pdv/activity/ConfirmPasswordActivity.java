package br.com.developen.pdv.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import br.com.developen.pdv.R;
import br.com.developen.pdv.room.UserModel;
import br.com.developen.pdv.task.ConfirmPasswordAsyncTask;
import br.com.developen.pdv.utils.Messaging;

public class ConfirmPasswordActivity
        extends AppCompatActivity
        implements ConfirmPasswordAsyncTask.Listener{


    public static final String USER_IDENTIFIER = "USER_IDENTIFIER";

    public static final String USER_NAME = "USER_NAME";

    public static final int RESULT_OK = 1;

    private View progressView, formView;

    private TextView messageTextView, nameTextView;

    private EditText editText1, editText2, editText3, editText4;

    int attemptCount = 0;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_confirm_password);

        progressView = findViewById(R.id.activity_confirm_password_progress);

        formView = findViewById(R.id.activity_confirm_password_form);

        nameTextView = findViewById(R.id.activity_confirm_password_name);

        messageTextView = findViewById(R.id.activity_confirm_password_message);

        editText1 = findViewById(R.id.et1);

        editText2 = findViewById(R.id.et2);

        editText3 = findViewById(R.id.et3);

        editText4 = findViewById(R.id.et4);

        editText1.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            public void afterTextChanged(Editable s) {

                if(s.length()==1) {

                    if (messageTextView.isShown())

                        messageTextView.setVisibility(View.GONE);

                    editText2.requestFocus();

                } else {

                    if(s.length()==0) {

                        editText1.clearFocus();

                    }

                }

            }

        });

        editText2.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            public void afterTextChanged(Editable s) {

                if(s.length()==1){

                    if (messageTextView.isShown())

                        messageTextView.setVisibility(View.GONE);

                    editText3.requestFocus();

                } else {

                    if(s.length()==0) {

                        editText1.requestFocus();

                    }

                }

            }

        });

        editText3.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            public void afterTextChanged(Editable s) {

                if (s.length()==1) {

                    if (messageTextView.isShown())

                        messageTextView.setVisibility(View.GONE);

                    editText4.requestFocus();

                } else {

                    if (s.length() == 0) {

                        editText2.requestFocus();

                    }

                }

            }

        });

        editText4.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            public void afterTextChanged(Editable s) {

                if(s.length()==1) {

                    if (messageTextView.isShown())

                        messageTextView.setVisibility(View.GONE);

                    attemptLogin();

                } else {

                    if (s.length() == 0) {

                        editText3.requestFocus();

                    }

                }

            }

        });

        nameTextView.setText(getIntent().getStringExtra(USER_NAME));

    }


    private void showProgress(final boolean show) {

        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        formView.setVisibility(show ? View.GONE : View.VISIBLE);

        formView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {

            public void onAnimationEnd(Animator animation) {

                formView.setVisibility(show ? View.GONE : View.VISIBLE);

            }

        });

        progressView.setVisibility(show ? View.VISIBLE : View.GONE);

        progressView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {

            public void onAnimationEnd(Animator animation) {

                progressView.setVisibility(show ? View.VISIBLE : View.GONE);

            }

        });

    }


    public void attemptLogin(){

        attemptCount++;

        String password = editText1.getText().toString() +
                editText2.getText().toString() +
                editText3.getText().toString() +
                editText4.getText().toString();

        new ConfirmPasswordAsyncTask<>(this).execute(
                getIntent().getIntExtra(USER_IDENTIFIER, 0),
                password, attemptCount>=3?10000:500);

    }


    public void onConfirmPasswordPreExecute() {

        showProgress(true);

    }


    public void onConfirmPasswordSuccess(UserModel userModel) {

        Intent resultIntent = new Intent();

        setResult(RESULT_OK, resultIntent);

        finish();

    }


    public void onConfirmPasswordFailure(Messaging messaging) {

        editText1.getText().clear();

        editText2.getText().clear();

        editText3.getText().clear();

        editText4.getText().clear();

        if (!messageTextView.isShown()) {

            messageTextView.setVisibility(View.VISIBLE);

            messageTextView.setText(messaging.getMessages()[0]);

        }

        showProgress(false);

        editText1.requestFocus();

    }


}