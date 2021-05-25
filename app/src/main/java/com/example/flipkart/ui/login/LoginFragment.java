package com.example.flipkart.ui.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.flipkart.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class LoginFragment extends Fragment {

    AppCompatButton sendOtp;
    TextInputEditText phonenumber;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_login, container, false);

        sendOtp=view.findViewById(R.id.sendOtp);
        phonenumber=view.findViewById(R.id.enterphonenumber);
        progressBar=view.findViewById(R.id.progressBar);


        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(phonenumber.length()==10){
                    sendOtp.setClickable(false);
                    progressBar.setVisibility(View.VISIBLE);
                    PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+phonenumber.getText().toString(),60, TimeUnit.SECONDS,getActivity(),
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    sendOtp.setClickable(true);
                                }

                                @Override
                                public void onCodeSent(@NonNull String otp, @NonNull  PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    super.onCodeSent(otp, forceResendingToken);
                                    EnterOTPFragment enterOTPFragment=new EnterOTPFragment();
                                    Bundle bundle=new Bundle();
                                    bundle.putString("OTP",otp);
                                    bundle.putString("phoneno",phonenumber.getText().toString());
                                    enterOTPFragment.setArguments(bundle);


                                    FragmentManager manager = getFragmentManager();
                                    FragmentTransaction transaction = manager.beginTransaction();
                                    transaction.replace(R.id.frame,enterOTPFragment).commit();
                                    progressBar.setVisibility(View.GONE);

                                    sendOtp.setClickable(true);
                                }
                            });


                }else{

                        phonenumber.setError("Not a valid phone number");

                }



            }
        });
        return view;
    }
}