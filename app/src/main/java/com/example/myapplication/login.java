package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

class LoginActivity extends AppCompatActivity {
    private EditText edtUsername,edtPassword;
    private Button buttonL,buttonS;
    private ProgressBar loading;
    private LinearLayout mLoginLayout;

    public static final String TAG = "SignInActivity";

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        edtUsername = findViewById(R.id.loginE);
        edtPassword = findViewById(R.id.loginP);
        buttonL = findViewById(R.id.buttonL);
        buttonS = findViewById(R.id.buttonS);

        loading = findViewById(R.id.progressBar);
        mLoginLayout = findViewById(R.id.loginForm);

        mAuth = FirebaseAuth.getInstance();

        buttonL.setOnClickListener(view -> {
            if(TextUtils.isEmpty(edtUsername.getText().toString())) {
                edtUsername.setError("Enter username");
                return;
            }
            if (TextUtils.isEmpty(edtPassword.getText().toString())) {
                edtPassword.setError("Enter password");
                return;
            }

            showProgressBar(true);
            signIn(edtUsername.getText().toString(), edtPassword.getText().toString());

            View view1 = getCurrentFocus();
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromInputMethod(view1 != null ? ((View) view1).getWindowToken() : null, 0);
        });

        buttonS.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this,regester.class)));

    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        showProgressBar(false);
                        startMainActivity();

                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, " username  is wrong!",
                                Toast.LENGTH_SHORT).show();
                        showProgressBar(false);
                    }
                });
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


    private void showProgressBar(boolean state) {

        if (state) {
            loading.setVisibility(View.VISIBLE);
            mLoginLayout.setVisibility(View.GONE);
        } else {
            loading.setVisibility(View.GONE);
            mLoginLayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
