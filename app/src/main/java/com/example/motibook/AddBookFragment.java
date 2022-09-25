package com.example.motibook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Stream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddBookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class AddBookFragment extends Fragment implements OnBackPressedListener {

    private Spinner filter;
    private SearchView bookSearch;
    int bookSearchFlag = 0;

    public AddBookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddBookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddBookFragment newInstance(String param1, String param2) {
        AddBookFragment fragment = new AddBookFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_add_book, container, false);

        filter = (Spinner)rootView.findViewById(R.id.bookSearchFilter);
        bookSearch = (SearchView) rootView.findViewById(R.id.bookSearchView);

        // Filter Array Note Filter Array 공유
        ArrayAdapter<CharSequence> bookFilterAdapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.noteFilterArray, android.R.layout.simple_spinner_item);
        bookFilterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        filter.setAdapter(bookFilterAdapter);
        filter.setOnItemSelectedListener(bookFilterListener);

        bookSearch.setOnQueryTextListener(bookSearchListener);

        return rootView;
    }

    AdapterView.OnItemSelectedListener bookFilterListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0: // SearchView 에서 제목으로 검색
                    bookSearchFlag = 0;
                case 1: // SearchView 에서 ISBN 으로 검색
                    bookSearchFlag = 1;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    SearchView.OnQueryTextListener bookSearchListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            // TODO: query 받아 검색
            if(bookSearchFlag == 0) { // 제목 검색인 경우

            }
            else if (bookSearchFlag == 1) { // ISBN 검색인 경우

            }
            //Test Line
            //검색 결과 받아왔다고 가정. 일단 800번(문학)이라고 가정함
            int dataIndex = 8;
            int data = ((MainActivity)getActivity()).statisticsData.data[dataIndex] + 1;
            ((MainActivity)getActivity()).statisticsData.data[dataIndex] += 1;
            ((MainActivity)getActivity()).statisticsData.totalNumUpdate();

            String filepath = new String(getActivity().getFilesDir().toString() + "/stastics");
            File statDir = new File(filepath);
            // 하위폴더 미존재시 생성
            if(!statDir.exists()) {
                statDir.mkdir();
            }
            File statFile = new File(statDir + "/StatisticsFile.txt");
            // 파일 존재시 삭제 후 재생성
//            if(statFile.exists()) {
//                statFile.delete();
//            }

            try {
                statFile.createNewFile();
                FileWriter fw = new FileWriter(statFile);
                fw.write(String.format("%d\n%d\n%d\n%d\n%d\n%d\n%d\n%d\n%d\n%d\n",
                        ((MainActivity)getActivity()).statisticsData.data[0],
                        ((MainActivity)getActivity()).statisticsData.data[1],
                        ((MainActivity)getActivity()).statisticsData.data[2],
                        ((MainActivity)getActivity()).statisticsData.data[3],
                        ((MainActivity)getActivity()).statisticsData.data[4],
                        ((MainActivity)getActivity()).statisticsData.data[5],
                        ((MainActivity)getActivity()).statisticsData.data[6],
                        ((MainActivity)getActivity()).statisticsData.data[7],
                        ((MainActivity)getActivity()).statisticsData.data[8],
                        ((MainActivity)getActivity()).statisticsData.data[9]));
                fw.flush();
                fw.close();
            } catch (IOException e) {
                System.out.println (e.toString());
            }

            Toast.makeText(getActivity(), String.format("파일생성 %d", ((MainActivity)getActivity()).statisticsData.totalNum), Toast.LENGTH_LONG).show();

            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        MainActivity activity = (MainActivity) getActivity();
        activity.FragmentView(1);
    }


}