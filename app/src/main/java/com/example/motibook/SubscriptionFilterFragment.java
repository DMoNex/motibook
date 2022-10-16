package com.example.motibook;

import android.Manifest;
import android.accounts.Account;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

import android.content.SharedPreferences;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class SubscriptionFilterFragment extends Fragment implements OnBackPressedListener {

    private Spinner regionFilter;
    private Spinner subRegionFilter;

    ArrayAdapter<CharSequence> regionFilterAdapter;
    ArrayAdapter<CharSequence> subRegionFilterAdapter;

    String regionCodeHead;
    String regionCodeTail;

    GoogleAccountCredential mCredential;
    MainActivity parents;

    int mID = 0;
    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = {CalendarScopes.CALENDAR};

    private Button mBtnCalendarCreate;

    /**
     * Google Calendar API에 접근하기 위해 사용되는 구글 캘린더 API 서비스 객체
     */
    private com.google.api.services.calendar.Calendar mService = null;

    public SubscriptionFilterFragment() {
        // Required empty public constructor
    }

    public SubscriptionFilterFragment(MainActivity main) {
        // Required empty public constructor
        parents = main;
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


        mCredential = GoogleAccountCredential.usingOAuth2(
                        getActivity().getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());

        getResultsFromApi();
    }

    private String getResultsFromApi() {

        if (!isGooglePlayServicesAvailable()) { // Google Play Services를 사용할 수 없는 경우

            acquireGooglePlayServices();
        } else if (mCredential.getSelectedAccountName() == null) { // 유효한 Google 계정이 선택되어 있지 않은 경우

            chooseAccount();
        } else if (!isDeviceOnline()) {    // 인터넷을 사용할 수 없는 경우

            Toast.makeText(parents, "인터넷을 먼저 연결해주세요.", Toast.LENGTH_SHORT).show();
        } else {

            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

            // Google Calendar API 호출
            mService = new com.google.api.services.calendar.Calendar
                    .Builder(transport, jsonFactory, mCredential)
                    .setApplicationName("Google Calendar API Android Quickstart")
                    .build();

            // Toast.makeText(parents, mCredential.getSelectedAccountName() + " 계정의 Google Calendar에 연결되었습니다.", Toast.LENGTH_SHORT).show();

            new MakeRequestTask().execute();
        }

        return null;
    }

    private class MakeRequestTask extends AsyncTask<Void, Void, String> {

        private Exception mLastError = null;
        private MainActivity mActivity;
        List<String> eventStrings = new ArrayList<String>();

        @Override
        protected String doInBackground(Void... params) {
            try {

                if ( mID == 1) {

                    return createCalendar();

                }else if (mID == 2) {

                    return addEvent();
                }
                else if (mID == 3) {

                    return getEvent();
                }

            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }

            return null;
        }

        private String getEvent() throws IOException {

            DateTime now = new DateTime(System.currentTimeMillis());

            String calendarID = getCalendarID("CalendarTitle");
            if ( calendarID == null ){

                return "캘린더를 먼저 생성하세요.";
            }
            Events events = mService.events().list(calendarID)//"primary")
                    .setMaxResults(10)
                    //.setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            List<Event> items = events.getItems();


            for (Event event : items) {

                DateTime start = event.getStart().getDateTime();
                if (start == null) {

                    // 모든 이벤트가 시작 시간을 갖고 있지는 않다. 그런 경우 시작 날짜만 사용
                    start = event.getStart().getDate();
                }


                eventStrings.add(String.format("%s \n (%s)", event.getSummary(), start));
            }
            return eventStrings.size() + "개의 데이터를 가져왔습니다.";
        }

        /*
         * 선택되어 있는 Google 계정에 새 캘린더를 추가한다.
         */
        private String createCalendar() throws IOException {

            String ids = getCalendarID("Motibook");

            if ( ids != null ){

                return "이미 캘린더가 생성되어 있습니다. ";
            }

            // 새로운 캘린더 생성
            com.google.api.services.calendar.model.Calendar calendar = new Calendar();

            // 캘린더의 제목 설정
            calendar.setSummary("Motibook");

            // 캘린더의 시간대 설정
            calendar.setTimeZone("Asia/Seoul");

            // 구글 캘린더에 새로 만든 캘린더를 추가
            Calendar createdCalendar = mService.calendars().insert(calendar).execute();

            // 추가한 캘린더의 ID를 가져옴.
            String calendarId = createdCalendar.getId();

            // 구글 캘린더의 캘린더 목록에서 새로 만든 캘린더를 검색
            CalendarListEntry calendarListEntry = mService.calendarList().get(calendarId).execute();

            // 캘린더의 배경색을 파란색으로 표시  RGB
            calendarListEntry.setBackgroundColor("#0000ff");

            // 변경한 내용을 구글 캘린더에 반영
            CalendarListEntry updatedCalendarListEntry =
                    mService.calendarList()
                            .update(calendarListEntry.getId(), calendarListEntry)
                            .setColorRgbFormat(true)
                            .execute();

            // 새로 추가한 캘린더의 ID를 리턴
            return "캘린더가 생성되었습니다.";
        }

        private String addEvent() {

            String calendarID = getCalendarID("Motibook");

            if ( calendarID == null ){
                return "캘린더를 먼저 생성하세요.";
            }

            Event event = new Event()
                    .setSummary("구글 캘린더 테스트")
                    .setLocation("서울시")
                    .setDescription("캘린더에 이벤트 추가하는 것을 테스트합니다.");


            java.util.Calendar calander;

            calander = java.util.Calendar.getInstance();
            SimpleDateFormat simpledateformat;
            //simpledateformat = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssZ", Locale.KOREA);
            // Z에 대응하여 +0900이 입력되어 문제 생겨 수작업으로 입력
            simpledateformat = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss+09:00", Locale.KOREA);
            String datetime = simpledateformat.format(calander.getTime());

            DateTime startDateTime = new DateTime(datetime);
            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime)
                    .setTimeZone("Asia/Seoul");
            event.setStart(start);

            Log.d( "@@@", datetime );


            DateTime endDateTime = new  DateTime(datetime);
            EventDateTime end = new EventDateTime()
                    .setDateTime(endDateTime)
                    .setTimeZone("Asia/Seoul");
            event.setEnd(end);

            //String[] recurrence = new String[]{"RRULE:FREQ=DAILY;COUNT=2"};
            //event.setRecurrence(Arrays.asList(recurrence));


            try {
                event = mService.events().insert(calendarID, event).execute();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", "Exception : " + e.toString());
            }
            System.out.printf("Event created: %s\n", event.getHtmlLink());
            Log.e("Event", "created : " + event.getHtmlLink());
            String eventStrings = "created : " + event.getHtmlLink();
            return eventStrings;
        }

        //////////////////////////////////////////////////////////////////////////////
    }


    /*
     * 선택되어 있는 Google 계정에 새 캘린더를 추가한다.
     */
    private String createCalendar() throws IOException {

        String ids = getCalendarID("Motibook");

        if ( ids != null ){
            return "이미 캘린더가 생성되어 있습니다. ";
        } else if ( ids == "Exception") {
            return "알 수 없는 이유로 캘린더를 생성할 수 없습니다.";
        }

        // 새로운 캘린더 생성
        com.google.api.services.calendar.model.Calendar calendar = new Calendar();
        // 캘린더의 제목 설정
        calendar.setSummary("Motibook");
        // 캘린더의 시간대 설정
        calendar.setTimeZone("Asia/Seoul");
        // 구글 캘린더에 새로 만든 캘린더를 추가
        Calendar createdCalendar = mService.calendars().insert(calendar).execute();
        // 추가한 캘린더의 ID를 가져옴.
        String calendarId = createdCalendar.getId();
        // 구글 캘린더의 캘린더 목록에서 새로 만든 캘린더를 검색
        CalendarListEntry calendarListEntry = mService.calendarList().get(calendarId).execute();
        // 캘린더의 배경색을 파란색으로 표시  RGB
        calendarListEntry.setBackgroundColor("#0000ff");
        // 변경한 내용을 구글 캘린더에 반영
        CalendarListEntry updatedCalendarListEntry =
                mService.calendarList()
                        .update(calendarListEntry.getId(), calendarListEntry)
                        .setColorRgbFormat(true)
                        .execute();
        // 새로 추가한 캘린더의 ID를 리턴
        return "캘린더가 생성되었습니다.";
    }

    /*
     * 캘린더 이름에 대응하는 캘린더 ID를 리턴
     */
    private String getCalendarID(String calendarTitle){

        String id = null;

        // Iterate through entries in calendar list
        String pageToken = null;
        do {

            com.google.api.services.calendar.model.CalendarList calendarList = null;

            try {
                calendarList = mService.calendarList().list().setPageToken(pageToken).execute();
            } catch (UserRecoverableAuthIOException e) {
                // 사용하지 않는 함수임으로 수정 필요
                startActivityForResult(e.getIntent(), REQUEST_AUTHORIZATION);
            }catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                Toast.makeText(parents, e.toString(), Toast.LENGTH_SHORT).show();
            }
            List<CalendarListEntry> items = calendarList.getItems();


            for (CalendarListEntry calendarListEntry : items) {

                if (calendarListEntry.getSummary().toString().equals(calendarTitle)) {

                    id = calendarListEntry.getId().toString();
                }
            }
            pageToken = calendarList.getNextPageToken();


        } while (pageToken != null);

        return id;
    }



    /**
     * 안드로이드 디바이스에 최신 버전의 Google Play Services가 설치되어 있는지 확인
     */
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        final int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(parents);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    /*
     * Google Play Services 업데이트로 해결가능하다면 사용자가 최신 버전으로 업데이트하도록 유도하기위해
     * 대화상자를 보여줌.
     */
    private void acquireGooglePlayServices() {

        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        final int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(parents);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }

    /*
     * 안드로이드 디바이스에 Google Play Services가 설치 안되어 있거나 오래된 버전인 경우 보여주는 대화상자
     */
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode
    ) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                parents,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES
        );
        dialog.show();
}

    /*
     * Google Calendar API의 자격 증명( credentials ) 에 사용할 구글 계정을 설정한다.
     *
     * 전에 사용자가 구글 계정을 선택한 적이 없다면 다이얼로그에서 사용자를 선택하도록 한다.
     * GET_ACCOUNTS 퍼미션이 필요하다.
     */
    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount() {

        // GET_ACCOUNTS 권한을 가지고 있다면
        if (EasyPermissions.hasPermissions(parents, Manifest.permission.GET_ACCOUNTS)) {

            // MainActivity 에서 로그인한 정보를 가져온다.
            String accountName = parents.acc.getEmail();
            if (parents.acc != null && accountName != null) {

                // 선택된 구글 계정 이름으로 설정한다.
                //mCredential.setSelectedAccountName(accountName);
                mCredential.setSelectedAccount(new Account(accountName, "com.example.motibook"));
                getResultsFromApi();
            }

            // GET_ACCOUNTS 권한을 가지고 있지 않다면
        } else {
            // 사용자에게 GET_ACCOUNTS 권한을 요구하는 다이얼로그를 보여준다.(주소록 권한 요청함)
            EasyPermissions.requestPermissions(
                    parents,
                    "This app needs to access your Google account (via Contacts).",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }

    /*
     * 안드로이드 디바이스가 인터넷 연결되어 있는지 확인한다. 연결되어 있다면 True 리턴, 아니면 False 리턴
     */
    private boolean isDeviceOnline() {

        ConnectivityManager connMgr = (ConnectivityManager) parents.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
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

        mBtnCalendarCreate = (Button) rootView.findViewById(R.id.btnCreateCalendar);


        mBtnCalendarCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mID = 1;           // 캘린더 생성
                // mID = 2;        // 캘린더 이벤트 추가
                // mID = 3;        // 캘린더 이벤트 얻기

                getResultsFromApi();
            }
        });
        return rootView;
    }

    @Override
    public void onBackPressed() {
        MainActivity activity = (MainActivity) getActivity();
        activity.FragmentView(1);
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