package com.example.motibook;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class AddNoteFragment extends Fragment implements OnBackPressedListener {
    MainActivity mainAct;
    TextView fileName;
    EditText contents;
    EditText noteRating;

    String fileNameString;
    String contentString;

    int prevRating;
    int newRating;

    public AddNoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mainAct = (MainActivity)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mainAct = null;
    }

    public static AddNoteFragment newInstance(String param1, String param2) {
        AddNoteFragment fragment = new AddNoteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_add_note, container, false);

        // Filepath 에서 txt 파일 이름 추출
        String[] tmpArr = mainAct.getLastFilePath().split("#&#");
        fileNameString = tmpArr[2].substring(0, tmpArr[2].length()-4);

        // TextView 에 파일 이름 세팅
        fileName = (TextView)rootView.findViewById(R.id.noteName);
        fileName.setText(fileNameString);

        // contents 는 본문 EditText
        contents = (EditText)rootView.findViewById(R.id.TextEditor);
        // noteRating 은 평점 EditText
        noteRating = (EditText)rootView.findViewById(R.id.ratingEditText);

        prevRating = Integer.parseInt(tmpArr[0].split("notes/")[1]);
        newRating = prevRating;
        String strPrevRating = String.format("%.1f", prevRating / 10.0);
        noteRating.setText(strPrevRating);

        noteRating.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty()) {
                    double dbNewRating = Double.parseDouble(s.toString());
                    if(dbNewRating < 0) {
                        noteRating.setText("0.0");
                        newRating = 0;
                    }
                    else if(dbNewRating > 5.0) {
                        noteRating.setText("5.0");
                        newRating = 50;
                    }
                    else {
                        newRating = (int)(dbNewRating * 10);
                    }
                }
                else {
                    newRating = 0;
                }
            }
        });

        // Filepath 이용하여 txt 파일 열기
        File txtFile = new File(mainAct.getLastFilePath());
        if(txtFile.exists()) {
            contentString = new String();
            try {
                String str;
                BufferedReader fr = new BufferedReader(new FileReader(txtFile));
                while((str = fr.readLine()) != null) {
                    contentString += (str + '\n');
                }
                contents.setText(contentString);
            } catch(IOException e) {
            }
        }

        return rootView;
    }

    @Override
    public void onBackPressed() {
        MainActivity activity = (MainActivity) getActivity();

        try {
            String tmpArr[] = mainAct.getLastFilePath().split("#&#");

            if(prevRating != newRating) {
                String nTmpArr[] = tmpArr[0].split("notes/");
                tmpArr[0] = nTmpArr[0] + "notes/" + newRating;
            }

            String newFileName = tmpArr[0] + "#&#" + tmpArr[1] + "#&#" + tmpArr[2];
            FileWriter fw = new FileWriter(newFileName);
            String str = contents.getText().toString();

            fw.write(str);
            Toast.makeText(getActivity(), "저장되었습니다", Toast.LENGTH_SHORT).show();

            if(prevRating != newRating) {
                File prevFile = new File(mainAct.getLastFilePath());
                prevFile.delete();
                mainAct.setLastFilePath(newFileName);
            }

            fw.flush();
            fw.close();
        } catch (IOException e) {
        }

        activity.FragmentView(2);
    }
}