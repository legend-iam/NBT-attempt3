package pbapps.nbt_attempt3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddRouteActivity extends AppCompatActivity {

    private EditText rtname, stop1, stop2, stop3, x1, y1, x2, y2, x3, y3;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rtname = (EditText) findViewById(R.id.route_name_edittext);
        stop1 = (EditText) findViewById(R.id.stop1nameet);
        stop2 = (EditText) findViewById(R.id.stop2nameet);
        stop3 = (EditText) findViewById(R.id.stop3nameet);
        x1 = (EditText) findViewById(R.id.stop1xet);
        y1 = (EditText) findViewById(R.id.stop1yet);
        x2 = (EditText) findViewById(R.id.stop2xet);
        y2 = (EditText) findViewById(R.id.stop2yet);
        x3 = (EditText) findViewById(R.id.stop3xet);
        y3 = (EditText) findViewById(R.id.stop3yet);

        add = (Button) findViewById(R.id.button_add);

        final MyDBAdapter myDBAdapter = new MyDBAdapter(this);
        myDBAdapter.open();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDBAdapter.insertEntry(rtname.getText().toString(),
                        stop1.getText().toString(),
                        stop2.getText().toString(),
                        stop3.getText().toString(),
                        x1.getText().toString(),
                        y1.getText().toString(),
                        x2.getText().toString(),
                        y2.getText().toString(),
                        x3.getText().toString(),
                        y3.getText().toString());

                myDBAdapter.close();

                Intent intent = new Intent(AddRouteActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
