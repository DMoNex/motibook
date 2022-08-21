package com.example.motibook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TEXT_HEADER = "지금까지 총";
    private static final String TEXT_FOOTER = "권의 책을 읽었습니다.";

    // TODO: Rename and change types of parameters
    // private String header;
    // private String footer;
    private int count; // 파일 입출력 통해 받을 값(읽은 책 수)
    TextView countView;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StatisticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticsFragment newInstance() {
        StatisticsFragment fragment = new StatisticsFragment();
        Bundle args = new Bundle();
//        args.putString(TEXT_HEADER, header);
//        args.putString(TEXT_FOOTER, footer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        count = 7777; // Test Line
        if (getArguments() != null) {
            count = 4896; // TODO: 여기서 파일입출력으로 읽은 책의 수를 받아옴
            // header = getArguments().getString(TEXT_HEADER);
            // footer = getArguments().getString(TEXT_FOOTER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // onCreate() 에서 받은 값들로 화면을 구성하는 함수
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_statistics, container, false);
        countView = rootView.findViewById(R.id.textCountView);
        countView.setText(Integer.toString(count));
        return rootView;
    }
}