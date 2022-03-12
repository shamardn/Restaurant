package com.shamardn.android.restaurant.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shamardn.android.restaurant.R;
import com.shamardn.android.restaurant.data.User;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 333;
    ImageView login_img;
    TextView login_txt1;
    TextInputLayout et_login_username, et_login_password;
    MaterialButton login_forget, btn_login_signIn, btn_login_signUp;

    SignInButton sign_in_button_google;
    private GoogleSignInClient mGoogleSignInClient;

    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
           // updateUI(currentUser);
            Intent intent = new Intent(getApplicationContext(),UserProfile.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        init();

        mAuth = FirebaseAuth.getInstance();

        createRequest();

        btn_login_signUp.setOnClickListener(this) ;
        btn_login_signIn.setOnClickListener(this) ;
        sign_in_button_google.setOnClickListener(this);
    }

    private void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void init() {
        login_img = findViewById(R.id.login_img);
        login_txt1 = findViewById(R.id.login_txt1);
        et_login_username = findViewById(R.id.et_login_username);
        et_login_password = findViewById(R.id.et_login_password);
        login_forget = findViewById(R.id.login_forget);
        btn_login_signIn = findViewById(R.id.btn_login_signIn);
        btn_login_signUp = findViewById(R.id.btn_login_signUp);
        sign_in_button_google = findViewById(R.id.sign_in_button_google);
    }

    private boolean validateUsername(){
        String val = et_login_username.getEditText().getText().toString();
        if (val.isEmpty()){
            et_login_username.setError("Field cannot be empty");
            return false;
        }else{
            et_login_username.setError(null);
            et_login_username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword(){
        String val = et_login_password.getEditText().getText().toString();
        if (val.isEmpty()){
            et_login_password.setError("Field cannot be empty");
            return false;
        }else{
            et_login_password.setError(null);
            et_login_password.setErrorEnabled(false);
            return true;
        }
    }

    private void loginUsername(){
        if (!validateUsername() | !validatePassword()){
            return;
        }else{
            isUser();
        }
    }

    private void isUser() {
        String usernameEntered = et_login_username.getEditText().getText().toString().trim();
        String passwordEntered = et_login_password.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        Query checkUser = reference.orderByChild("userName").equalTo(usernameEntered);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    et_login_username.setError(null);
                    et_login_username.setErrorEnabled(false);
                    String passwordFromDB = snapshot.child(usernameEntered).child("password").getValue(String.class);
                    if (passwordFromDB.equals(passwordEntered)){
                        et_login_password.setError(null);
                        et_login_password.setErrorEnabled(false);

                        String usernameFromDB = snapshot.child(usernameEntered).child("userName").getValue(String.class);
                        String fullnameFromDB = snapshot.child(usernameEntered).child("fullName").getValue(String.class);
                        String emailFromDB = snapshot.child(usernameEntered).child("email").getValue(String.class);
                        String phoneFromDB = snapshot.child(usernameEntered).child("phone").getValue(String.class);

                        User user = new User(fullnameFromDB, usernameFromDB, emailFromDB, phoneFromDB, passwordFromDB);
                        Intent intent = new Intent(getApplicationContext(),UserProfile.class);
                        intent.putExtra("userData",user);

                        startActivity(intent);
                        finish();

                    }else{
                        et_login_password.setError("wrong Password");
                        et_login_password.requestFocus();
                    }
                }else{
                    et_login_username.setError("User Name is not exist");
                    et_login_username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login_signUp:
                signUpTransition();
                break;

            case R.id.btn_login_signIn:
                loginUsername();
                break;

            case R.id.sign_in_button_google:
                sign_in();
                break;
        }
    }

    private void sign_in() {
        mGoogleSignInClient.signOut();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        //noinspection deprecation
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
               }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(),UserProfile.class);
                            startActivity(intent);
//                            updateUI(user);
                        } else {
                            Toast.makeText(Login.this, "Sign in operation is faild", Toast.LENGTH_SHORT).show();
                           //   updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null){
            Intent intent = new Intent(getApplicationContext(),UserProfile.class);
            startActivity(intent);
        }


    }


    private void signUpTransition() {
        Pair[] pairs = new Pair[7];
        pairs[0] = new Pair<View,String>(login_img,"trans_img");
        pairs[1] = new Pair<View,String>(login_txt1,"trans_logo");
        pairs[2] = new Pair<View,String>(et_login_username,"trans_username");
        pairs[3] = new Pair<View,String>(et_login_password,"trans_password");
        pairs[4] = new Pair<View,String>(login_forget,"trans_phone");
        pairs[5] = new Pair<View,String>(btn_login_signIn,"trans_go");
        pairs[6] = new Pair<View,String>(btn_login_signUp,"trans_return");

        Intent intent = new Intent(Login.this, SignUp.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);
        startActivity(intent,options.toBundle());
    }
}