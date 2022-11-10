package com.example.motibook;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.media.metrics.Event;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.material.navigation.NavigationView;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public StatisticsData statisticsData;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    String lastFilePath;

    public ArrayList<EventListItem> eventListItems;


    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount acc;

    private static final String TAG = "MainActivity";
    private final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(this);

        FragmentView(1); // 1: Statistics, 2: Note, 3: Subscribe. 4: Calender

        /// Google Login API Start
        // 최초 Default 로그인 객체 Build
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail() // email addresses도 요청함
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);

        // 로그인 이력 있으면 자동 로그인, 없으면 Email 요구
        GoogleSignInAccount gsa = GoogleSignIn.getLastSignedInAccount(MainActivity.this);
        if (gsa != null && gsa.getId() != null) {

        }

        // Login
        signIn();
        /// Google Login API End

        // Statistics Data Setting
        String filepath = new String(this.getFilesDir().toString() + "/stastics");
        File statDir = new File(filepath);
        // 하위폴더 미존재시 생성
        if(!statDir.exists()) {
            statDir.mkdir();
        }
        // 파일 미존재시 생성 후 통계데이터 0으로 초기화
        File statFile = new File(statDir + "/StatisticsFile.txt");
        if(!statFile.exists()) {
            try {
                statFile.createNewFile();
                FileWriter fw = new FileWriter(statFile);
                fw.write("0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n");
                fw.flush();
                fw.close();
            } catch (IOException e) {
            }
        }

        // 파일 읽기
        try {
            BufferedReader fr = new BufferedReader(new FileReader(statFile));
            statisticsData = new StatisticsData(
                    Integer.parseInt(fr.readLine()), // 0
                    Integer.parseInt(fr.readLine()), // 1
                    Integer.parseInt(fr.readLine()), // 2
                    Integer.parseInt(fr.readLine()), // 3
                    Integer.parseInt(fr.readLine()), // 4
                    Integer.parseInt(fr.readLine()), // 5
                    Integer.parseInt(fr.readLine()), // 6
                    Integer.parseInt(fr.readLine()), // 7
                    Integer.parseInt(fr.readLine()), // 8
                    Integer.parseInt(fr.readLine())); // 9
            fr.close();
        } catch(IOException e) {
        }

        eventListItems = new ArrayList<EventListItem>();
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activityResultLauncher.launch(signInIntent);
    }

    // Google Login 종료 시 실행
    private ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

                // Login API 정상 종료
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null) {
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                        try {
                            //Google Login Succeed
                            acc = task.getResult(ApiException.class);
                            Log.d("LOGIN", "firebaseAuthWithGoogle:" + acc.getId());

                            // 사용자 이름 받아와 출력
                            TextView nameView = (TextView) findViewById(R.id.namecard_name);
                            nameView.setText(acc.getDisplayName());

                            // 사용자 Email 받아와 출력
                            TextView emailView = (TextView) findViewById(R.id.namecard_mail);
                            emailView.setText(acc.getEmail());

                            // 사용자 프로필 아이콘 받아와 출력
                            ImageView imageView = (ImageView) findViewById(R.id.namecard_image);
                            Glide.with(this).load(acc.getPhotoUrl()).into(imageView);

                        } catch (ApiException e) {

                            //Google Login Failed
                            Log.w("LOGIN", "Google sign in failed", e);
                        }
                    }
                }
            });


    public void FragmentView(int fragmentNumber) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch(fragmentNumber) {
            case 1:
                StatisticsFragment  statisticsFragment = new StatisticsFragment();
                transaction.replace(R.id.Screen, statisticsFragment);
                transaction.commit();
                break;
            case 2:
                NoteFragment noteFragment = new NoteFragment();
                transaction.replace(R.id.Screen, noteFragment);
                transaction.commit();
                break;
            case 3:
//                GoogleCalenderFragment calenderFragment = new GoogleCalenderFragment();
//                transaction.replace(R.id.Screen, calenderFragment);
                SubscriptionFilterFragment subscriptionFilterFragment = new SubscriptionFilterFragment(MainActivity.this);
                transaction.replace(R.id.Screen, subscriptionFilterFragment);
                transaction.commit();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Fragment subFragment = getSupportFragmentManager().findFragmentById(R.id.Screen);
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else if (subFragment != null) {
            ((OnBackPressedListener)subFragment).onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_menu_home) {
            FragmentView(1);
        } else if (id == R.id.nav_menu_note) {
            FragmentView(2);
        } else if (id == R.id.nav_menu_event) {
            FragmentView(3);
        }

        drawerLayout.closeDrawer(navigationView);
        return true;
    }

    public void onAddBookFragment() {
        AddBookFragment addBookFragment = new AddBookFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(R.id.Screen, addBookFragment);
        transaction.commit();
    }

    public void onEventSearchFragment() {
        EventListFragment eventListFragment = new EventListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(R.id.Screen, eventListFragment);
        transaction.commit();
    }

    public void setLastFilePath(String str) {
        lastFilePath = str;
    }

    public String getLastFilePath() {
        return lastFilePath;
    }

    public void onAddNoteFragment(String path) {
        lastFilePath = path;
        AddNoteFragment addNoteFragment = new AddNoteFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(R.id.Screen, addNoteFragment);
        transaction.commit();
    }
}