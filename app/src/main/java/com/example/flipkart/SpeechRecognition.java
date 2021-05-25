package com.example.flipkart;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flipkart.ui.search.SearchActivity;
import com.example.flipkart.ui.search.SearchResultActivity;
import com.github.zagum.speechrecognitionview.RecognitionProgressView;
import com.github.zagum.speechrecognitionview.adapters.RecognitionListenerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class SpeechRecognition extends BottomSheetDialogFragment {

    SpeechRecognizer speechRecognizer ;
    FirebaseFirestore firebaseFirestore;
    ArrayList<String> resultarray;
    int x = 0;
    String search_string;

    TextView text;
    private static final String TAG = "getContext";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION_CODE = 1;
    RecognitionProgressView recognitionProgressView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_speech_recognition, container, false);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(view.getContext());
        firebaseFirestore = FirebaseFirestore.getInstance();
        resultarray = new ArrayList<>();


        text=view.findViewById(R.id.yourspeech);


        int[] colors = {
                ContextCompat.getColor(getContext(), R.color.colorPrimary),
                ContextCompat.getColor(getContext(), R.color.colorPrimary),
                ContextCompat.getColor(getContext(), R.color.colorPrimary),
                ContextCompat.getColor(getContext(), R.color.colorPrimary)



        };



        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());

        recognitionProgressView = (RecognitionProgressView)view.findViewById(R.id.recognition_view);
        recognitionProgressView.setSpeechRecognizer(speechRecognizer);
        recognitionProgressView.play();
        recognitionProgressView.setRecognitionListener(new RecognitionListenerAdapter() {
            @Override
            public void onResults(Bundle results) {
                showResults(results);
            }
        });
        int[] heights = { 30, 44, 58, 43, 26 };
        recognitionProgressView.setColors(colors);
        recognitionProgressView.setBarMaxHeightsInDp(heights);
        recognitionProgressView.setCircleRadiusInDp(5);
        recognitionProgressView.setSpacingInDp(7);
        recognitionProgressView.setIdleStateAmplitudeInDp(15);
        recognitionProgressView.setRotationRadiusInDp(25);






                startRecognition();
                recognitionProgressView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startRecognition();
                    }
                }, 10);







        return view;
    }

    @Override
    public void onDestroy() {
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
        super.onDestroy();
    }

    private void startRecognition() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        String packagename=getActivity().getPackageName();
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packagename );
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en");
        speechRecognizer.startListening(intent);
    }

    private void showResults(Bundle results) {
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        text.setText(matches.get(0));

        search_string= matches.get(0);

        readData(new SearchActivity.MyCallback() {


            @Override
            public void onCallback() {

                show();
            }
        });
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.RECORD_AUDIO)) {
            Toast.makeText(getContext(), "Requires RECORD_AUDIO permission", Toast.LENGTH_SHORT).show();

        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[] { Manifest.permission.RECORD_AUDIO },
                    REQUEST_RECORD_AUDIO_PERMISSION_CODE);
        }
    }



    public void show() {


        Intent intent = new Intent(getContext(), SearchResultActivity.class);
        intent.putExtra("products_id", resultarray);
        intent.putExtra("searchString",search_string);
        startActivity(intent);


    }



    public interface MyCallback {
        void onCallback();
    }


    public void readData(final SearchActivity.MyCallback myCallback) {

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


}