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
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticsFragment extends Fragment {
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

        // File I/O Start
        String filepath = new String(getActivity().getFilesDir().toString() + "/stastics");
        File statDir = new File(filepath);
        // 하위폴더 미존재시 생성
        if(!statDir.exists()) {
            statDir.mkdir();
        }

        File statFile = new File(statDir + "/StatisticsFile.txt");
        // 파일 미존재시 생성(통계데이터 0으로 초기화)
        if(!statFile.exists()) {
            try {
                statFile.createNewFile();
                FileWriter fw = new FileWriter(statFile);
                fw.write("0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n");
                fw.flush();
                fw.close();
            } catch (IOException e) {
                System.out.println (e.toString());
            }
        }

        try { // 파일 읽어오기
            BufferedReader fr = new BufferedReader(new FileReader(statFile));

            for(int i = 0; i < 10; ++i) {
                ((MainActivity)getActivity()).statisticsData.data[i] = Integer.parseInt(fr.readLine());
            }
            ((MainActivity)getActivity()).statisticsData.totalNumUpdate();

        } catch (IOException e) {
            System.out.println(e.toString());
        }

        /// Test Data End
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // onCreate() 에서 받은 값들로 화면을 구성하는 함수
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_statistics, container, false);
        countView = rootView.findViewById(R.id.textCountView);
        countView.setText(Integer.toString(((MainActivity)getActivity()).statisticsData.totalNum));
        countView.setOnClickListener(addBookListener);

        bookGraph = rootView.findViewById(R.id.bookGraph);

        // 읽어 온 데이터로 그래프 생성
        bookGraphDataEntries.clear();
        bookGraphDataEntries.add(new BarEntry(0, ((MainActivity)getActivity()).statisticsData.data[0]));
        bookGraphDataEntries.add(new BarEntry(1, ((MainActivity)getActivity()).statisticsData.data[1]));
        bookGraphDataEntries.add(new BarEntry(2, ((MainActivity)getActivity()).statisticsData.data[2]));
        bookGraphDataEntries.add(new BarEntry(3, ((MainActivity)getActivity()).statisticsData.data[3]));
        bookGraphDataEntries.add(new BarEntry(4, ((MainActivity)getActivity()).statisticsData.data[4]));
        bookGraphDataEntries.add(new BarEntry(5, ((MainActivity)getActivity()).statisticsData.data[5]));
        bookGraphDataEntries.add(new BarEntry(6, ((MainActivity)getActivity()).statisticsData.data[6]));
        bookGraphDataEntries.add(new BarEntry(7, ((MainActivity)getActivity()).statisticsData.data[7]));
        bookGraphDataEntries.add(new BarEntry(8, ((MainActivity)getActivity()).statisticsData.data[8]));
        bookGraphDataEntries.add(new BarEntry(9, ((MainActivity)getActivity()).statisticsData.data[9]));

        setGraphStyle();
        bookGraph.invalidate();

        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mainAct = (MainActivity)getActivity();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        bookGraph.invalidate();
//    }

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
        bookGraphDataset.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float data) {
                return String.format("%d", (int)data);
            }
        });

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