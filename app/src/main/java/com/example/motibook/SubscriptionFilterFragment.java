package com.example.motibook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SubscriptionFilterFragment extends Fragment {

    private Spinner regionFilter;
    private Spinner subRegionFilter;

    ArrayAdapter<CharSequence> regionFilterAdapter;
    ArrayAdapter<CharSequence> subRegionFilterAdapter;

    String regionCodeHead;
    String regionCodeTail;

    public SubscriptionFilterFragment() {
        // Required empty public constructor
    }

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
                    subRegionFilterAdapter = ArrayAdapter.createFromResource(
                            getActivity(), R.array.Empty, android.R.layout.simple_spinner_item);
                    subRegionFilter.setOnItemSelectedListener(subRegionFilterListenerEmpty);
                    break;
                case 1: // 강원도 32
                    regionCodeHead = "32";
                    subRegionFilterAdapter = ArrayAdapter.createFromResource(
                            getActivity(), R.array.Gangwon, android.R.layout.simple_spinner_item);
                    subRegionFilter.setOnItemSelectedListener(subRegionFilterListenerGangwon);
                    break;
                case 2: // 경기도 31
                    regionCodeHead = "31";
                    subRegionFilterAdapter = ArrayAdapter.createFromResource(
                            getActivity(), R.array.Gyeonggi, android.R.layout.simple_spinner_item);
                    subRegionFilter.setOnItemSelectedListener(subRegionFilterListenerGyeonggi);
                    break;
                case 3: // 경상남도 38
                    regionCodeHead = "38";
                    subRegionFilterAdapter = ArrayAdapter.createFromResource(
                            getActivity(), R.array.GyeongsangS, android.R.layout.simple_spinner_item);
                    subRegionFilter.setOnItemSelectedListener(subRegionFilterListenerGyeongsangS);
                    break;
                case 4: // 경상북도 37
                    regionCodeHead = "37";
                    subRegionFilterAdapter = ArrayAdapter.createFromResource(
                            getActivity(), R.array.GyeongsangN, android.R.layout.simple_spinner_item);
                    subRegionFilter.setOnItemSelectedListener(subRegionFilterListenerGyeongsangN);
                    break;
                case 5: // 광주 24
                    regionCodeHead = "24";
                    subRegionFilterAdapter = ArrayAdapter.createFromResource(
                            getActivity(), R.array.Gwangju, android.R.layout.simple_spinner_item);
                    subRegionFilter.setOnItemSelectedListener(subRegionFilterListenerGwangju);
                    break;
                case 6: // 대구 22
                    regionCodeHead = "22";
                    subRegionFilterAdapter = ArrayAdapter.createFromResource(
                            getActivity(), R.array.Daegu, android.R.layout.simple_spinner_item);
                    subRegionFilter.setOnItemSelectedListener(subRegionFilterListenerDaegu);
                    break;
                case 7: // 대전 25
                    regionCodeHead = "25";
                    subRegionFilterAdapter = ArrayAdapter.createFromResource(
                            getActivity(), R.array.Daejeon, android.R.layout.simple_spinner_item);
                    subRegionFilter.setOnItemSelectedListener(subRegionFilterListenerDaejeon);
                    break;
                case 8: // 부산 21
                    regionCodeHead = "21";
                    subRegionFilterAdapter = ArrayAdapter.createFromResource(
                            getActivity(), R.array.Busan, android.R.layout.simple_spinner_item);
                    subRegionFilter.setOnItemSelectedListener(subRegionFilterListenerBusan);
                    break;
                case 9: // 서울 11
                    regionCodeHead = "11";
                    subRegionFilterAdapter = ArrayAdapter.createFromResource(
                            getActivity(), R.array.Seoul, android.R.layout.simple_spinner_item);
                    subRegionFilter.setOnItemSelectedListener(subRegionFilterListenerSeoul);
                    break;
                case 10: // 세종 29
                    regionCodeHead = "29";
                    subRegionFilterAdapter = ArrayAdapter.createFromResource(
                            getActivity(), R.array.Sejong, android.R.layout.simple_spinner_item);
                    subRegionFilter.setOnItemSelectedListener(subRegionFilterListenerSejong);
                    break;
                case 11: // 울산 26
                    regionCodeHead = "26";
                    subRegionFilterAdapter = ArrayAdapter.createFromResource(
                            getActivity(), R.array.Ulsan, android.R.layout.simple_spinner_item);
                    subRegionFilter.setOnItemSelectedListener(subRegionFilterListenerUlsan);
                    break;
                case 12: // 인천 23
                    regionCodeHead = "23";
                    subRegionFilterAdapter = ArrayAdapter.createFromResource(
                            getActivity(), R.array.Incheon, android.R.layout.simple_spinner_item);
                    subRegionFilter.setOnItemSelectedListener(subRegionFilterListenerIncheon);
                    break;
                case 13: // 전라남도 36
                    regionCodeHead = "36";
                    subRegionFilterAdapter = ArrayAdapter.createFromResource(
                            getActivity(), R.array.JeollaS, android.R.layout.simple_spinner_item);
                    subRegionFilter.setOnItemSelectedListener(subRegionFilterListenerJeollaS);
                    break;
                case 14: // 전라북도 35
                    regionCodeHead = "35";
                    subRegionFilterAdapter = ArrayAdapter.createFromResource(
                            getActivity(), R.array.JeollaN, android.R.layout.simple_spinner_item);
                    subRegionFilter.setOnItemSelectedListener(subRegionFilterListenerJeollaN);
                    break;
                case 15: // 제주 39
                    regionCodeHead = "39";
                    subRegionFilterAdapter = ArrayAdapter.createFromResource(
                            getActivity(), R.array.Jeju, android.R.layout.simple_spinner_item);
                    subRegionFilter.setOnItemSelectedListener(subRegionFilterListenerJeju);
                    break;
                case 16: // 충청남도 34
                    regionCodeHead = "34";
                    subRegionFilterAdapter = ArrayAdapter.createFromResource(
                            getActivity(), R.array.ChungcheongS, android.R.layout.simple_spinner_item);
                    subRegionFilter.setOnItemSelectedListener(subRegionFilterListenerChungcheongS);
                    break;
                case 17: // 충청북도 33
                    regionCodeHead = "33";
                    subRegionFilterAdapter = ArrayAdapter.createFromResource(
                            getActivity(), R.array.ChungcheongN, android.R.layout.simple_spinner_item);
                    subRegionFilter.setOnItemSelectedListener(subRegionFilterListenerChungcheongN);
                    break;
            }
            subRegionFilterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            subRegionFilter.setAdapter(subRegionFilterAdapter);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            subRegionFilterAdapter = ArrayAdapter.createFromResource(
                    getActivity(), R.array.Empty, android.R.layout.simple_spinner_item);
            subRegionFilterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            subRegionFilter.setOnItemSelectedListener(subRegionFilterListenerEmpty);
            subRegionFilter.setAdapter(subRegionFilterAdapter);
        }
    };

    AdapterView.OnItemSelectedListener subRegionFilterListenerEmpty = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                default: // 전체(선택권X)
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

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
            switch (position) {
                case 0: // 전체
                    break;
                case 1: // 거제시
                    break;
                case 2: // 거창군
                    break;
                case 3: // 고성군
                    break;
                case 4: // 김해시
                    break;
                case 5: // 남해군
                    break;
                case 6: // 밀양시
                    break;
                case 7: // 사천시
                    break;
                case 8: // 산청군
                    break;
                case 9: // 양산시
                    break;
                case 10: // 의령군
                    break;
                case 11: // 진주시
                    break;
                case 12: // 창녕군
                    break;
                case 13: // 창원시
                    break;
                case 14: // 통영시
                    break;
                case 15: // 하동군
                    break;
                case 16: // 함안군
                    break;
                case 17: // 함양군
                    break;
                case 18: // 합천군
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener subRegionFilterListenerGyeongsangN = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0: // 전체
                    break;
                case 1: // 경산시
                    break;
                case 2: // 경주시
                    break;
                case 3: // 고령군
                    break;
                case 4: // 구미시
                    break;
                case 5: // 군위군
                    break;
                case 6: // 김천시
                    break;
                case 7: // 문경시
                    break;
                case 8: // 봉화군
                    break;
                case 9: // 상주시
                    break;
                case 10: // 성주군
                    break;
                case 11: // 안동시
                    break;
                case 12: // 영덕군
                    break;
                case 13: // 영양군
                    break;
                case 14: // 영주시
                    break;
                case 15: // 영천시
                    break;
                case 16: // 예천군
                    break;
                case 17: // 울릉군
                    break;
                case 18: // 울진군
                    break;
                case 19: // 의성군
                    break;
                case 20: // 청도군
                    break;
                case 21: // 청송군
                    break;
                case 22: // 칠곡군
                    break;
                case 23: // 포항시
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener subRegionFilterListenerGwangju = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0: // 전체
                    break;
                case 1: // 광산구
                    break;
                case 2: // 남구
                    break;
                case 3: // 동구
                    break;
                case 4: // 북구
                    break;
                case 5: // 서구
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener subRegionFilterListenerDaegu = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0: // 전체
                    break;
                case 1: // 남구
                    break;
                case 2: // 달서구
                    break;
                case 3: // 달성군
                    break;
                case 4: // 동구
                    break;
                case 5: // 북구
                    break;
                case 6: // 서구
                    break;
                case 7: // 수성구
                    break;
                case 8: // 중구
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener subRegionFilterListenerDaejeon = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0: // 전체
                    break;
                case 1: // 대덕구
                    break;
                case 2: // 동구
                    break;
                case 3: // 서구
                    break;
                case 4: // 유성구
                    break;
                case 5: // 중구
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener subRegionFilterListenerBusan = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0: // 전체
                    break;
                case 1: // 강서구
                    break;
                case 2: // 금정구
                    break;
                case 3: // 기장군
                    break;
                case 4: // 남구
                    break;
                case 5: // 동구
                    break;
                case 6: // 동래구
                    break;
                case 7: // 부산진구
                    break;
                case 8: // 북구
                    break;
                case 9: // 사상구
                    break;
                case 10: // 사하구
                    break;
                case 11: // 서구
                    break;
                case 12: // 수영구
                    break;
                case 13: // 연제구
                    break;
                case 14: // 영도구
                    break;
                case 15: // 중구
                    break;
                case 16: // 해운대구
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener subRegionFilterListenerSeoul = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0: // 전체
                    break;
                case 1: // 강남구
                    break;
                case 2: // 강동구
                    break;
                case 3: // 강북구
                    break;
                case 4: // 강서구
                    break;
                case 5: // 관악구
                    break;
                case 6: // 광진구
                    break;
                case 7: // 구로구
                    break;
                case 8: // 금천구
                    break;
                case 9: // 노원구
                    break;
                case 10: // 도봉구
                    break;
                case 11: // 동대문구
                    break;
                case 12: // 동작구
                    break;
                case 13: // 마포구
                    break;
                case 14: // 서대문구
                    break;
                case 15: // 서초구
                    break;
                case 16: // 성동구
                    break;
                case 17: // 성북구
                    break;
                case 18: // 송파구
                    break;
                case 19: // 양천구
                    break;
                case 20: // 영등포구
                    break;
                case 21: // 용산구
                    break;
                case 22: // 은평구
                    break;
                case 23: // 종로구
                    break;
                case 24: // 중구
                    break;
                case 25: // 중랑구
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener subRegionFilterListenerSejong = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                default: // 전체
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener subRegionFilterListenerUlsan = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0: // 전체
                    break;
                case 1: // 남구
                    break;
                case 2: // 동구
                    break;
                case 3: // 북구
                    break;
                case 4: // 울주군
                    break;
                case 5: // 중구
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener subRegionFilterListenerIncheon = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0: // 전체
                    break;
                case 1: // 강화군
                    break;
                case 2: // 계양구
                    break;
                case 3: // 남동구
                    break;
                case 4: // 동구
                    break;
                case 5: // 미추홀구
                    break;
                case 6: // 부평구
                    break;
                case 7: // 서구
                    break;
                case 8: // 연수구
                    break;
                case 9: // 옹진군
                    break;
                case 10: // 중구
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener subRegionFilterListenerJeollaS = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0: // 전체
                    break;
                case 1: // 강진군
                    break;
                case 2: // 고흥군
                    break;
                case 3: // 곡성군
                    break;
                case 4: // 광양시
                    break;
                case 5: // 구례군
                    break;
                case 6: // 나주시
                    break;
                case 7: // 담양군
                    break;
                case 8: // 목포시
                    break;
                case 9: // 무안군
                    break;
                case 10: // 보성군
                    break;
                case 11: // 순천시
                    break;
                case 12: // 신안군
                    break;
                case 13: // 여수시
                    break;
                case 14: // 영광군
                    break;
                case 15: // 영암군
                    break;
                case 16: // 완도군
                    break;
                case 17: // 장성군
                    break;
                case 18: // 장흥군
                    break;
                case 19: // 진도군
                    break;
                case 20: // 함평군
                    break;
                case 21: // 해남군
                    break;
                case 22: // 화순군
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener subRegionFilterListenerJeollaN = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0: // 전체
                    break;
                case 1: // 고창군
                    break;
                case 2: // 군산시
                    break;
                case 3: // 김제시
                    break;
                case 4: // 남원시
                    break;
                case 5: // 무주군
                    break;
                case 6: // 부안군
                    break;
                case 7: // 순창군
                    break;
                case 8: // 완주군
                    break;
                case 9: // 익산시
                    break;
                case 10: // 임실군
                    break;
                case 11: // 장수군
                    break;
                case 12: // 전주시
                    break;
                case 13: // 정읍시
                    break;
                case 14: // 진안군
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener subRegionFilterListenerJeju = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0: // 전체
                    break;
                case 1: // 서귀포시
                    break;
                case 2: // 제주시
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener subRegionFilterListenerChungcheongS = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0: // 전체
                    break;
                case 1: // 계룡시
                    break;
                case 2: // 공주시
                    break;
                case 3: // 금산군
                    break;
                case 4: // 논산시
                    break;
                case 5: // 당진시
                    break;
                case 6: // 보령시
                    break;
                case 7: // 부여군
                    break;
                case 8: // 서산시
                    break;
                case 9: // 서천군
                    break;
                case 10: // 아산시
                    break;
                case 11: // 예산군
                    break;
                case 12: // 천안시
                    break;
                case 13: // 청양군
                    break;
                case 14: // 태안군
                    break;
                case 15: // 홍성군
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener subRegionFilterListenerChungcheongN = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0: // 전체
                    break;
                case 1: // 괴산군
                    break;
                case 2: // 단양군
                    break;
                case 3: // 보은군
                    break;
                case 4: // 영동군
                    break;
                case 5: // 옥천군
                    break;
                case 6: // 음성군
                    break;
                case 7: // 제천시
                    break;
                case 8: // 증평군
                    break;
                case 9: // 진천군
                    break;
                case 10: // 청주시
                    break;
                case 11: // 충주시
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}