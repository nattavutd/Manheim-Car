package appewtc.masterung.manheimcar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class ListService extends AppCompatActivity {

    //Explicit
    ListView listView;
    String[] nameStrings, latStrings, lngStrings, imageStrings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_service);

        //Bind Widget
        listView = (ListView) findViewById(R.id.livOfficer);

        //Get Value From Intent
        nameStrings = getIntent().getStringArrayExtra("Name");
        latStrings = getIntent().getStringArrayExtra("Lat");
        lngStrings = getIntent().getStringArrayExtra("Lng");
        imageStrings = getIntent().getStringArrayExtra("Image");

        //Check Array
        Log.d("24octv3", "จำนวน RECORD ที่อ่านได้ ==> " + nameStrings.length);
        for (int i=0;i<nameStrings.length;i++) {

            Log.d("24octV3", "Name(" + i + ")==>" + nameStrings);
            Log.d("24octV3", "Image(" + i + ")==>" + imageStrings);
            Log.d("24octV3", "Lat(" + i + ")==>" + latStrings);
            Log.d("24octV3", "Lng(" + i + ")==>" + lngStrings);

        }

        //Create ListView
        OfficerAdapter officerAdapter = new OfficerAdapter(ListService.this,
                nameStrings, latStrings, lngStrings, imageStrings);
        listView.setAdapter(officerAdapter);


    }   //MainMethod

}       //MainClass
