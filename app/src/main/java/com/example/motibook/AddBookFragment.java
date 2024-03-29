package com.example.motibook;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class AddBookFragment extends Fragment implements OnBackPressedListener {
    private Spinner filter;
    private SearchView bookSearch;
    private RecyclerView bookList;
    private ArrayList<BookListItem> bookListItems;
    int bookSearchFlag = 0;

    InputMethodManager imm;
    BookListItemAdapter bookListItemAdapter;

    String mKwd = "";
    int pageNum = 1; // 현재 페이지
    String KEY_STRING = "0b8f384d2efa7df3d0321ae312e0d8074724e887fab112cd8b870be0f5973d89"; // 키
    String sendQuery = "";

    final int MESSAGE_ID_LIST_UPDATE = 1;

    Handler handler;

    public AddBookFragment() {
        // Required empty public constructor
    }

    public static AddBookFragment newInstance(String param1, String param2) {
        AddBookFragment fragment = new AddBookFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what){
                    //msg.what == MESSAGE_ID_CAT 인 경우
                    case MESSAGE_ID_LIST_UPDATE:
                        bookListItemAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_add_book, container, false);

        bookListItems = new ArrayList<BookListItem>();

        filter = (Spinner) rootView.findViewById(R.id.bookSearchFilter);
        bookSearch = (SearchView) rootView.findViewById(R.id.bookSearchView);
        // bookList 멤버는 RecyclerView bookSearchListView 임
        bookList = (RecyclerView) rootView.findViewById(R.id.bookSearchListView);

        bookList.setOnScrollListener (new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (!bookList.canScrollVertically(-1)) {
                    // Top of list
                } else if (!bookList.canScrollVertically(1)) {
                    // End of list
                    pageNum++;
                    searchBookList();
                } else {
                    // idle
                }
            }
        });

        // Filter Array Note Filter Array 공유
        ArrayAdapter<CharSequence> bookFilterAdapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.noteFilterArray, android.R.layout.simple_spinner_item);
        bookFilterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Layout manager 추가
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        bookList.setLayoutManager(linearLayoutManager);

        // Adapter 추가
        bookListItemAdapter = new BookListItemAdapter(bookListItems);
        bookListItemAdapter.setOnItemClickListener(new BookListItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                String isbn = bookListItems.get(pos).getIsbn();
                String bookName = bookListItems.get(pos).getBookName();

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("저장");
                builder.setMessage(String.format("%s 책을 추가하시겠습니까?", bookName));

                builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //예 눌렀을때의 이벤트 처리

                                int dataIndex = bookListItems.get(pos).getKdcCodeLs();

                                int data = ((MainActivity)getActivity()).statisticsData.data[dataIndex] + 1;
                                ((MainActivity)getActivity()).statisticsData.data[dataIndex] += 1;
                                ((MainActivity)getActivity()).statisticsData.totalNumUpdate();

                                String notepath = new String(getActivity().getFilesDir().toString() + "/notes");
                                File noteDir = new File(notepath);
                                if(!noteDir.exists()) {
                                    noteDir.mkdir();
                                }
                                File noteFile = new File(noteDir + "/0#&#" + isbn + "#&#" + bookName + ".txt");
                                String noteFiles[] = noteDir.list();
                                boolean check = false;

                                for (String fileN : noteFiles) {
                                    if (fileN.contains(isbn)) {
                                        check = true;
                                    }
                                }

                                // noteFile이 존재한다면 이미 추가했던 책이므로 아무 동작도 하지 않아야 함
                                if(check) {
                                    Toast.makeText(getActivity(), String.format("이미 등록된 도서입니다."), Toast.LENGTH_LONG).show();
                                }
                                else { // noteFile.txt 생성 (empty)
                                    try {
                                        noteFile.createNewFile();

                                        String filepath = new String(getActivity().getFilesDir().toString() + "/stastics");
                                        File statDir = new File(filepath);
                                        // 하위폴더 미존재시 생성
                                        if(!statDir.exists()) {
                                            statDir.mkdir();
                                        }
                                        File statFile = new File(statDir + "/StatisticsFile.txt");

                                        try {
                                            statFile.createNewFile();
                                            FileWriter fw = new FileWriter(statFile);
                                            fw.write(String.format("%d\n%d\n%d\n%d\n%d\n%d\n%d\n%d\n%d\n%d\n",
                                                    ((MainActivity)getActivity()).statisticsData.data[0],
                                                    ((MainActivity)getActivity()).statisticsData.data[1],
                                                    ((MainActivity)getActivity()).statisticsData.data[2],
                                                    ((MainActivity)getActivity()).statisticsData.data[3],
                                                    ((MainActivity)getActivity()).statisticsData.data[4],
                                                    ((MainActivity)getActivity()).statisticsData.data[5],
                                                    ((MainActivity)getActivity()).statisticsData.data[6],
                                                    ((MainActivity)getActivity()).statisticsData.data[7],
                                                    ((MainActivity)getActivity()).statisticsData.data[8],
                                                    ((MainActivity)getActivity()).statisticsData.data[9]));
                                            fw.flush();
                                            fw.close();
                                        } catch (IOException e) {
                                            System.out.println (e.toString());
                                        }
                                        Toast.makeText(getActivity(), "책이 등록되었습니다.", Toast.LENGTH_LONG).show();

                                    } catch (IOException e) {
                                        System.out.println (e.toString());
                                    }
                                }
                            }
                        });

                builder.setPositiveButton("아니오",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //아니오 눌렀을때의 이벤트 처리
                                return;
                            }
                        });

                builder.show();
            }
        });

        filter.setAdapter(bookFilterAdapter);
        filter.setOnItemSelectedListener(bookFilterListener);

        // noteList 와 Adapter 연결
        bookList.setAdapter(bookListItemAdapter);

        bookSearch.setOnQueryTextListener(bookSearchListener);

        return rootView;
    }

    AdapterView.OnItemSelectedListener bookFilterListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0: // SearchView 에서 ISBN 으로 검색
                    bookSearchFlag = 0;
                case 1: // SearchView 에서 제목으로 검색
                    bookSearchFlag = 1;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    SearchView.OnQueryTextListener bookSearchListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            try {
                pageNum = 1;
                bookListItems.clear();

                bookSearchFlag = filter.getSelectedItemPosition();
                mKwd = URLEncoder.encode(query.trim(), "utf-8");

                searchBookList();
                // 키보드 숨김
                imm.hideSoftInputFromWindow(bookSearch.getWindowToken(), 0);
            } catch (Exception e) {
                Toast.makeText(getActivity(), String.format("조건이 올바르지 않습니다."), Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            pageNum = 1;
            bookListItems.clear();
            return false;
        }
    };

    public void searchBookList() {
        if (bookSearchFlag == 0) {
            // 요청 쿼리의 포맷은 https://www.nl.go.kr/NL/contents/N31101030700.do 참조

            String category = "";

            try {
                category = URLEncoder.encode("도서", "utf-8");
            } catch(Exception e) {
            }

            sendQuery = String.format("https://www.nl.go.kr/NL/search/openApi/search.do?key=%s&apiType=xml&srchTarget=total&kwd=%s&pageSize=20&pageNum=%s&category=%s&sort=&srchTarget=title",
                    KEY_STRING,
                    mKwd,
                    pageNum,
                    category);

        } else if (bookSearchFlag == 1) {
            sendQuery = String.format("https://www.nl.go.kr/NL/search/openApi/search.do?key=%s&detailSearch=true&isbnOp=isbn&isbnCode=%s",
                    KEY_STRING,
                    mKwd);
        }

        new AddBookFragment.MakeRequestTask().execute();
    }

    @Override
    public void onBackPressed() {
        MainActivity activity = (MainActivity) getActivity();
        activity.FragmentView(1);
    }

    private String replaceHTMLTags (String str) {

        str = str.replace("&lt;", "<");
        str = str.replace("&gt;", ">");
        str = str.replace("&gt;", ">");
        str = str.replace("&amp;", "&");
        str = str.replace("&nbsp;", " ");
        str = str.replace("&quot;", "\"");
        str = str.replace("&#035;", "#");
        str = str.replace("&#039;", "\'");

        return str;
    }

    private class MakeRequestTask extends AsyncTask<Void, Void, String> {

        private Exception mLastError = null;
        private MainActivity mActivity;
        List<String> eventStrings = new ArrayList<String>();

        @Override
        protected String doInBackground(Void... params) {
            try {

                if (!sendQuery.isEmpty()) {
                    URL url = new URL(sendQuery);
                    InputStream is = url.openStream();
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    XmlPullParser xpp = factory.newPullParser();
                    //inputstream 으로부터 xml 입력받기
                    xpp.setInput(new InputStreamReader(is, "UTF-8"));
                    String tag;

                    String total_num = "";
                    String title_info = "";
                    String author_info = "";
                    String pub_name = "";
                    String image_url = "";
                    String type_name = "";
                    String isbn = "";
                    String detail_link = "";
                    String kdc_name_1s = "";
                    int kdc_code_1s = -1;
                    String class_no = "";
                    xpp.next();
                    int eventType = xpp.getEventType();

                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:
                                tag = xpp.getName();
                                if (tag.equals("item")) {
                                } else if (tag.equals("title_info")) {  // 제목
                                    xpp.next();
                                    title_info = xpp.getText();
                                    title_info = replaceHTMLTags(title_info);
                                } else if (tag.equals("author_info")) {  // 저자
                                    xpp.next();
                                    author_info = xpp.getText();
                                    author_info = replaceHTMLTags(author_info);
                                } else if (tag.equals("pub_info")) { // 출판사
                                    xpp.next();
                                    pub_name = xpp.getText();
                                } else if (tag.equals("image_url")) { // 이미지 url
                                    xpp.next();
                                    image_url = xpp.getText();
                                } else if (tag.equals("type_name")) { // 도서, 신문, 음악자료 등
                                    xpp.next();
                                    type_name = xpp.getText();
                                } else if (tag.equals("isbn")) { // ISBN
                                    xpp.next();
                                    isbn = xpp.getText();
                                } else if (tag.equals("detail_link")) { // 추가 정보 링크
                                    xpp.next();
                                    detail_link = "https://www.nl.go.kr/" + xpp.getText();
                                } else if (tag.equals("kdc_name_1s")) { // 문학, 예술 등
                                    xpp.next();
                                    kdc_name_1s = xpp.getText();
                                } else if (tag.equals("kdc_code_1s")) { // 8(문학), 6(예술) 등
                                    xpp.next();
                                    try{
                                        kdc_code_1s = Integer.parseInt((xpp.getText()));
                                    } catch (Exception e) {
                                        kdc_code_1s = -1;
                                    }
                                } else if (tag.equals("class_no")) { // 813.6, 668.4 등
                                    xpp.next();
                                    class_no = xpp.getText();
                                }


                                // 도서 정보가 아닌 페이지의 패러미터에서 가져오는 반환값
                                else if (tag.equals("total")) { // 총 검색 갯수
                                    xpp.next();
                                    total_num = xpp.getText();
                                }
                                //else if (tag.equals("category")) { // 현재 검색한 카테고리
                                //    xpp.next();
                                //    category = xpp.getText();
                                //}
                                break;
                            case XmlPullParser.END_TAG:
                                tag = xpp.getName();
                                if (tag.equals("item") && (isbn != null)) {
                                    // 여기서 각 지역변수 가지고 list에 item 추가,
                                    bookListItems.add(new BookListItem(author_info + " - " + pub_name, title_info, isbn, kdc_code_1s));
                                    Log.v("bookListAdd:" , author_info + " - " + pub_name + ", " + title_info + ", " + isbn + ", " + kdc_code_1s);
                                }
                                break;
                        }

                        eventType = xpp.next();
                    }
                }

            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }

            //메인스레드 핸들러의 메시지 객체 가져오기
            Message msg = handler.obtainMessage();
            // 메시지 아이디 설정
            msg.what = MESSAGE_ID_LIST_UPDATE;
            // 메인스레드 핸들러로 메시지 보내기
            handler.sendMessage(msg);

            return null;
        }

    }
}