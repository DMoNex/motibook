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
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
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

    String[] labels = {"총류", "철학", "종교", "사회", "자연", "기술", "예술", "언어", "문학", "역사"};

    public StatisticsFragment() {
        // Required empty public constructor
    }

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
        bookGraphDataEntries.add(new BarEntry(1, 200));
        bookGraphDataEntries.add(new BarEntry(2, 50));
        bookGraphDataEntries.add(new BarEntry(3, 30));
        bookGraphDataEntries.add(new BarEntry(4, 33));
        bookGraphDataEntries.add(new BarEntry(5, 81));
        bookGraphDataEntries.add(new BarEntry(6, 130));
        bookGraphDataEntries.add(new BarEntry(7, 83));
        bookGraphDataEntries.add(new BarEntry(8, 350));
        bookGraphDataEntries.add(new BarEntry(9, 200));

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

        setGraphStyle();

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

    void setGraphStyle() {
        BarDataSet bookGraphDataset = new BarDataSet(bookGraphDataEntries, "book count");
        bookGraphDataset.setColor(Color.parseColor("#99FFCC"));
        bookGraphDataset.setValueTextColor(Color.BLACK);
        bookGraphDataset.setValueTextSize(16f);

        BarData bookGraphData = new BarData(bookGraphDataset);

        XAxis xAxis = bookGraph.getXAxis();
        xAxis.setLabelCount(10);
        xAxis.setDrawLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMinimum(-1f);
        xAxis.setAxisMaximum(10f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        YAxis yAxisL = bookGraph.getAxisLeft();
        yAxisL.setDrawGridLines(false);
        yAxisL.setDrawAxisLine(false);
        yAxisL.setAxisMinimum(0f);
        yAxisL.setDrawLabels(false);

        YAxis yAxisR = bookGraph.getAxisRight();
        yAxisR.setDrawGridLines(false);
        yAxisR.setDrawAxisLine(false);
        yAxisR.setDrawLabels(false);

        bookGraph.getDescription().setEnabled(false);
        bookGraph.setTouchEnabled(false);
        bookGraph.getLegend().setEnabled(false);
        bookGraph.setExtraOffsets(10f, 0f, 10f, 30f);

        // 데이터 세팅
        bookGraph.setData(bookGraphData);
    }
}