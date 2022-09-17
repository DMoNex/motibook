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
                    regionCodeHead = "*"; // Query 할 때 전체검색 해야 함
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
            regionCodeTail = "*";
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
                    regionCodeTail = "*"; // Query 할 때 전체검색 해야 함
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
                    regionCodeTail = "*"; // Query 할 때 전체검색 해야 함
                    break;
                case 1: // 강릉시 030
                    regionCodeTail = "030";
                    break;
                case 2: // 고성군 600
                    regionCodeTail = "600";
                    break;
                case 3: // 동해시 040
                    regionCodeTail = "040";
                    break;
                case 4: // 삼척시 070
                    regionCodeTail = "070";
                    break;
                case 5: // 속초시 060
                    regionCodeTail = "060";
                    break;
                case 6: // 양구군 580
                    regionCodeTail = "580";
                    break;
                case 7: // 양양군 610
                    regionCodeTail = "610";
                    break;
                case 8: // 영월군 530
                    regionCodeTail = "530";
                    break;
                case 9: // 원주시 020
                    regionCodeTail = "020";
                    break;
                case 10: // 인제군 590
                    regionCodeTail = "590";
                    break;
                case 11: // 정선군 550
                    regionCodeTail = "550";
                    break;
                case 12: // 철원군 560
                    regionCodeTail = "560";
                    break;
                case 13: // 춘천시 010
                    regionCodeTail = "010";
                    break;
                case 14: // 태백시 050
                    regionCodeTail = "050";
                    break;
                case 15: // 평창군 540
                    regionCodeTail = "540";
                    break;
                case 16: // 홍천군 510
                    regionCodeTail = "510";
                    break;
                case 17: // 화천군 570
                    regionCodeTail = "570";
                    break;
                case 18: // 횡성군 520
                    regionCodeTail = "520";
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
                    regionCodeTail = "*"; // Query 할 때 전체검색 해야 함
                    break;
                case 1: // 가평군 570
                    regionCodeTail = "570";
                    break;
                case 2: // 고양시 100
                    regionCodeTail = "100";
                    break;
                case 3: // 과천시 110
                    regionCodeTail = "110";
                    break;
                case 4: // 광명시 060
                    regionCodeTail = "060";
                    break;
                case 5: // 광주시 250
                    regionCodeTail = "250";
                    break;
                case 6: // 구리시 120
                    regionCodeTail = "120";
                    break;
                case 7: // 군포시 160
                    regionCodeTail = "160";
                    break;
                case 8: // 김포시 230
                    regionCodeTail = "230";
                    break;
                case 9: // 남양주시 130
                    regionCodeTail = "130";
                    break;
                case 10: // 동두천시 080
                    regionCodeTail = "080";
                    break;
                case 11: // 부천시 050
                    regionCodeTail = "050";
                    break;
                case 12: // 성남시
                    regionCodeTail = "020";
                    break;
                case 13: // 수원시 010
                    regionCodeTail = "010";
                    break;
                case 14: // 시흥시 150
                    regionCodeTail = "150";
                    break;
                case 15: // 안산시 090
                    regionCodeTail = "090";
                    break;
                case 16: // 안성시 220
                    regionCodeTail = "220";
                    break;
                case 17: // 안양시 040
                    regionCodeTail = "040";
                    break;
                case 18: // 양주시 260
                    regionCodeTail = "260";
                    break;
                case 19: // 양평군 580
                    regionCodeTail = "580";
                    break;
                case 20: // 여주시 280
                    regionCodeTail = "280";
                    break;
                case 21: // 연천군 550
                    regionCodeTail = "550";
                    break;
                case 22: // 오산시 140
                    regionCodeTail = "140";
                    break;
                case 23: // 용인시 190
                    regionCodeTail = "190";
                    break;
                case 24: // 의왕시 170
                    regionCodeTail = "170";
                    break;
                case 25: // 의정부시 030
                    regionCodeTail = "030";
                    break;
                case 26: // 이천시 210
                    regionCodeTail = "210";
                    break;
                case 27: // 파주시 200
                    regionCodeTail = "200";
                    break;
                case 28: // 평택시 070
                    regionCodeTail = "070";
                    break;
                case 29: // 포천시 270
                    regionCodeTail = "270";
                    break;
                case 30: // 하남시 180
                    regionCodeTail = "180";
                    break;
                case 31: // 화성시 240
                    regionCodeTail = "240";
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
                    regionCodeTail = "*"; // Query 할 때 전체검색 해야 함
                    break;
                case 1: // 거제시 090
                    regionCodeTail = "090";
                    break;
                case 2: // 거창군 590
                    regionCodeTail = "590";
                    break;
                case 3: // 고성군 540
                    regionCodeTail = "540";
                    break;
                case 4: // 김해시 070
                    regionCodeTail = "070";
                    break;
                case 5: // 남해군 550
                    regionCodeTail = "550";
                    break;
                case 6: // 밀양시 080
                    regionCodeTail = "080";
                    break;
                case 7: // 사천시 060
                    regionCodeTail = "060";
                    break;
                case 8: // 산청군 570
                    regionCodeTail = "570";
                    break;
                case 9: // 양산시 100
                    regionCodeTail = "100";
                    break;
                case 10: // 의령군 510
                    regionCodeTail = "510";
                    break;
                case 11: // 진주시 030
                    regionCodeTail = "030";
                    break;
                case 12: // 창녕군 530
                    regionCodeTail = "530";
                    break;
                case 13: // 창원시 110
                    regionCodeTail = "110";
                    break;
                case 14: // 통영시 050
                    regionCodeTail = "050";
                    break;
                case 15: // 하동군 560
                    regionCodeTail = "560";
                    break;
                case 16: // 함안군 520
                    regionCodeTail = "520";
                    break;
                case 17: // 함양군 580
                    regionCodeTail = "580";
                    break;
                case 18: // 합천군 600
                    regionCodeTail = "600";
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
                    regionCodeTail = "*"; // Query 할 때 전체검색 해야 함
                    break;
                case 1: // 경산시 100
                    regionCodeTail = "100";
                    break;
                case 2: // 경주시 020
                    regionCodeTail = "020";
                    break;
                case 3: // 고령군 570
                    regionCodeTail = "570";
                    break;
                case 4: // 구미시 050
                    regionCodeTail = "050";
                    break;
                case 5: // 군위군 510
                    regionCodeTail = "510";
                    break;
                case 6: // 김천시 030
                    regionCodeTail = "030";
                    break;
                case 7: // 문경시 090
                    regionCodeTail = "090";
                    break;
                case 8: // 봉화군 610
                    regionCodeTail = "610";
                    break;
                case 9: // 상주시 080
                    regionCodeTail = "080";
                    break;
                case 10: // 성주군 580
                    regionCodeTail = "580";
                    break;
                case 11: // 안동시 040
                    regionCodeTail = "040";
                    break;
                case 12: // 영덕군 550
                    regionCodeTail = "550";
                    break;
                case 13: // 영양군 540
                    regionCodeTail = "540";
                    break;
                case 14: // 영주시 060
                    regionCodeTail = "060";
                    break;
                case 15: // 영천시 070
                    regionCodeTail = "070";
                    break;
                case 16: // 예천군 600
                    regionCodeTail = "600";
                    break;
                case 17: // 울릉군 630
                    regionCodeTail = "630";
                    break;
                case 18: // 울진군 620
                    regionCodeTail = "620";
                    break;
                case 19: // 의성군 520
                    regionCodeTail = "520";
                    break;
                case 20: // 청도군 560
                    regionCodeTail = "560";
                    break;
                case 21: // 청송군 530
                    regionCodeTail = "530";
                    break;
                case 22: // 칠곡군 590
                    regionCodeTail = "590";
                    break;
                case 23: // 포항시 010
                    regionCodeTail = "010";
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
                    regionCodeTail = "*"; // Query 할 때 전체검색 해야 함
                    break;
                case 1: // 광산구 050
                    regionCodeTail = "050";
                    break;
                case 2: // 남구 030
                    regionCodeTail = "030";
                    break;
                case 3: // 동구 010
                    regionCodeTail = "010";
                    break;
                case 4: // 북구 040
                    regionCodeTail = "040";
                    break;
                case 5: // 서구 020
                    regionCodeTail = "020";
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
                    regionCodeTail = "*"; // Query 할 때 전체검색 해야 함
                    break;
                case 1: // 남구 040
                    regionCodeTail = "040";
                    break;
                case 2: // 달서구 070
                    regionCodeTail = "070";
                    break;
                case 3: // 달성군 510
                    regionCodeTail = "510";
                    break;
                case 4: // 동구 020
                    regionCodeTail = "020";
                    break;
                case 5: // 북구 050
                    regionCodeTail = "050";
                    break;
                case 6: // 서구 030
                    regionCodeTail = "030";
                    break;
                case 7: // 수성구 060
                    regionCodeTail = "060";
                    break;
                case 8: // 중구 010
                    regionCodeTail = "010";
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
                    regionCodeTail = "*"; // Query 할 때 전체검색 해야 함
                    break;
                case 1: // 대덕구 050
                    regionCodeTail = "050";
                    break;
                case 2: // 동구 010
                    regionCodeTail = "010";
                    break;
                case 3: // 서구 030
                    regionCodeTail = "030";
                    break;
                case 4: // 유성구 040
                    regionCodeTail = "040";
                    break;
                case 5: // 중구 020
                    regionCodeTail = "020";
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
                    regionCodeTail = "*"; // Query 할 때 전체검색 해야 함
                    break;
                case 1: // 강서구 120
                    regionCodeTail = "120";
                    break;
                case 2: // 금정구 110
                    regionCodeTail = "110";
                    break;
                case 3: // 기장군 510
                    regionCodeTail = "510";
                    break;
                case 4: // 남구 070
                    regionCodeTail = "070";
                    break;
                case 5: // 동구 030
                    regionCodeTail = "030";
                    break;
                case 6: // 동래구 060
                    regionCodeTail = "060";
                    break;
                case 7: // 부산진구 050
                    regionCodeTail = "050";
                    break;
                case 8: // 북구 080
                    regionCodeTail = "080";
                    break;
                case 9: // 사상구 150
                    regionCodeTail = "150";
                    break;
                case 10: // 사하구 100
                    regionCodeTail = "100";
                    break;
                case 11: // 서구 020
                    regionCodeTail = "020";
                    break;
                case 12: // 수영구 140
                    regionCodeTail = "140";
                    break;
                case 13: // 연제구 130
                    regionCodeTail = "130";
                    break;
                case 14: // 영도구 040
                    regionCodeTail = "040";
                    break;
                case 15: // 중구 010
                    regionCodeTail = "010";
                    break;
                case 16: // 해운대구 090
                    regionCodeTail = "090";
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
                    regionCodeTail = "*"; // Query 할 때 전체검색 해야 함
                    break;
                case 1: // 강남구 230
                    regionCodeTail = "230";
                    break;
                case 2: // 강동구 250
                    regionCodeTail = "250";
                    break;
                case 3: // 강북구 090
                    regionCodeTail = "090";
                    break;
                case 4: // 강서구 160
                    regionCodeTail = "160";
                    break;
                case 5: // 관악구 210
                    regionCodeTail = "210";
                    break;
                case 6: // 광진구 050
                    regionCodeTail = "050";
                    break;
                case 7: // 구로구 170
                    regionCodeTail = "170";
                    break;
                case 8: // 금천구 180
                    regionCodeTail = "180";
                    break;
                case 9: // 노원구 110
                    regionCodeTail = "110";
                    break;
                case 10: // 도봉구 100
                    regionCodeTail = "100";
                    break;
                case 11: // 동대문구 060
                    regionCodeTail = "060";
                    break;
                case 12: // 동작구 200
                    regionCodeTail = "200";
                    break;
                case 13: // 마포구
                    regionCodeTail = "140";
                    break;
                case 14: // 서대문구 130
                    regionCodeTail = "130";
                    break;
                case 15: // 서초구 220
                    regionCodeTail = "220";
                    break;
                case 16: // 성동구 040
                    regionCodeTail = "040";
                    break;
                case 17: // 성북구 080
                    regionCodeTail = "080";
                    break;
                case 18: // 송파구 240
                    regionCodeTail = "240";
                    break;
                case 19: // 양천구 150
                    regionCodeTail = "150";
                    break;
                case 20: // 영등포구 190
                    regionCodeTail = "190";
                    break;
                case 21: // 용산구 030
                    regionCodeTail = "030";
                    break;
                case 22: // 은평구 120
                    regionCodeTail = "120";
                    break;
                case 23: // 종로구 010
                    regionCodeTail = "010";
                    break;
                case 24: // 중구 020
                    regionCodeTail = "020";
                    break;
                case 25: // 중랑구 070
                    regionCodeTail = "070";
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
                    regionCodeTail = "*"; // Query 할 때 전체검색 해야 함
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
                    regionCodeTail = "*"; // Query 할 때 전체검색 해야 함
                    break;
                case 1: // 남구 020
                    regionCodeTail = "020";
                    break;
                case 2: // 동구 030
                    regionCodeTail = "030";
                    break;
                case 3: // 북구 040
                    regionCodeTail = "040";
                    break;
                case 4: // 울주군 510
                    regionCodeTail = "510";
                    break;
                case 5: // 중구 010
                    regionCodeTail = "010";
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
                    regionCodeTail = "*"; // Query 할 때 전체검색 해야 함
                    break;
                case 1: // 강화군 510
                    regionCodeTail = "510";
                    break;
                case 2: // 계양구 070
                    regionCodeTail = "070";
                    break;
                case 3: // 남동구 050
                    regionCodeTail = "050";
                    break;
                case 4: // 동구 020
                    regionCodeTail = "020";
                    break;
                case 5: // 미추홀구 090
                    regionCodeTail = "090";
                    break;
                case 6: // 부평구 060
                    regionCodeTail = "060";
                    break;
                case 7: // 서구 080
                    regionCodeTail = "080";
                    break;
                case 8: // 연수구 040
                    regionCodeTail = "040";
                    break;
                case 9: // 옹진군 520
                    regionCodeTail = "520";
                    break;
                case 10: // 중구 010
                    regionCodeTail = "010";
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
                    regionCodeTail = "*"; // Query 할 때 전체검색 해야 함
                    break;
                case 1: // 강진군 590
                    regionCodeTail = "590";
                    break;
                case 2: // 고흥군 550
                    regionCodeTail = "550";
                    break;
                case 3: // 곡성군 520
                    regionCodeTail = "520";
                    break;
                case 4: // 광양시 060
                    regionCodeTail = "060";
                    break;
                case 5: // 구례군 530
                    regionCodeTail = "530";
                    break;
                case 6: // 나주시 040
                    regionCodeTail = "040";
                    break;
                case 7: // 담양군 510
                    regionCodeTail = "510";
                    break;
                case 8: // 목포시 010
                    regionCodeTail = "010";
                    break;
                case 9: // 무안군 620
                    regionCodeTail = "620";
                    break;
                case 10: // 보성군 560
                    regionCodeTail = "560";
                    break;
                case 11: // 순천시 030
                    regionCodeTail = "030";
                    break;
                case 12: // 신안군 680
                    regionCodeTail = "680";
                    break;
                case 13: // 여수시 020
                    regionCodeTail = "020";
                    break;
                case 14: // 영광군 640
                    regionCodeTail = "640";
                    break;
                case 15: // 영암군 610
                    regionCodeTail = "610";
                    break;
                case 16: // 완도군 660
                    regionCodeTail = "660";
                    break;
                case 17: // 장성군 650
                    regionCodeTail = "650";
                    break;
                case 18: // 장흥군 580
                    regionCodeTail = "580";
                    break;
                case 19: // 진도군 670
                    regionCodeTail = "670";
                    break;
                case 20: // 함평군 630
                    regionCodeTail = "630";
                    break;
                case 21: // 해남군 600
                    regionCodeTail = "600";
                    break;
                case 22: // 화순군 570
                    regionCodeTail = "570";
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
                    regionCodeTail = "*"; // Query 할 때 전체검색 해야 함
                    break;
                case 1: // 고창군 570
                    regionCodeTail = "570";
                    break;
                case 2: // 군산시 020
                    regionCodeTail = "020";
                    break;
                case 3: // 김제시 060
                    regionCodeTail = "060";
                    break;
                case 4: // 남원시 050
                    regionCodeTail = "050";
                    break;
                case 5: // 무주군 530
                    regionCodeTail = "530";
                    break;
                case 6: // 부안군 580
                    regionCodeTail = "580";
                    break;
                case 7: // 순창군 560
                    regionCodeTail = "560";
                    break;
                case 8: // 완주군 510
                    regionCodeTail = "510";
                    break;
                case 9: // 익산시 030
                    regionCodeTail = "030";
                    break;
                case 10: // 임실군 550
                    regionCodeTail = "550";
                    break;
                case 11: // 장수군 540
                    regionCodeTail = "540";
                    break;
                case 12: // 전주시 010
                    regionCodeTail = "010";
                    break;
                case 13: // 정읍시 040
                    regionCodeTail = "040";
                    break;
                case 14: // 진안군 520
                    regionCodeTail = "520";
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
                    regionCodeTail = "*"; // Query 할 때 전체검색 해야 함
                    break;
                case 1: // 서귀포시 020
                    regionCodeTail = "020";
                    break;
                case 2: // 제주시 010
                    regionCodeTail = "010";
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
                    regionCodeTail = "*"; // Query 할 때 전체검색 해야 함
                    break;
                case 1: // 계룡시 070
                    regionCodeTail = "070";
                    break;
                case 2: // 공주시 020
                    regionCodeTail = "020";
                    break;
                case 3: // 금산군 510
                    regionCodeTail = "510";
                    break;
                case 4: // 논산시 060
                    regionCodeTail = "060";
                    break;
                case 5: // 당진시 080
                    regionCodeTail = "080";
                    break;
                case 6: // 보령시 030
                    regionCodeTail = "030";
                    break;
                case 7: // 부여군 530
                    regionCodeTail = "530";
                    break;
                case 8: // 서산시 050
                    regionCodeTail = "050";
                    break;
                case 9: // 서천군 540
                    regionCodeTail = "540";
                    break;
                case 10: // 아산시 040
                    regionCodeTail = "040";
                    break;
                case 11: // 예산군 570
                    regionCodeTail = "570";
                    break;
                case 12: // 천안시 010
                    regionCodeTail = "010";
                    break;
                case 13: // 청양군 550
                    regionCodeTail = "550";
                    break;
                case 14: // 태안군 580
                    regionCodeTail = "580";
                    break;
                case 15: // 홍성군 560
                    regionCodeTail = "560";
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
                    regionCodeTail = "*"; // Query 할 때 전체검색 해야 함
                    break;
                case 1: // 괴산군 560
                    regionCodeTail = "560";
                    break;
                case 2: // 단양군 580
                    regionCodeTail = "580";
                    break;
                case 3: // 보은군 520
                    regionCodeTail = "520";
                    break;
                case 4: // 영동군 540
                    regionCodeTail = "540";
                    break;
                case 5: // 옥천군 530
                    regionCodeTail = "530";
                    break;
                case 6: // 음성군 570
                    regionCodeTail = "570";
                    break;
                case 7: // 제천시 030
                    regionCodeTail = "030";
                    break;
                case 8: // 증평군 590
                    regionCodeTail = "590";
                    break;
                case 9: // 진천군 550
                    regionCodeTail = "550";
                    break;
                case 10: // 청주시 040
                    regionCodeTail = "040";
                    break;
                case 11: // 충주시 020
                    regionCodeTail = "020";
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}