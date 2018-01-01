package pbapps.nbt_attempt3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;



import android.widget.AdapterView;


import android.widget.DatePicker;




import java.util.ArrayList;

public class DisplayLocationActivity extends AppCompatActivity {

    private Button disp;
    String selectedRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        disp = (Button) findViewById(R.id.display_button);



        final MyDBAdapter myDBAdapter = new MyDBAdapter(this);
        myDBAdapter.open();

        final ArrayList<String> routes = myDBAdapter.getAllRoutesForSpinner();   //creating category array

        final String[] routeArray = new String[routes.size()];

        routes.toArray(routeArray);


        final Spinner spinner = (Spinner)findViewById(R.id.routeSpinner);      //creating the drop-down menu
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, routeArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        disp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedRoute = spinner.getSelectedItem().toString();
                Intent intent = new Intent(DisplayLocationActivity.this, MapsActivity.class);
                intent.putExtra("selectedRoute", selectedRoute);
                Log.d("lol", selectedRoute);
                myDBAdapter.close();
                startActivity(intent);
                finish();
            }
        });

    }


}
