package com.example.motibook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

public class NoteFragment extends Fragment implements OnBackPressedListener {
    private Spinner filter;
    private SearchView noteSearch;
    private RecyclerView noteList;
    private ArrayList<NoteListItem> noteListItems;
    int noteSearchFlag = 0;
    MainActivity mainAct;
    InputMethodManager imm;

    // Search 에서 호출하기 위해 여기에 선언
    NoteListItemAdapter noteListItemAdapter;

    public NoteFragment() {
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

    public static NoteFragment newInstance(String param1, String param2) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_note, container, false);

        noteListItems = new ArrayList<NoteListItem>();

        // filter 멤버는 Spinner 메뉴 noteSearchFilter 임
        filter = (Spinner)rootView.findViewById(R.id.noteSearchFilter);
        // noteSearch 멤버는 SearchView noteSearchView 임
        noteSearch = (SearchView) rootView.findViewById(R.id.noteSearchView);
        // noteList 멤버는 RecyclerView noteSearchListView 임
        noteList = (RecyclerView) rootView.findViewById(R.id.noteSearchListView);

        //Filter 항목을 준비한 Array 와 연결해줄 Adapter
        ArrayAdapter<CharSequence> noteFilterAdapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.noteFilterArray, android.R.layout.simple_spinner_item);
        //filter 의 작동방식 지정
        noteFilterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // noteList Manager 지정
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        noteList.setLayoutManager(linearLayoutManager);
        // noteList 항목을 Array 와 연결해줄 Adapter 생성 & Listener 설정
        noteListItemAdapter = new NoteListItemAdapter(noteListItems);
        noteListItemAdapter.setOnItemClickListener(new NoteListItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                String notePath = new String(getActivity().getFilesDir().toString() + "/notes");
                File noteDir = new File(notePath);
                if(!noteDir.exists()) {
                    noteDir.mkdir();
                }

                File noteFile = new File(noteDir + "/" + noteListItems.get(pos).getRating() + "#&#" + noteListItems.get(pos).getISBN() + "#&#" + noteListItems.get(pos).getBookName() + ".txt");
                if(noteFile.exists()) {
                    // TXT 편집 화면으로 이동
                    mainAct.onAddNoteFragment(noteDir + "/" + noteListItems.get(pos).getRating() + "#&#" + noteListItems.get(pos).getISBN() + "#&#" + noteListItems.get(pos).getBookName() + ".txt");
                }

            }
        });

        noteListItemAdapter.setOnItemLongClickListener(new NoteListItemAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, int pos) {

                String notePath = new String(getActivity().getFilesDir().toString() + "/notes");
                File noteDir = new File(notePath);
                if(!noteDir.exists()) {
                    noteDir.mkdir();
                }


                Intent Sharing_intent = new Intent(Intent.ACTION_SEND);
                Sharing_intent.setType("text/plain");

                String Test_Message = "";
                File txtFile = new File(noteDir + "/" + noteListItems.get(pos).getRating() + "#&#" + noteListItems.get(pos).getISBN() + "#&#" + noteListItems.get(pos).getBookName() + ".txt");
                if(txtFile.exists()) {
                    String contentString = new String();
                    try {
                        String str;
                        BufferedReader fr = new BufferedReader(new FileReader(txtFile));
                        while((str = fr.readLine()) != null) {
                            contentString += (str + '\n');
                        }
                        Test_Message =contentString;
                    } catch(IOException e) {
                    }
                }


                Sharing_intent.putExtra(Intent.EXTRA_TEXT, Test_Message);

                Intent Sharing = Intent.createChooser(Sharing_intent, "공유하기");
                startActivity(Sharing);
            }
        });

        // filter 와 Adapter 연결
        filter.setAdapter(noteFilterAdapter);
        // noteList 와 Adapter 연결
        noteList.setAdapter(noteListItemAdapter);

        // 여러가지 Listener 설정
        filter.setOnItemSelectedListener(noteFilterListener);
        noteSearch.setOnQueryTextListener(noteSearchListener);

        String tmpNotepath = new String(getActivity().getFilesDir().toString() + "/notes");
        File tmpNoteDir = new File(tmpNotepath);

        if(!tmpNoteDir.exists()) {
            tmpNoteDir.mkdir();
        }

        noteSearchListener.onQueryTextSubmit("");

        return rootView;
    }

    // 필터 Listener (Spinner)
    AdapterView.OnItemSelectedListener noteFilterListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0: // SearchView 에서 제목으로 검색
                    noteSearchFlag = 0;
                    break;
                case 1: // SearchView 에서 ISBN 으로 검색
                    noteSearchFlag = 1;
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    // 검색창 Listener
    SearchView.OnQueryTextListener noteSearchListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            String noteDir = new String(getActivity().getFilesDir().toString() + "/notes");
            String noteFiles[] = new File(noteDir).list();

            /// For Test Start
            noteListItems.clear();
            for(int i = 0; i < noteFiles.length; ++i) {
                String[] tmpArr = noteFiles[i].split("#&#");

                if(noteSearchFlag == 0) { // 제목 검색인 경우
                    if (tmpArr[2].substring(0, tmpArr[2].length()-4).contains(query)) {
                        noteListItems.add(new NoteListItem(tmpArr[1], tmpArr[2].substring(0, tmpArr[2].length()-4), Integer.parseInt(tmpArr[0])));
                    }
                }
                else if (noteSearchFlag == 1) { // ISBN 검색인 경우
                    if (tmpArr[1].contains(query)) {
                        noteListItems.add(new NoteListItem(tmpArr[1], tmpArr[2].substring(0, tmpArr[2].length()-4), Integer.parseInt(tmpArr[0])));
                    }
                }
            }

            if(!query.isEmpty()) {
                imm.hideSoftInputFromWindow(noteSearch.getWindowToken(), 0);
            }
            //Toast.makeText(getActivity(), "검색어 = "+query, Toast.LENGTH_SHORT).show();
            /// For Test End

            // noteSearchListView 갱신
            noteListItemAdapter.notifyDataSetChanged();

            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if(newText.equals("")) {
                this.onQueryTextSubmit("");
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        MainActivity activity = (MainActivity) getActivity();
        activity.FragmentView(1);
    }
}