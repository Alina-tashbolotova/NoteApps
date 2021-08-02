package com.example.noteapps.ui.form;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.noteapps.R;
import com.example.noteapps.databinding.FragmentFormBinding;
import com.example.noteapps.model.TaskModel;
import com.example.noteapps.utils.MyApp;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static androidx.core.content.ContextCompat.getSystemService;


public class FormFragment extends Fragment {
    FragmentFormBinding binding;
    TaskModel model;
    NavController navController;
    public static final Integer RecordAudioRequestCode = 1;
    private SpeechRecognizer speechRecognizer;
    private int position;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFormBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        }
        initClickListener();
        initBack();
        speechMic();
        return root;

    }


    private void speechMic() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());
        final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {
                binding.etTitle.setText("");
                binding.etTitle.setHint("Говорите...");

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {
                binding.imMicrophone.setImageResource(R.drawable.ic_micoff24);
                ArrayList<String> list = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                binding.etTitle.setText(list.get(0));


            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });
        binding.imMicrophone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    speechRecognizer.stopListening();
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    binding.imMicrophone.setImageResource(R.drawable.ic_mic24);
                    speechRecognizer.startListening(speechRecognizerIntent);
                }
                return false;
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        speechRecognizer.destroy();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, RecordAudioRequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RecordAudioRequestCode && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();
        }
    }


    private void initBack() {
        binding.imageBack.setOnClickListener(v -> {
            close();
        });
    }

    private void initClickListener() {
        binding.txtReady.setOnClickListener(v -> {
            if (binding.etTitle.getText().toString().isEmpty()) {
                binding.etTitle.setError("Enter text");
                YoYo.with(Techniques.Bounce)
                        .duration(1000)
                        .repeat(1)
                        .playOn(binding.etTitle);
            } else {
                save();
            }

        });
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            model = (TaskModel) getArguments().getSerializable("key");
            if (model != null) {
                binding.etTitle.setText(model.getTitle());
            }
        }
        if (getArguments() != null) {
            TaskModel taskModel = (TaskModel) getArguments().getSerializable("key");
            position = getArguments().getInt("pos");
            binding.etTitle.setText(taskModel.getTitle());

        }

    }


    private void save() {
        String title2 = binding.etTitle.getText().toString();
        if (model == null) {
            model = new TaskModel(title2);
            MyApp.getInstance().noteDao().insertNote(model);
            Log.e("TAG", "FormFragment save: " + title2);
        } else {
            model.setTitle(title2);
            MyApp.getInstance().noteDao().update(model);
            Log.e("TAG", "FormFragment save update: " + title2);
        }

        close();
    }

    public void close() {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        navController.navigateUp();

    }


}