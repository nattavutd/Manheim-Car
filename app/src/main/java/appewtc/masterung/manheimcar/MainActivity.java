package appewtc.masterung.manheimcar;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private Button singInButton, singUpButton;
    private EditText userEditText, passwordEditText;
    private String userString, passwordString, truepasswordString;
    private String[] nameStrings, imageStrings, latStrings, lngStrings;
    private boolean aBoolean = true;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        singInButton = (Button) findViewById(R.id.button);
        singUpButton = (Button) findViewById(R.id.button2);
        userEditText = (EditText) findViewById(R.id.editText4);
        passwordEditText = (EditText) findViewById(R.id.editText5);

        //signUp Controller
        singUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });

        //SignIn controller
        singInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get Value
                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

                //Check Space
                if (userString.equals("") || passwordString.equals("")) {
                    MyAlert myAlert = new MyAlert(MainActivity.this,
                            R.drawable.nobita48, "Have Space",
                            "Please Fill All Every Blank");
                    myAlert.myDialog();

                } else {
                    //No Space
                    MyConstant myConstant = new MyConstant();
                    SynData synData = new SynData(MainActivity.this);
                    synData.execute(myConstant.getUslJSON(),
                            myConstant.getTestTitle(),
                            myConstant.getTestMessage());

                }

            }   //OnClick
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }   // Main Method

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private class SynData extends AsyncTask<String, Void, String> {

        //Explicit
        private Context context;
        private String titleString, messageString;


        public SynData(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {

            try {

                titleString = strings[1];
                messageString = strings[2];

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(strings[0]).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();


            } catch (Exception e) {
                Log.d("24octV1", "e doInBack ==>" + e.toString());
                return null;
            }


        }   //doInBlack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("24octV1", "JSON ==>" + s);

            try {
                JSONArray jsonArray = new JSONArray(s);

                nameStrings = new String[jsonArray.length()];
                imageStrings = new String[jsonArray.length()];
                latStrings = new String[jsonArray.length()];
                lngStrings = new String[jsonArray.length()];


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    //Check User
                    if (userString.equals(jsonObject.getString("User"))) {

                        aBoolean = false;
                        truepasswordString = jsonObject.getString("Password");
                    }   //if

                    //Setup Array
                    nameStrings[i] = jsonObject.getString("Name");
                    imageStrings[i] = jsonObject.getString("Image");
                    latStrings[i] = jsonObject.getString("Lat");
                    lngStrings[i] = jsonObject.getString("Lng");

                    //Check
                    Log.d("24octV4", "Name(" + i + ") ==> " + nameStrings[i]);
                    Log.d("24octV4", "Image(" + i + ") ==> " + imageStrings[i]);
                    Log.d("24octV4", "Lat(" + i + ") ==> " + latStrings[i]);
                    Log.d("24octV4", "Lng(" + i + ") ==> " + lngStrings[i]);


                }   //For

                if (aBoolean) {

                    MyAlert myAlert = new MyAlert(context, R.drawable.bird48,
                            titleString, messageString);
                    myAlert.myDialog();

                } else if (passwordString.equals(truepasswordString)) {
                    //Password True
                    Toast.makeText(context, "Welcome", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, ListService.class);

                    //Put Data to ListService
                    intent.putExtra("Name", nameStrings);
                    intent.putExtra("Image", imageStrings);
                    intent.putExtra("Lat", latStrings);
                    intent.putExtra("Lng", lngStrings);

                    startActivity(intent);
                    finish();

                } else {
                    //Password False
                    MyAlert myAlert = new MyAlert(context, R.drawable.doremon48, "Wrong Password", "Please Try Again");
                    myAlert.myDialog();
                }

            } catch (Exception e) {
                Log.d("24octV2", "e ==>" + e.toString());

            }

        }   //OnPost

    }   //SynData Class


}   // Main Class   นี่คือ คลาสหลัก
