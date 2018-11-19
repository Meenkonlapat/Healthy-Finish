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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by LAB203_43 on 20/8/2561.
 */

public class RegisterFragment extends Fragment {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRegisterBtn();
    }

    // Action after click login button
    private void initRegisterBtn() {
        Button _registerBtn = (Button) getView().findViewById(R.id.register_register_btn);
        _registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText _email = (EditText) getView().findViewById(R.id.register_mail);
                EditText _password = (EditText) getView().findViewById(R.id.register_password);
                EditText _rePassword = (EditText) getView().findViewById(R.id.register_re_password);

                String _emailString = _email.getText().toString();
                String _passwordString = _password.getText().toString();
                String _rePasswordString = _rePassword.getText().toString();

                if (_emailString.isEmpty() || _passwordString.isEmpty() || _rePasswordString.isEmpty())
                {
                    Toast.makeText(getContext(), "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
                    Log.d("register", "some field is empty");
                }
                else if (_passwordString.length() < 6)
                {
                    Toast.makeText(getContext(), "password ต้องยาวอย่างน้อย 6 ตัวอักษร", Toast.LENGTH_SHORT).show();
                    Log.d("register", "password length too short");
                }
                else if (!_passwordString.equals(_rePasswordString))
                {
                    Toast.makeText(getContext(), "confirm password ผิด", Toast.LENGTH_SHORT).show();
                    Log.d("register", "confirm password not equal to password");
                }
                else if (_emailString.equals("admin"))
                {
                    Toast.makeText(getContext(), "user นี้มีอยู่ในระบบแล้ว", Toast.LENGTH_SHORT).show();
                    Log.d("register", "username already existed");
                }
                else {
                    mAuth.createUserWithEmailAndPassword(_emailString, _passwordString).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            sendVerfifiedEmail(authResult.getUser());
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.main_view, new LoginFragment())
                                    .commit();
                            mAuth.signOut();
                            Log.d("register", "register success");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("register", "register failed.Error :" + e.getMessage());
                        }
                    });
                }
            }
        });
    }
    void sendVerfifiedEmail(FirebaseUser _user){
        Log.d("REGISTER", _user.getUid());
        _user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new LoginFragment()).addToBackStack(null).commit();
                Toast.makeText(getActivity(), "Success" , Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            Log.d("ERROR", e.getMessage());
            }
        });
    }
}
