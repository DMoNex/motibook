package com.example.motibook;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddNoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNoteFragment extends Fragment implements OnBackPressedListener {
    MainActivity mainAct;
    TextView fileName;
    EditText contents;

    String fileNameString;
    String contentString;

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
        fileNameString = tmpArr[1].substring(0, tmpArr[1].length()-4);

        // TextView 에 파일 이름 세팅
        fileName = (TextView)rootView.findViewById(R.id.noteName);
        fileName.setText(fileNameString);

        // contents 는 본문 EditText
        contents = (EditText)rootView.findViewById(R.id.TextEditor);

        // Filepath 이용하여 txt 파일 열기
        File txtFile = new File(mainAct.getLastFilePath());
        if(txtFile.exists()) {
            contentString = new String();
            // TODO :: txt 파일 읽어 Content 에 저장, 화면에 띄우기
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
            FileWriter fw = new FileWriter(mainAct.getLastFilePath());
            String str = contents.getText().toString();

            fw.write(str);
            Toast.makeText(getActivity(), "저장되었습니다", Toast.LENGTH_SHORT).show();

            fw.flush();
            fw.close();
        } catch (IOException e) {
        }

        activity.FragmentView(2);
    }
}