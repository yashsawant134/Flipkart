package com.example.flipkart.ui.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flipkart.ProductImagesViewPagerAdapter;
import com.example.flipkart.ProductsDetailActivity;
import com.example.flipkart.R;
import com.example.flipkart.SpeechRecognition;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    EditText searchbar;
    String search_string;
    FirebaseFirestore firebaseFirestore;
    ArrayList<String> resultarray;
    ImageView searchmic;
    int x = 0;

    String voicetext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().hide();
        firebaseFirestore = FirebaseFirestore.getInstance();
        resultarray = new ArrayList<>();

        searchmic=findViewById(R.id.searchAtivitymic);
        searchmic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpeechRecognition speechRecognition=new SpeechRecognition();
                speechRecognition.show(getSupportFragmentManager(),speechRecognition.getTag());
            }
        });

        voicetext=getIntent().getStringExtra("search");

        if(voicetext!=null && !voicetext.isEmpty()){
            search_string=voicetext;
            readData(new MyCallback() {


                @Override
                public void onCallback() {

                    show();
                }
            });
        }

        searchbar = findViewById(R.id.search_bar);


        searchbar.requestFocus();
        searchbar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search_string = searchbar.getText().toString();
                    readData(new MyCallback() {


                        @Override
                        public void onCallback() {

                            show();
                        }
                    });
                }
                return false;
            }
        });


    }


    public void show() {


        Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
        intent.putExtra("products_id", resultarray);
        intent.putExtra("searchString",search_string);
        startActivity(intent);
        finish();

    }


    public interface MyCallback {
        void onCallback();
    }


    public void readData(final MyCallback myCallback) {

        final ArrayList<String> containsID = new ArrayList<>();
        x = 0;

        final String[] splitStr = search_string.trim().split("\\s+");
        resultarray.clear();

        for (int i = 0; i < splitStr.length; i++) {
            firebaseFirestore.collection("products").whereArrayContains("tags", splitStr[i]).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {


                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            if (!containsID.contains(documentSnapshot.getId())) {
                                containsID.add(documentSnapshot.getId());
                                resultarray.add(documentSnapshot.getId());
                            }

                        }
                        if (x == (splitStr.length - 1)) {
                            myCallback.onCallback();
                        }
                        x++;
                    }

                }

            });


        }


    }


    @Override
    public void onBackPressed() {
        searchbar.clearFocus();

        super.onBackPressed();

        finish();
    }
}