package com.shinhan.googlemapexam;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    SupportMapFragment mapFragment;
    GoogleMap map;
    int tourPos = 0;


    class MyMarker {
        String name;
        LatLng latLng;
        MyMarker(String name, LatLng latLng) {
            this.name = name;
            this.latLng = latLng;
        }
    }

    MyMarker[] markers = {
            new MyMarker("동백호수공원", new LatLng(37.2761609,127.1476889)),
            new MyMarker("중랑구청", new LatLng(37.6065615,127.0918558)),
            new MyMarker("마라도", new LatLng(33.1166704,126.2657834)),
            new MyMarker("독도", new LatLng(37.2426815,131.8591766)),
            new MyMarker("예루살렘", new LatLng(31.7964453,35.1053184)),
            new MyMarker("로마", new LatLng(41.9102415,12.3959123)),
            new MyMarker("파리", new LatLng(48.8589507,2.2775168)),
            new MyMarker("북경", new LatLng(39.9390731,116.1172668)),
            new MyMarker("평양역", new LatLng(39.0048981,125.734234)),
            new MyMarker("워싱턴", new LatLng(38.8995322,-77.1546526))
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Log.d(TAG, "GoogleMap is ready");
                map = googleMap;

                PolylineOptions rectOptions = new PolylineOptions();
                rectOptions.color(Color.RED);


                // 마커출력
                for (int i = 0; i < markers.length; i++) {
                    MarkerOptions marker = new MarkerOptions();
                    marker.position(markers[i].latLng);
                    marker.title(markers[i].name);
                    map.addMarker(marker);

                    // 마커 터치 이벤트 등록
                    map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {

                            for (int j=0; j<markers.length; j++) {
                                if (markers[j].name.equals(marker.getTitle())) {
                                    tourPos = j + 1;
                                    if (j > markers.length) tourPos = 0;
                                    break;
                                }
                            }

                            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15));
                            return false;
                        }
                    });

                    rectOptions.add(markers[i].latLng);
                }
                Polyline polyline = map.addPolyline(rectOptions);
            }
        });

        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "GPS 연동 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            }
            else {
                ActivityCompat.requestPermissions(this,
                        new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "GPS 권한 승인!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "GPS 권한 거부!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void startLocationService(View view) {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            TextView textView = (TextView) findViewById(R.id.location);
            if (location != null) {
                textView.setText("마지막 위치: " + location.getLatitude() + ", " + location.getLongitude());
                Toast.makeText(MainActivity.this, "마지막 위치: " + location.getLatitude() + ", " + location.getLongitude(),
                        Toast.LENGTH_SHORT).show();
            }
            LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());
            if (map != null) {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
            }

            GPSListener gpsListener = new GPSListener();
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, gpsListener);
        }
    }

    public void onWorldMapButtonClicked(View view) {
        if (map != null) {
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            map.moveCamera(CameraUpdateFactory.zoomTo(1));
        }
    }

    public void onTourButtonClicked(View view) {

        if (map != null) {
            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(markers[tourPos].latLng, 17));
            tourPos++;
            if (tourPos >= markers.length) tourPos = 0;
        }
    }

    private class GPSListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            double latitude = location.getLatitude(); // 위도
            double longitude = location.getLongitude(); // 경도
            TextView textView = (TextView) findViewById(R.id.location);
            textView.setText("내 위치: " + latitude + ", " + longitude);
            Toast.makeText(MainActivity.this, "내 위치: " + latitude + ", " + longitude,
                    Toast.LENGTH_SHORT).show();

            LatLng curPoint = new LatLng(latitude, longitude);
            if (map != null) {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
