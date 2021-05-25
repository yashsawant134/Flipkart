package com.example.flipkart.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.flipkart.MainActivity1;
import com.example.flipkart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;


public class EnterOTPFragment extends Fragment {

    PinView otp;
AppCompatButton verify;
TextView phoneno,resendotp;
ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_enter_o_t_p, container, false);
        String otpbackend=getArguments().getString("OTP");

        progressBar=view.findViewById(R.id.otpprogress);

        phoneno=view.findViewById(R.id.phoneno);
        phoneno.setText("+91 "+getArguments().getString("phoneno"));


        resendotp=view.findViewById(R.id.resendOtp);
        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneno.getText().toString(),60, TimeUnit.SECONDS,getActivity(),
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String otp, @NonNull  PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(otp, forceResendingToken);




                            }
                        });



            }
        });

        otp=view.findViewById(R.id.otp);
        verify=view.findViewById(R.id.verify);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify.setClickable(false);
                verify.setBackgroundColor(Color.parseColor("#BEFF5722"));
                progressBar.setVisibility(View.VISIBLE);
                if(otp.length()==6){


                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpbackend, otp.getText().toString());

                        FirebaseAuth.getInstance().signInWithCredential(credential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull  Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();

                                            FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                                            String user_id=firebaseUser.getUid();
                                            Map<String,Object> userRecords=new HashMap<>();
                                            userRecords.put("user_id",user_id);
                                            userRecords.put("user_name","");
                                            userRecords.put("user_address","");
                                            userRecords.put("pincode","");
                                            userRecords.put("phone_number",getArguments().getString("phoneno"));


                                            DocumentReference docIdRef = firebaseFirestore.collection("users").document(user_id);
                                            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot document = task.getResult();
                                                        if (document.exists()) {

                                                        } else {
                                                            firebaseFirestore.collection("users").document(user_id).set(userRecords);
                                                        }
                                                    } else {
                                                        Log.d(TAG, "Failed with: ", task.getException());
                                                    }
                                                }
                                            });

                                            getActivity().startActivity(new Intent(getActivity(),com.example.flipkart.MainActivity1.class));
                                        }else{
                                            progressBar.setVisibility(View.GONE);
                                            verify.setClickable(false);
                                            verify.setBackgroundColor(Color.parseColor("#FF5722"));
                                            Toast.makeText(getContext(),"Wrong OTP Enter",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }
                }

        });
        return view;
    }
}