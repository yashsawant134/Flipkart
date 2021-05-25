package com.example.flipkart;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flipkart.ui.addAddress.AddAddress;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SelectAddress extends BottomSheetDialogFragment {
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    String userid;
   LinearLayout adsresss;
   AppCompatButton addaddress;
   TextView selectaddressname,select_address_address;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_select_address, container, false);

        firebaseFirestore= FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        userid=firebaseUser.getUid();

        adsresss=view.findViewById(R.id.address);
        addaddress=view.findViewById(R.id.addaddress);

        selectaddressname=view.findViewById(R.id.selectaddressname);
        select_address_address=view.findViewById(R.id.selectaddess_address);

        firebaseFirestore.collection("users").document(userid).collection("address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.getResult().size()>0){
                    QuerySnapshot documentSnapshot=task.getResult();
                    adsresss.setVisibility(View.VISIBLE);
                    addaddress.setText("Change Address");
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        selectaddressname.setText(document.get("name").toString()+", "+document.get("pincode").toString());
                        select_address_address.setText(document.get("address").toString());
                    }


                    addaddress.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getActivity().startActivity(new Intent(getContext(), AddAddress.class));
                        }
                    });


                }else{
                   adsresss.setVisibility(View.GONE);
                    addaddress.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getActivity().startActivity(new Intent(getContext(), AddAddress.class));
                        }
                    });

                }
            }
        });

        return view;
    }
}