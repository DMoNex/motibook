package com.example.motibook;

import android.Manifest;
import android.accounts.Account;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class EventListFragment extends Fragment implements OnBackPressedListener {
    MainActivity mainAct;
    private RecyclerView eventList;
    private ArrayList<EventListItem> eventListItems;

    GoogleAccountCredential mCredential;
    private String eventName = "";
    private String eventLocation = ""; //v
    private String eventDescription = ""; //v
    private DateTime eventDateStartTime; //v
    private DateTime eventDateEndTime; //v

    static final int REQUEST_ACCOUNT_PICKER = 1000; //v
    static final int REQUEST_AUTHORIZATION = 1001; //v
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002; //v
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003; //v

    private static final String PREF_ACCOUNT_NAME = "accountName"; //v
    private static final String[] SCOPES = {CalendarScopes.CALENDAR}; //v

    // Google Calendar API에 접근하기 위해 사용되는 구글 캘린더 API 서비스 객체
    private com.google.api.services.calendar.Calendar mService;

    public EventListFragment() {
        // Required empty public constructor
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

    public static EventListFragment newInstance(String param1, String param2) {
        EventListFragment fragment = new EventListFragment();
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

        getResultsFromApi(1);// mID 1 :: Calendar 생성
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_event_list, container, false);

        eventListItems = mainAct.eventListItems;

        eventList = (RecyclerView) rootView.findViewById(R.id.eventSearchListView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        eventList.setLayoutManager(linearLayoutManager);

        EventListItemAdapter eventListItemAdapter = new EventListItemAdapter(eventListItems);
        eventListItemAdapter.setOnItemClickListener(new EventListItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Add Calendar?");
                builder.setMessage(String.format("이 행사를 달력에 추가하시겠습니까?\n\"%s\"", eventListItems.get(pos).getEventName()));
                String[] typeConvert = new String[4];


                builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
                    //예 눌렀을때의 이벤트 처리
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 구글의 DateTime은 rfc3339 포맷을 사용
                        // date 는 "yyyy-MM-ddTHH:mm:ss" 와 같은 문자열
                        setEvent(eventListItems.get(pos).getEventName(),             // 제목
                                eventListItems.get(pos).getStrAddress() +    // 장소
                                        eventListItems.get(pos).getLocate(),
                                "춘천시에서 주관하는 대양도서관 바자회입니다.",   // 설명
                                eventListItems.get(pos).getRFCTime_Start(),
                                eventListItems.get(pos).getRFCTime_End());          // 날짜

                        Toast.makeText(getActivity(), "행사가 등록되었습니다.", Toast.LENGTH_LONG).show();
                    }
                });

                builder.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                    //아니오 눌렀을때의 이벤트 처리
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

                // builder 화면에 출력
                builder.show();
            }
        }); // setOnItemClickListener End

        eventList.setAdapter(eventListItemAdapter);

        /*
        mBtnSubmitQuery.setOnClickListener(new View.OnClickListener() { //v
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Submit:" + regionCodeHead + regionCodeTail + eventNameFilter.getText() + queryEventType, Toast.LENGTH_SHORT).show();
                // TODO : DB로 쿼리 전송 후 받아오기.

                // 캘린더에 일정 추가
                {
                    mID = 2;
                    // 구글의 DateTime은 rfc3339 포맷을 사용
                    // date 는 "yyyy-MM-ddTHH:mm:ss" 와 같은 문자열으로 넣어주면 된다.


                    getResultsFromApi();
                }

                //캘린더에 있는 모든 일정 얻기
                {
                    mID = 3;
                    getResultsFromApi();
                    // 얻은 이벤트는 onPostExecute() 에서 eventStrings 으로 작업
                    // 혹은 getEvent에서 추가로 아이템을 불러와서 작업
                }
            }
        });
*/


        return rootView;
    }

    private String getResultsFromApi(int mID) {
        if (!isGooglePlayServicesAvailable()) { // Google Play Services를 사용할 수 없는 경우
            acquireGooglePlayServices();
        } else if (mCredential.getSelectedAccountName() == null) { // 유효한 Google 계정이 선택되어 있지 않은 경우
            chooseAccount();
        } else if (!isDeviceOnline()) {    // 인터넷을 사용할 수 없는 경우
            Toast.makeText(mainAct, "인터넷을 먼저 연결해주세요.", Toast.LENGTH_SHORT).show();
        } else {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

            // Google Calendar API 호출
            mService = new com.google.api.services.calendar.Calendar
                    .Builder(transport, jsonFactory, mCredential)
                    .setApplicationName("Google Calendar API Android Quickstart")
                    .build();

            // Toast.makeText(parents, mCredential.getSelectedAccountName() + " 계정의 Google Calendar에 연결되었습니다.", Toast.LENGTH_SHORT).show();

            new EventListFragment.MakeRequestTask(mID).execute();
        }
        return null;
    }

    private class MakeRequestTask extends AsyncTask<Void, Void, String> { //v
        private Exception mLastError;
        private int mID;
        List<String> eventStrings = new ArrayList<String>();

        MakeRequestTask() {
            this.mLastError = null;
            this.mID = 0;
        }

        MakeRequestTask(int mID) {
            this.mLastError = null;
            this.mID = mID;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                if (mID == 1) {
                    return createCalendar();
                } else if (mID == 2) {
                    return addEvent();
                } else if (mID == 3) {
                    return getEvent();
                }
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(String output) {
            if (mID == 3) {
                // 여기서 ArrayList, eventStrings 으로 추가 작업
            }
        }

        private String getEvent() throws IOException {
            DateTime now = new DateTime(System.currentTimeMillis());

            String calendarID = getCalendarID("CalendarTitle");
            if (calendarID == null) {
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

                String sum = event.getSummary(); // 이벤트 제목
                String loc = event.getLocation(); // 이벤트 장소
                String des = event.getDescription(); // 이벤트 설명
                String time = start.toStringRfc3339(); // 이벤트 시간

                eventStrings.add(String.format("%s\n%s\n장소: %s\n시간: ", sum, des, loc, time));
            }

            return eventStrings.size() + "개의 데이터를 가져왔습니다.";
        }

        // 선택되어 있는 Google 계정에 새 캘린더를 추가한다.
        private String createCalendar() throws IOException {
            String ids = getCalendarID("Motibook");

            if (ids != null) {
                return "이미 캘린더가 생성되어 있습니다.";
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

            // 캘린더의 배경색을 녹색으로 표시  RGB
            calendarListEntry.setBackgroundColor("#99FFCC");

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

            if (calendarID == null) {
                return "캘린더를 먼저 생성하세요.";
            }

            Event event = new Event()
                    .setSummary(eventName)
                    .setLocation(eventLocation)
                    .setDescription(eventDescription);

            java.util.Calendar calander;

            DateTime startDateTime = eventDateStartTime;
            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime)
                    .setTimeZone("Asia/Seoul");
            event.setStart(start);


            DateTime endDateTime = eventDateEndTime;
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

    private void setEvent(String summary, String location, String description, String startDate, String endDate) { //v
        eventName = summary;
        eventLocation = location;
        eventDescription = description;

        // 구글의 DateTime은 rfc3339 포맷을 사용
        // date 는 "yyyy-MM-ddTHH:mm:ss" 와 같은 문자열으로 넣어주면 된다.
        //eventDateTime = new DateTime(date + "+09:00");

        return;
    }

    // 선택되어 있는 Google 계정에 새 캘린더를 추가한다.
    private String createCalendar() throws IOException { //v

        String ids = getCalendarID("Motibook");

        if (ids != null) {
            return "이미 캘린더가 생성되어 있습니다. ";
        } else if (ids == "Exception") {
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

    // 캘린더 이름에 대응하는 캘린더 ID를 리턴
    private String getCalendarID(String calendarTitle) { //v
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
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                Toast.makeText(mainAct, e.toString(), Toast.LENGTH_SHORT).show();
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

    // 안드로이드 디바이스에 최신 버전의 Google Play Services가 설치되어 있는지 확인
    private boolean isGooglePlayServicesAvailable() { //v
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        final int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(mainAct);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    // Google Play Services 업데이트로 해결가능하다면 사용자가 최신 버전으로 업데이트하도록 유도하기위해 대화상자를 보여줌.
    private void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        final int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(mainAct);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }

    // 안드로이드 디바이스에 Google Play Services가 설치 안되어 있거나 오래된 버전인 경우 보여주는 대화상자
    void showGooglePlayServicesAvailabilityErrorDialog(final int connectionStatusCode) { //v
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                mainAct,
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
    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS) //v
    private void chooseAccount() { //v
        // GET_ACCOUNTS 권한을 가지고 있다면
        if (EasyPermissions.hasPermissions(mainAct, Manifest.permission.GET_ACCOUNTS)) {
            // MainActivity 에서 로그인한 정보를 가져온다.
            String accountName = mainAct.acc.getEmail();
            if (mainAct.acc != null && accountName != null) {
                // 선택된 구글 계정 이름으로 설정한다.
                //mCredential.setSelectedAccountName(accountName);
                mCredential.setSelectedAccount(new Account(accountName, "com.example.motibook"));
                getResultsFromApi(1);
            }

            // GET_ACCOUNTS 권한을 가지고 있지 않다면
        } else {
            // 사용자에게 GET_ACCOUNTS 권한을 요구하는 다이얼로그를 보여준다.(주소록 권한 요청함)
            EasyPermissions.requestPermissions(
                    mainAct,
                    "This app needs to access your Google account (via Contacts).",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }

    // 안드로이드 디바이스가 인터넷 연결되어 있는지 확인한다. 연결되어 있다면 True 리턴, 아니면 False 리턴
    private boolean isDeviceOnline() { //v
        ConnectivityManager connMgr = (ConnectivityManager) mainAct.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public void onBackPressed() {
        MainActivity activity = (MainActivity) getActivity();
        activity.FragmentView(3);
    }
}