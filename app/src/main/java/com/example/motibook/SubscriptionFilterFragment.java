package com.example.motibook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubscriptionFilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubscriptionFilterFragment extends Fragment {

    private Spinner regionFilter;
    private Spinner subRegionFilter;

    ArrayAdapter<CharSequence> regionFilterAdapter;
    ArrayAdapter<CharSequence> subRegionFilterAdapter;

    public SubscriptionFilterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubscriptionFilterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubscriptionFilterFragment newInstance(String param1, String param2) {
        SubscriptionFilterFragment fragment = new SubscriptionFilterFragment();
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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_subscription_filter, container, false);

        regionFilter = (Spinner)rootView.findViewById(R.id.RegionAddr1Spinner);
        subRegionFilter = (Spinner)rootView.findViewById(R.id.RegionAddr2Spinner);

        //regionFilter 항목을 준비한 Array 와 연결해줄 Adapter
        regionFilterAdapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.address, android.R.layout.simple_spinner_item);
        //regionFilter 의 작동방식 지정
        regionFilterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //regionFilter 리스너
        regionFilter.setOnItemSelectedListener(regionFilterListener);
        //regionFilter 와 Adapter 연결
        regionFilter.setAdapter(regionFilterAdapter);

        return rootView;
    }

    // regionFilterListener (Spinner)
    AdapterView.OnItemSelectedListener regionFilterListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0: // 전체
                    break;
                case 1: // 강원도
                    subRegionFilterAdapter = ArrayAdapter.createFromResource(
                            getActivity(), R.array.Gangwon, android.R.layout.simple_spinner_item);
                    subRegionFilter.setOnItemSelectedListener(subRegionFilterListenerGangwon);
                    break;
                case 2: // 경기도
                    subRegionFilterAdapter = ArrayAdapter.createFromResource(
                            getActivity(), R.array.Gyeonggi, android.R.layout.simple_spinner_item);
                    subRegionFilter.setOnItemSelectedListener(subRegionFilterListenerGyeonggi);
                    break;
                case 3: // 경상남도
                    subRegionFilterAdapter = ArrayAdapter.createFromResource(
                            getActivity(), R.array.GyeongsangS, android.R.layout.simple_spinner_item);
                    subRegionFilter.setOnItemSelectedListener(subRegionFilterListenerGyeongsangS);
                    break;
                case 4: // 경상북도
                    break;
                case 5: // 광주
                    break;
                case 6: // 대구
                    break;
                case 7: // 대전
                    break;
                case 8: // 부산
                    break;
                case 9: // 서울
                    break;
                case 10: // 세종
                    break;
                case 11: // 울산
                    break;
                case 12: // 인천
                    break;
                case 13: // 전라남도
                    break;
                case 14: // 전라북도
                    break;
                case 15: // 제주
                    break;
                case 16: // 충청남도
                    break;
                case 17: // 충청북도
                    break;
            }
            subRegionFilterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            subRegionFilter.setAdapter(subRegionFilterAdapter);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO : subRegionFilter 를 empty Array 로 지정해야 함
        }
    };

    AdapterView.OnItemSelectedListener subRegionFilterListenerGangwon = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0: // 전체
                    break;
                case 1: // 강릉시
                    break;
                case 2: // 고성군
                    break;
                case 3: // 동해시
                    break;
                case 4: // 삼척시
                    break;
                case 5: // 속초시
                    break;
                case 6: // 양구군
                    break;
                case 7: // 양양군
                    break;
                case 8: // 영월군
                    break;
                case 9: // 원주시
                    break;
                case 10: // 인제군
                    break;
                case 11: // 정선군
                    break;
                case 12: // 철원군
                    break;
                case 13: // 춘천시
                    break;
                case 14: // 태백시
                    break;
                case 15: // 평창군
                    break;
                case 16: // 홍천군
                    break;
                case 17: // 화천군
                    break;
                case 18: // 횡성군
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener subRegionFilterListenerGyeonggi = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0: // 전체
                    break;
                case 1: // 가평군
                    break;
                case 2: // 고양시
                    break;
                case 3: // 과천시
                    break;
                case 4: // 광명시
                    break;
                case 5: // 광주시
                    break;
                case 6: // 구리시
                    break;
                case 7: // 군포시
                    break;
                case 8: // 김포시
                    break;
                case 9: // 남양주시
                    break;
                case 10: // 동두천시
                    break;
                case 11: // 부천시
                    break;
                case 12: // 성남시
                    break;
                case 13: // 수원시
                    break;
                case 14: // 시흥시
                    break;
                case 15: // 안산시
                    break;
                case 16: // 안성시
                    break;
                case 17: // 안양시
                    break;
                case 18: // 양주시
                    break;
                case 19: // 양평군
                    break;
                case 20: // 여주시
                    break;
                case 21: // 연천군
                    break;
                case 22: // 오산시
                    break;
                case 23: // 용인시
                    break;
                case 24: // 의왕시
                    break;
                case 25: // 의정부시
                    break;
                case 26: // 이천시
                    break;
                case 27: // 파주시
                    break;
                case 28: // 평택시
                    break;
                case 29: // 포천시
                    break;
                case 30: // 하남시
                    break;
                case 31: // 화성시
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener subRegionFilterListenerGyeongsangS = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}