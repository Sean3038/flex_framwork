package com.example.ffes.flex_framwork.noteview.Login.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.BaseActivity;
import com.example.ffes.flex_framwork.noteview.api.AuthRepository;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;


public class LoginActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener{

    private static final int RC_SIGN_IN = 1;

    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth auth;

    TextInputLayout password;
    TextInputLayout account;

    EditText iaccount;
    EditText ipassword;

    ImageButton google;
    ImageButton guest;
    ImageButton login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_1);

        initUI();
        init();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(LoginActivity.this)
                .enableAutoManage(LoginActivity.this /* FragmentActivity */, LoginActivity.this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();

        auth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void initUI() {
        password = (TextInputLayout) findViewById(R.id.password);
        account = (TextInputLayout) findViewById(R.id.account);
        ipassword = (EditText) findViewById(R.id.ipassword);
        iaccount = (EditText) findViewById(R.id.iaccount);
        google = (ImageButton) findViewById(R.id.google);
        guest = (ImageButton) findViewById(R.id.guest);
        login = (ImageButton) findViewById(R.id.login);
    }

    public void init(){
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        guest.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                account.setError(null);
                password.setError(null);
                if (iaccount.length() == 0) {
                    account.setError("Error in name input");
                } else if(ipassword.length()==0){
                    password.setError("password is null");
                }else{
                    login(iaccount.getText().toString(),ipassword.getText().toString());
                }
            }
        });
    }

    public void notifySuccess(){
        this.setResult(RESULT_OK);
        finish();
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void login(final String account, final String password){
        showProgress("登入中....");
        auth.signInWithEmailAndPassword(account,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        dismissProgress();
                        notifySuccess();

                    }
                })
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()) {
                            try {
                                throw task.getException();
                            }catch (FirebaseAuthInvalidUserException e) {
                                Toast.makeText(LoginActivity.this, e.toString(),
                                        Toast.LENGTH_LONG).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setTitle("帳號尚未注冊")
                                        .setMessage("點擊確認，執行注冊")
                                        .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                register(account, password);
                                            }
                                        })
                                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }).show();
                            }catch (FirebaseAuthInvalidCredentialsException e) {
                                LoginActivity.this.password.setError("the password wrong");
                                Toast.makeText(LoginActivity.this, "密碼錯誤",
                                        Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(LoginActivity.this, e.toString(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dismissProgress();
                        notifyFailure();
                    }
                });
    }

    public void register(final String account, final String password){
        final AuthRepository authRepository=new AuthRepository(FirebaseAuth.getInstance(), FirebaseDatabase.getInstance());
        showProgress("註冊中....");
        auth.createUserWithEmailAndPassword(account,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        authRepository.createUser(authResult.getUser().getUid(),account);
                        login(account,password);
                        dismissProgress();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, "register fail",
                                Toast.LENGTH_SHORT).show();
                        dismissProgress();
                    }
                });

    }

    private void notifyFailure() {
        Toast.makeText(LoginActivity.this, "login fail",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (!mGoogleApiClient.isConnecting() &&
                    !mGoogleApiClient.isConnected()) {
                mGoogleApiClient.connect();
            }

            GoogleSignInResult task = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = task.getSignInAccount();
            firebaseAuthWithGoogle(account);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        final AuthRepository authRepository=new AuthRepository(FirebaseAuth.getInstance(), FirebaseDatabase.getInstance());
        showProgress("登入中....");
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dismissProgress();
                            authRepository.checkGoogleUser();
                            notifySuccess();
                        } else {
                            // If sign in fails, display a message to the user.
                            dismissProgress();
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public static void startAction(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(LoginActivity.this, "Authentication failed.",
                Toast.LENGTH_SHORT).show();
    }
}
