package pbapps.nbt_attempt3;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, RoutingListener
{

    private GoogleMap googleMap;
    String rtname;
    private Circle circle;


    MyDBAdapter mdb = new MyDBAdapter(this);
    //public static ArrayList<String> x1 = new ArrayList<String>();
    //public static ArrayList<String> y1 = new ArrayList<String>();
    //public static ArrayList<String> x2 = new ArrayList<String>();
    //public static ArrayList<String> y2 = new ArrayList<String>();
    //public static ArrayList<String> x3 = new ArrayList<String>();
    //public static ArrayList<String> y3 = new ArrayList<String>();

    String a1,b1,a2,b2,a3,b3;

    private static LatLng Stop1, Stop2, Stop3;







    //mMap.addPolyline(poption);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        googleMap = mapFragment.getMap();




        Bundle bundle = getIntent().getExtras();
        rtname = bundle.getString("selectedRoute");
        Log.d("lol", rtname);
        polylines = new ArrayList<>();

        mdb.open();

        Cursor c = mdb.getSpecificRouteEntry(rtname);
        //x1.clear();
        //y1.clear();
        //x2.clear();
        //y2.clear();
        //x3.clear();
        //y3.clear();
        //Log.d("lol", c.getString(1));

        if(c.getCount()>0)
        {
            while(c.moveToNext())
            {
                //x1.add(c.getString(2));
                //y1.add(c.getString(3));
                //x2.add(c.getString(5));
                //y2.add(c.getString(6));
                //x3.add(c.getString(8));
                //y3.add(c.getString(9));
                Log.d("lol", c.getString(1));
                a1=c.getString(2);
                b1=c.getString(3);
                a2=c.getString(5);
                b2=c.getString(6);
                a3=c.getString(8);
                b3=c.getString(9);
            }

        }

        //LatLng Stop1 = new LatLng(Double.parseDouble(x1.toArray()), Double.parseDouble(y1.toString()));
        //LatLng Stop2 = new LatLng(Double.parseDouble(x2.toString()), Double.parseDouble(y2.toString()));
        //LatLng Stop3 = new LatLng(Double.parseDouble(x3.toString()), Double.parseDouble(y3.toString()));

        Stop1 = new LatLng(Double.parseDouble(a1), Double.parseDouble(b1));
        Stop2 = new LatLng(Double.parseDouble(a2), Double.parseDouble(b2));
        Stop3 = new LatLng(Double.parseDouble(a3), Double.parseDouble(b3));
        Log.d("lol", a1);
        Log.d("lol", Stop1.toString());
        Log.d("lol", Stop2.toString());
        Log.d("lol", Stop3.toString());


        //PolylineOptions poption = new PolylineOptions().add(Stop1).add(Stop2).add(Stop3).width(5).color(Color.BLUE).geodesic(true);
        //googleMap.addPolyline(poption);
        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Stop3, 15));
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mdb.close();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        // Add a marker in Sydney and move the camera
        LatLng Nirma_university = new LatLng(23.128435, 72.542948);
        /*if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {*/
        try
        {
            map.setMyLocationEnabled(true);
        }
        catch (Exception e){};
        //map.setMyLocationEnabled(true);

        Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(false)
                .waypoints(Stop1, Stop2, Stop3)
                .build();
        routing.execute();

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Nirma_university, 13));

        map.addMarker(new MarkerOptions()
                .title("Nirma University")
                .snippet("Route Ends Here")
                .position(Nirma_university));

        circle = googleMap.addCircle(new CircleOptions()
                .center(Stop1)
                .radius(100)
                .strokeWidth(10)
                .strokeColor(Color.GREEN)
                .fillColor(Color.argb(128, 255, 0, 0)));
        circle = googleMap.addCircle(new CircleOptions()
                .center(Stop2)
                .radius(100)
                .strokeWidth(10)
                .strokeColor(Color.GREEN)
                .fillColor(Color.argb(128, 255, 0, 0)));
        circle = googleMap.addCircle(new CircleOptions()
                .center(Stop3)
                .radius(100)
                .strokeWidth(10)
                .strokeColor(Color.GREEN)
                .fillColor(Color.argb(128, 255, 0, 0)));
    }

    private List<Polyline> polylines;
    private static final int[] COLORS = new int[]{R.color.primary_dark_material_light};


    @Override
    public void onRoutingFailure(RouteException e) {
        if(e != null) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {

        if(polylines.size()>0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i <route.size(); i++) {

            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(10 + i * 3);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = googleMap.addPolyline(polyOptions);
            polylines.add(polyline);

            Toast.makeText(getApplicationContext(),"Route "+ (i+1) +": distance - "+ route.get(i).getDistanceValue()+": duration - "+ route.get(i).getDurationValue(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRoutingCancelled() {

    }


}
