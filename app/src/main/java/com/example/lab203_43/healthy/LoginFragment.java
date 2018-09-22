package com.example.lab203_43.healthy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by LAB203_43 on 20/8/2561.
 */

public class LoginFragment extends Fragment {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initLoginBtn();
        initRegisterBtn();
        initCurrentUser();
    }

    // Action after click login button
    private void initLoginBtn() {
        Button _loginBtn = (Button) getView().findViewById(R.id.login_login_btn);
        _loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText _userId = (EditText) getView().findViewById(R.id.login_user_id);
                EditText _password = (EditText) getView().findViewById(R.id.login_password);
                String _userIdStr = _userId.getText().toString();
                final String _passwordStr = _password.getText().toString();
                if (_userIdStr.isEmpty() || _passwordStr.isEmpty()) {
                    Toast.makeText(
                            getActivity(),
                            "กรุณาระบุ user or password",
                            Toast.LENGTH_SHORT
                    ).show();
                    Log.d("USER", "USER OR PASSWORD IS EMPTY");
                }  else {
                    mAuth.signInWithEmailAndPassword(_userIdStr, _passwordStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            if(mAuth.getCurrentUser().isEmailVerified()) {
                                getActivity()
                                        .getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.main_view, new MenuFragment())
                                        .commit();
                            }
                            else {
                                Toast.makeText(getActivity(),"Email ของคุณยังไม่ได้รับการยืนยัน", Toast.LENGTH_SHORT).show();
                                Log.d("USER", "EMAIL STILL NOT VERIFY");
                            }
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(),"User นี้ยังไม่มีในระบบ", Toast.LENGTH_SHORT).show();
                            Log.d("USER", "THIS EMAIL HAS INVALID");
                        }
                    });
                    Log.d("USER", "INVALID USERNAME OR PASSWORD");
                }
            }
        });
    }

    //Action when click register button
    private void initRegisterBtn() {
        TextView _registerBtn = (TextView) getView().findViewById(R.id.login_register_btn);
        _registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        Log.d("USER", "GOTO REGISTER");
    }
    private void initCurrentUser() {
        if(mAuth.getCurrentUser() != null )
        {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new MenuFragment()).commit();
            Log.d("USER", "HAVE A USER CURRENT");
        }
        else
        {
        }
    }
}
