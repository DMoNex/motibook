package com.example.motibook;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticsFragment extends Fragment {
    private int count; // 파일 입출력 통해 받을 값(읽은 책 수)
    ArrayList<BarEntry> bookGraphDataEntries = new ArrayList<>();
    TextView countView;
    MainActivity mainAct;
    BarChart bookGraph;

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
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /// Test Data Start
        count = 7777;
        bookGraphDataEntries.clear();
        bookGraphDataEntries.add(new BarEntry(0, 100));
        bookGraphDataEntries.add(new BarEntry(1, 100));
        bookGraphDataEntries.add(new BarEntry(2, 100));
        bookGraphDataEntries.add(new BarEntry(3, 100));
        bookGraphDataEntries.add(new BarEntry(4, 100));
        bookGraphDataEntries.add(new BarEntry(5, 100));
        bookGraphDataEntries.add(new BarEntry(6, 100));
        bookGraphDataEntries.add(new BarEntry(7, 100));
        bookGraphDataEntries.add(new BarEntry(8, 100));
        bookGraphDataEntries.add(new BarEntry(9, 100));

        //String[] labels = {"총류, 도서학, 문헌정보학, 백과사전, 강연&수필&연설, 간행물, 학회, 신문, 문학, 향토"}

        /// Test Data End
        if (getArguments() != null) {
            count = 4896; // TODO: 여기서 파일입출력으로 읽은 책의 수를 받아옴
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // onCreate() 에서 받은 값들로 화면을 구성하는 함수
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_statistics, container, false);
        countView = rootView.findViewById(R.id.textCountView);
        countView.setText(Integer.toString(count));
        countView.setOnClickListener(addBookListener);

        bookGraph = rootView.findViewById(R.id.bookGraph);

        BarDataSet bookGraphDataset = new BarDataSet(bookGraphDataEntries, "book count");
        bookGraphDataset.setColors(ColorTemplate.PASTEL_COLORS);
        bookGraphDataset.setValueTextColor(Color.BLACK);
        bookGraphDataset.setValueTextSize(16f);

        BarData bookGraphData = new BarData(bookGraphDataset);

        XAxis xAxis = bookGraph.getXAxis();
        xAxis.setDrawLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.toString();
        xAxis.setSpaceMax(1f);
        xAxis.setSpaceMin(1f);
        xAxis.setGranularity(1);

        bookGraph.setData(bookGraphData);

        return rootView;
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

    View.OnClickListener addBookListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mainAct.onAddBookFragment();
        }
    };
}