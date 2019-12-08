package example.hellojaleel.corner;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import android.os.Bundle;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

import example.hellojaleel.corner.models.Take;

public class ComposeActivity extends AppCompatActivity {

    public EditText description_et;
    public Button submit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);



        description_et = findViewById(R.id.description_et);
        submit_btn = findViewById(R.id.submit_btn);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Body = description_et.getText().toString();
                ParseUser user = ParseUser.getCurrentUser();
                saveTake(Body, user);
                return;
            }
        });
    }


    private void saveTake(String Body, ParseUser user) {
        Take take = new Take();
        take.setBody(Body);
        take.setUser(user);
        take.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    e.printStackTrace();
                    return;
                }
                description_et.setText("");
            }
        });
    }

    private void queryTakes() {
        final ParseQuery<Take> takeQuery = new ParseQuery<Take>(Take.class);
        takeQuery.include(Take.KEY_USER);
        takeQuery.findInBackground(new FindCallback<Take>() {
            @Override
            public void done(List<Take> objects, ParseException e) {
                if (e != null) {
                    e.printStackTrace();
                    return;
                }
            }
        });
    }
}
