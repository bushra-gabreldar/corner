package example.hellojaleel.corner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    final FragmentManager fragmentManager = getSupportFragmentManager();
    androidx.appcompat.widget.Toolbar toolbar;
    ParseUser user;
    int startingPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = ParseUser.getCurrentUser();


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        setUpBottomNavigationView();

        toolbar = findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
       // setSupportActionBar(toolbar);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void setUpBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = new takesFragment();
                int newPosition = 0;
                switch (menuItem.getItemId()) {
                    case R.id.takesFragment:
                        Log.d("takesFragment", "takesFragment clicked");
                        fragment = new takesFragment();
                        newPosition = 1;
                        break;
                    case R.id.profileFragment:
                        Log.d("takesFragment", "takesFragment clicked");
                        fragment = new takesFragment();
                        newPosition = 2;
                        break;

                }
                return loadFragment(fragment, newPosition);
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.takesFragment);
    }

    private boolean loadFragment(Fragment fragment, int pos) {
        if (fragment != null) {
            if (startingPosition < pos) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.flContainter, fragment);
                transaction.commit();
            } else {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.flContainter, fragment);
                transaction.commit();
            }
            startingPosition = pos;
            return true;
        }
        return false;
    }


    public void onClickLogout(MenuItem item) {
        ParseUser.getCurrentUser().put("deviceToken", "");
        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    e.printStackTrace();
                } else {
                    ParseUser.logOut();
                    Intent logoutIntent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(logoutIntent);
                    finish();
                }
            }
        });
    }
}
