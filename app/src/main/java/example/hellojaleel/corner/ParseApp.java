package example.hellojaleel.corner;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;

import example.hellojaleel.corner.models.Take;
import example.hellojaleel.corner.models.User;

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Take.class);
        ParseObject.registerSubclass(User.class);

        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("corner")
                .clientKey("wizardsvrockets")
                .server("http://cornerharvard.herokuapp.com/parse")
                .build();

        Parse.initialize(configuration);
    }
}
