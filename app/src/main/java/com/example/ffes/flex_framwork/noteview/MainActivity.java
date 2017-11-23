package com.example.ffes.flex_framwork.noteview;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.Login.view.LoginActivity;
import com.example.ffes.flex_framwork.noteview.account.AccountFragment;
import com.example.ffes.flex_framwork.noteview.addnote.AddNoteFragment;
import com.example.ffes.flex_framwork.noteview.addnote.MakeNoteFragment;
import com.example.ffes.flex_framwork.noteview.personalspace.adapter.AccountAdapter;
import com.example.ffes.flex_framwork.noteview.personalspace.view.NoteSpaceFragment;
import com.example.ffes.flex_framwork.noteview.searchmaterial.view.SearchNoteFragement;
import com.example.ffes.flex_framwork.noteview.widget.AccountViewPager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_LOGIN=100;
    BottomNavigationView bottomNavigationView;

    AccountViewPager viewPager;
    MenuItem prevMenuItem;

    private static final String TAG = "Account";

    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener listener;

    //SearchNoteFragment fragmenta;最左邊搜尋筆記介面
    //MakeNoteFragment fragmentb;右邊數來第三個做筆記介面
    //SelfNoteFragment fragmentc;//右邊數來第二個個人空間介面
    //GoogleAccountToolbar fragmentd;//最右邊的帳戶介面

    NoteSpaceFragment noteSpaceFragment;
    AccountFragment accountFragment;
    MakeNoteFragment makeNoteFragment;
    SearchNoteFragement searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();
        viewPager = (AccountViewPager) findViewById(R.id.viewpager);

        //Initializing the bottomNavigationView
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.title_:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.add:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.note:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.people:
                                viewPager.setCurrentItem(3);
                                break;
                        }
                        return false;
                    }
                });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        listener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user==null){
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }else{
                    setupViewPager(viewPager);
                    viewPager.setCurrentItem(3);
                    bottomNavigationView.getMenu().getItem(3).setChecked(true);
                }
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        if(listener!=null){
            auth.removeAuthStateListener(listener);
        }
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(listener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_LOGIN:
                if (resultCode != RESULT_OK) {
                    finish();
                }
                break;
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        AccountAdapter adapter = new AccountAdapter(getSupportFragmentManager());
        //fragmenta= new SearchNoteFragment();
        //fragmentb= new MakeNoteFragment();
        //fragmentc=new SelfNoteFragment();
        //fragmentd= new GoogleAccountToolbar();
        noteSpaceFragment=NoteSpaceFragment.newInstance();
        accountFragment=AccountFragment.newIstance();
        makeNoteFragment= MakeNoteFragment.newInstance();
        searchFragment=SearchNoteFragement.newInstance();
        //adapter.addFragment(fragmenta);
        //adapter.addFragment(fragmentb);
        //adapter.addFragment(fragmentc);
        //adapter.addFragment(fragmentd);//要有這些介面才能切換
        adapter.addFragment(searchFragment);
        adapter.addFragment(makeNoteFragment);
        adapter.addFragment(noteSpaceFragment);
        adapter.addFragment(accountFragment);
        viewPager.setAdapter(adapter);
    }

    public static void startAction(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        context.startActivity(intent);
    }

}
