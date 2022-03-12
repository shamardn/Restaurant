package com.shamardn.android.restaurant.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.shamardn.android.restaurant.R;
import com.shamardn.android.restaurant.data.User;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneNo extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout et_verify;
    MaterialButton btn_verify;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    String verificationCodeBySystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verify_phone_no);

        init();

        btn_verify.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();

        String phoneNo = getUserData().getPhone();

        sendVerificationCodeToUser(phoneNo);
    }

    private User getUserData() {
        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("userData");
        return user;
    }

    private void sendVerificationCodeToUser(String phoneNo) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+2" + phoneNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);

                    verificationCodeBySystem = s;
                }

                @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                    String codeByUser = credential.getSmsCode();
                    if (codeByUser != null){
                        progressBar.setVisibility(View.VISIBLE);
                        verifyCode(codeByUser);
                    }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerifyPhoneNo.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void verifyCode(String codeByUser) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem,codeByUser);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(VerifyPhoneNo.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(getApplicationContext(),UserProfile.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("userData",getUserData());
                    startActivity(intent);

                }else{
                    Toast.makeText(VerifyPhoneNo.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {
        et_verify = findViewById(R.id.et_verify);
        btn_verify = findViewById(R.id.btn_verify);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {

        String codeByUser = et_verify.getEditText().getText().toString().trim();
        if (codeByUser.isEmpty() || codeByUser.length() < 6 ){
            et_verify.setError("Wrong OTP..");
            et_verify.requestFocus();
            return;
        }
        et_verify.setError(null);
        et_verify.setErrorEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        verifyCode(codeByUser);
    }
}