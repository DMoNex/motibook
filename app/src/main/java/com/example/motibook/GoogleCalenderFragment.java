package com.example.motibook;


import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;

import java.text.SimpleDateFormat;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoogleCalenderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
// Activity 기준으로 작업하였으나 Fragment와 호환되지 않아 재작업
public class GoogleCalenderFragment extends Fragment {
    private CalendarView mCalendarView;
    private Button mLoadCalenderBtn;
    private Button mInsertCalenderBtn;
    GoogleAccountCredential mCredential;

    private ViewGroup rootView;

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = {CalendarScopes.CALENDAR};

    public GoogleCalenderFragment() {
        // Required empty public constructor
    }

    public static GoogleCalenderFragment newInstance(String param1, String param2) {
        GoogleCalenderFragment fragment = new GoogleCalenderFragment();
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
        // Inflate the layout for this fragment
        rootView = (ViewGroup)inflater.inflate(R.layout.fragment_statistics, container, false);


        mCalendarView = (CalendarView) rootView.findViewById(R.id.calendarView);
        mLoadCalenderBtn = (Button) rootView.findViewById(R.id.btnLoadCalender);
        mInsertCalenderBtn = (Button) rootView.findViewById(R.id.btnInsert);

        /*
        mLoadCalenderBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //getResultsFromApi();
            }
        });*/
        return rootView;
    }

    /**
     * 다음 사전 조건을 모두 만족해야 Google Calendar API를 사용할 수 있다.
     * <p>
     * 사전 조건
     * - Google Play Services 설치
     * - 유효한 구글 계정 선택
     * - 안드로이드 디바이스에서 인터넷 사용 가능
     * <p>
     * 하나라도 만족하지 않으면 해당 사항을 사용자에게 알림.
     */
    /*
    private String getResultsFromApi() {

        if (!isGooglePlayServicesAvailable()) { // Google Play Services를 사용할 수 없는 경우

            acquireGooglePlayServices();
        } /*else if (!isDeviceOnline()) {    // 인터넷을 사용할 수 없는 경우

        } else {

            // Google Calendar API 호출
            //new MakeRequestTask(this.mainActivity, mCredential).execute();
        }
        return null;
    }*/

    /**
     * 안드로이드 디바이스에 최신 버전의 Google Play Services가 설치되어 있는지 확인

    private boolean isGooglePlayServicesAvailable() {

//        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();

//        final int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(this);
//        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    /*
     * Google Play Services 업데이트로 해결가능하다면 사용자가 최신 버전으로 업데이트하도록 유도하기위해
     * 대화상자를 보여줌.
    private void acquireGooglePlayServices() {

        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        final int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {

            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }
     */

    /*
     * 안드로이드 디바이스에 Google Play Services가 설치 안되어 있거나 오래된 버전인 경우 보여주는 대화상자
     */
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode
    ) {


    }


    /*
     * 안드로이드 디바이스가 인터넷 연결되어 있는지 확인한다. 연결되어 있다면 True 리턴, 아니면 False 리턴
     */
    private void isDeviceOnline() {

//        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

//        return (networkInfo != null && networkInfo.isConnected());
    }


    /*
     * 비동기적으로 Google Calendar API 호출

    private class MakeRequestTask extends AsyncTask<Void, Void, String> {

        private Exception mLastError = null;
        private MainActivity mActivity;
        List<String> eventStrings = new ArrayList<String>();


        public MakeRequestTask(MainActivity activity, GoogleAccountCredential credential) {

            mActivity = activity;

            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

            mService = new com.google.api.services.calendar.Calendar
                    .Builder(transport, jsonFactory, credential)
                    .setApplicationName("Google Calendar API Android Quickstart")
                    .build();
        }


        @Override
        protected void onPreExecute() {
            // mStatusText.setText("");
            mProgress.show();
            mStatusText.setText("데이터 가져오는 중...");
            mResultText.setText("");
        }*/


        /*
         * 백그라운드에서 Google Calendar API 호출 처리

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
        }*/


        /*
         * CalendarTitle 이름의 캘린더에서 10개의 이벤트를 가져와 리턴

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
        }*/

        /*
         * 선택되어 있는 Google 계정에 새 캘린더를 추가한다.

        private String createCalendar() throws IOException {

            String ids = getCalendarID("CalendarTitle");

            if ( ids != null ){

                return "이미 캘린더가 생성되어 있습니다. ";
            }

            // 새로운 캘린더 생성
            com.google.api.services.calendar.model.Calendar calendar = new Calendar();

            // 캘린더의 제목 설정
            calendar.setSummary("CalendarTitle");


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
        }*/


    /*
        @Override
        protected void onPostExecute(String output) {

            mProgress.hide();
            mStatusText.setText(output);

            if ( mID == 3 )   mResultText.setText(TextUtils.join("\n\n", eventStrings));
        }


        @Override
        protected void onCancelled() {
            mProgress.hide();
            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            MainActivity.REQUEST_AUTHORIZATION);
                } else {
                    // MakeRequestTask The following error occurred:\n" + mLastError.getMessage()
                }
            } else {
                //요청 취소됨.
            }
        }


        private String addEvent() {


            String calendarID = getCalendarID("CalendarTitle");

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
    }*/

}