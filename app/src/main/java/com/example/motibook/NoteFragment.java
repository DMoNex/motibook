package com.example.motibook;

import android.os.Bundle;

import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private Spinner filter;
    private SearchView noteSearch;
    int noteSearchFlag = 0;

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
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
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

        filter = (Spinner)rootView.findViewById(R.id.noteSearchFilter);
        noteSearch = (SearchView) rootView.findViewById(R.id.noteSearchView);

        ArrayAdapter<CharSequence> noteFilterAdapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.noteFilterArray, android.R.layout.simple_spinner_dropdown_item);
        noteFilterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        filter.setAdapter(noteFilterAdapter);
        filter.setOnItemSelectedListener(noteFilterListener);

        noteSearch.setOnQueryTextListener(noteSearchListener);

        return rootView;
    }

    AdapterView.OnItemSelectedListener noteFilterListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0: // SearchView 에서 제목으로 검색
                    noteSearchFlag = 0;
                case 1: // SearchView 에서 ISBN 으로 검색
                    noteSearchFlag = 1;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    SearchView.OnQueryTextListener noteSearchListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            // TODO: query 받아 검색
            if(noteSearchFlag == 0) { // 제목 검색인 경우

            }
            else if (noteSearchFlag == 1) { // ISBN 검색인 경우

            }
            //Test Line
            Toast.makeText(getActivity(), "검색어 = "+query, Toast.LENGTH_LONG).show();
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };
}