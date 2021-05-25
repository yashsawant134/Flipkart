package com.example.flipkart.ui.addAddress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;

import com.example.flipkart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class AddAddress extends AppCompatActivity {

    TextInputEditText name,address,pincode,nearbylocation;
    AppCompatButton add;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);


        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        String userid=firebaseUser.getUid();

        name=findViewById(R.id.username);
        address=findViewById(R.id.useraddress);
        pincode=findViewById(R.id.userpincode);
        nearbylocation=findViewById(R.id.nearbylocation);

        add=findViewById(R.id.saveaddress);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nam=name.getText().toString();
                String addres=address.getText().toString();
                String pin=pincode.getText().toString();
                String nearby=nearbylocation.getText().toString();

                Map<String,Object> userdata=new HashMap<>();
                userdata.put("name",nam);
                userdata.put("address",addres);
                userdata.put("pincode",pin);
                userdata.put("near_by_location",nearby);

                firebaseFirestore.collection("users").document(userid).collection("address").document().set(userdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            onBackPressed();
                        }
                    }
                });

            }
        });


        firebaseFirestore.collection("users").document(userid).collection("address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){

                    for(QueryDocumentSnapshot documentSnapshots:task.getResult()){
                        name.setText(documentSnapshots.get("name").toString());
                        address.setText(documentSnapshots.get("address").toString());
                        pincode.setText(documentSnapshots.get("pincode").toString());
                        nearbylocation.setText(documentSnapshots.get("near_by_location").toString());

                    }

                }
            }
        });

    }
}