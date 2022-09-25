package com.example.motibook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment implements OnBackPressedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private Spinner filter;
    private SearchView noteSearch;
    private RecyclerView noteList;
    private ArrayList<NoteListItem> noteListItems;
    private FloatingActionButton noteAddButton;
    int noteSearchFlag = 0;

    // Search 에서 호출하기 위해 여기에 선언
    NoteListItemAdapter noteListItemAdapter;

    public NoteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoteFragment newInstance(String param1, String param2) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
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
        // noteAddButton 멤버는 FloatingActionButton noteAddButton 임
        noteAddButton = (FloatingActionButton) rootView.findViewById(R.id.noteAddButton);

        //Filter 항목을 준비한 Array 와 연결해줄 Adapter
        ArrayAdapter<CharSequence> noteFilterAdapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.noteFilterArray, android.R.layout.simple_spinner_item);
        //filter 의 작동방식 지정
        noteFilterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // noteList Manager 지정
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        noteList.setLayoutManager(linearLayoutManager);
        // noteList 항목을 Array 와 연결해줄 Adapter 생성
        noteListItemAdapter = new NoteListItemAdapter(noteListItems);
        noteList.setAdapter(noteListItemAdapter);
        // filter 와 Adapter 연결
        filter.setAdapter(noteFilterAdapter);

        // 여러가지 Listener 설정
        filter.setOnItemSelectedListener(noteFilterListener);
        noteSearch.setOnQueryTextListener(noteSearchListener);
        noteAddButton.setOnClickListener(noteAddButtonListener);

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
            // TODO: query 받아 검색
            if(noteSearchFlag == 0) { // 제목 검색인 경우

            }
            else if (noteSearchFlag == 1) { // ISBN 검색인 경우

            }
            /// For Test Start
            noteListItems.clear();
            noteListItems.add(new NoteListItem("1000000000001", "xxjg48ghag"));
            noteListItems.add(new NoteListItem("1000000000002", "8yzwktapad"));
            noteListItems.add(new NoteListItem("1000000000003", "hsc73iajdn"));
            noteListItems.add(new NoteListItem("1000000000004", "mz6rby2bhl"));
            noteListItems.add(new NoteListItem("1000000000005", "pi2tkk69fw"));
            noteListItems.add(new NoteListItem("1000000000006", "u7k58rjubo"));
            noteListItems.add(new NoteListItem("1000000000007", "bxmu5yvwgw"));
            noteListItems.add(new NoteListItem("1000000000008", "l5osx6m00j"));
            noteListItems.add(new NoteListItem("1000000000009", "0k05embc8r"));
            noteListItems.add(new NoteListItem("1000000000010", "y1rqccos64"));

            Toast.makeText(getActivity(), "검색어 = "+query, Toast.LENGTH_LONG).show();
            /// For Test End

            // noteSearchListView 갱신
            noteListItemAdapter.notifyDataSetChanged();

            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };

    View.OnClickListener noteAddButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 파일 생성 화면 띄우기
        }
    };

    @Override
    public void onBackPressed() {
        MainActivity activity = (MainActivity) getActivity();
        activity.FragmentView(1);
    }
}