package com.example.testapi;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.LocationBias;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private PlacesClient placesClient;
    //    private PlacesAdapter placesAdapter;
    private AutocompleteSessionToken sessionToken;
    //    String TAG = "placeautocomplete";
//    TextView txtView;
    private GoogleMap mMap = null;

    private FusedLocationProviderClient fusedLocationProviderClient;


    private boolean mLocationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private Location lastKnownlocation = null;
    private Double selectedLatitude = null;
    private Double selectedLongtitude = null;
    private String selectedAddress = "";

    // Used for selecting the current place.
    private static final int M_MAX_ENTRIES = 5;
    private String[] mLikelyPlaceNames;
    private String[] mLikelyPlaceAddresses;
    private String[] mLikelyPlaceAttributions;
    private LatLng[] mLikelyPlaceLatLngs;

    private static LatLng mDefaultLocation = new LatLng(10.8231, 106.6297);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String apiKey = getString(R.string.APIKey);

        MaterialButton doneBtn = (MaterialButton) findViewById(R.id.donebBtn);
        TextView selectedPlaceTv = (TextView) findViewById(R.id.selectedPlaceTv);
        //get the selected location back to requesting activity/fragment class
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("latitude", selectedLatitude);
                intent.putExtra("longtitude", selectedLongtitude);
                intent.putExtra("address", selectedAddress);

                setResult(RESULT_OK, intent);
                finish();
            }
        });

        //SupportMapFragment, get notified when the map is ready to used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);


        //Initialize the Places client
        Places.initialize(getApplicationContext(), apiKey);
        placesClient = Places.createClient(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteSupportFragment.setTypeFilter(TypeFilter.ESTABLISHMENT);

        autocompleteSupportFragment.setCountries("VN");
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG));



        //Lấy thông tin ra gán địa chỉ vào ô Address
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                String id = place.getId();
                String title = place.getName();
                LatLng latLng = place.getLatLng();
                selectedLatitude = latLng.latitude;
                selectedLongtitude = latLng.longitude;
                selectedAddress = place.getAddress();

                Log.i(TAG, "onPlaceSelected ID: " + id);
                Log.i(TAG, "onPlaceSelected Title: " + title);
                Log.i(TAG, "onPlaceSelected Latitude: " + selectedLatitude);
                Log.i(TAG, "onPlaceSelected Longtitude: " + selectedLongtitude);
                Log.i(TAG, "onPlaceSelected Address: " + selectedAddress);

                selectedPlaceTv.setText(selectedAddress);

            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i(TAG, "onError: stats: " + status);
            }
        });
//        detectAndShowDeviceLocationMap();
        getDeviceLocation();

    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        mLocationPermissionGranted = false;
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }
    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng hcm = new LatLng(10.8231, 106.6297);
        mMap.addMarker(new MarkerOptions().position(hcm).title("My Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hcm, 15));

        mMap.getUiSettings().setZoomControlsEnabled(true);
//        requestLocationPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                selectedLatitude = latLng.latitude;
                selectedLongtitude = latLng.longitude;

                Log.d(TAG, "onMapClick: selectLatitude: " + selectedLatitude);
                Log.d(TAG, "onMapClick: selectLongtitude: " + selectedLongtitude);

                addressFromLatLng(latLng);
            }
        });

        // Prompt the user for permission.
        getLocationPermission();

    }

//    private void getCurrentPlaceLikelihoods() {
//        // Use fields to define the data types to return.
//        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS,
//                Place.Field.LAT_LNG);
//
//        // Get the likely places - that is, the businesses and other points of interest that
//        // are the best match for the device's current location.
//        @SuppressWarnings("MissingPermission") final FindCurrentPlaceRequest request =
//                FindCurrentPlaceRequest.builder(placeFields).build();
//        @SuppressLint("MissingPermission") Task<FindCurrentPlaceResponse> placeResponse = placesClient.findCurrentPlace(request);
//        placeResponse.addOnCompleteListener(this,
//                new OnCompleteListener<FindCurrentPlaceResponse>() {
//                    @Override
//                    public void onComplete(@NonNull Task<FindCurrentPlaceResponse> task) {
//                        if (task.isSuccessful()) {
//                            FindCurrentPlaceResponse response = task.getResult();
//                            // Set the count, handling cases where less than 5 entries are returned.
//                            int count;
//                            if (response.getPlaceLikelihoods().size() < M_MAX_ENTRIES) {
//                                count = response.getPlaceLikelihoods().size();
//                            } else {
//                                count = M_MAX_ENTRIES;
//                            }
//
//                            int i = 0;
//                            mLikelyPlaceNames = new String[count];
//                            mLikelyPlaceAddresses = new String[count];
//                            mLikelyPlaceAttributions = new String[count];
//                            mLikelyPlaceLatLngs = new LatLng[count];
//
//                            for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
//                                Place currPlace = placeLikelihood.getPlace();
//                                mLikelyPlaceNames[i] = currPlace.getName();
//                                mLikelyPlaceAddresses[i] = currPlace.getAddress();
//                                mLikelyPlaceAttributions[i] = (currPlace.getAttributions() == null) ?
//                                        null : TextUtils.join(" ", currPlace.getAttributions());
//                                mLikelyPlaceLatLngs[i] = currPlace.getLatLng();
//
//                                String currLatLng = (mLikelyPlaceLatLngs[i] == null) ?
//                                        "" : mLikelyPlaceLatLngs[i].toString();
//
//                                Log.i(TAG, String.format("Place " + currPlace.getName()
//                                        + " has likelihood: " + placeLikelihood.getLikelihood()
//                                        + " at " + currLatLng));
//
//                                i++;
//                                if (i > (count - 1)) {
//                                    break;
//                                }
//                            }
//
//
//                            // COMMENTED OUT UNTIL WE DEFINE THE METHOD
//                            // Populate the ListView
//                        } else {
//                            Exception exception = task.getException();
//                            if (exception instanceof ApiException) {
//                                ApiException apiException = (ApiException) exception;
//                                Log.e(TAG, "Place not found: " + apiException.getStatusCode());
//                            }
//                        }
//                    }
//                });
//    }

    /**
     * Get the current location of the device, and position the map's camera
     */
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                @SuppressLint("MissingPermission") Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnSuccessListener((Executor) this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownlocation= location;
                            Log.d(TAG, "Latitude: " + lastKnownlocation.getLatitude());
                            Log.d(TAG, "Longitude: " + lastKnownlocation.getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(lastKnownlocation.getLatitude(),
                                            lastKnownlocation.getLongitude()), 15));
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, 15));
                        }

//                        getCurrentPlaceLikelihoods();
                    }
                });
            }
        } catch (Exception e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }
    /**
     * Fetch a list of likely places, and show the current place on the map - provided the user
     * has granted location permission.
     */
    private void pickCurrentPlace() {
        if (mMap == null) {
            return;
        }

        if (mLocationPermissionGranted) {
            getDeviceLocation();
        } else {
            // The user has not granted permission.
            Log.i(TAG, "The user did not grant location permission.");

            // Add a default marker, because the user hasn't selected a place.
            mMap.addMarker(new MarkerOptions()
                    .title(getString(R.string.default_info_title))
                    .position(mDefaultLocation)
                    .snippet(getString(R.string.default_info_snippet)));

            // Prompt the user for permission.
            getLocationPermission();
        }
    }
    /**
     * When user taps an item in the Places list, add a marker to the map with the place details
     */
    private AdapterView.OnItemClickListener listClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            // position will give us the index of which place was selected in the array
            LatLng markerLatLng = mLikelyPlaceLatLngs[position];
            String markerSnippet = mLikelyPlaceAddresses[position];
            if (mLikelyPlaceAttributions[position] != null) {
                markerSnippet = markerSnippet + "\n" + mLikelyPlaceAttributions[position];
            }

            // Add a marker for the selected place, with an info window
            // showing information about that place.
            mMap.addMarker(new MarkerOptions()
                    .title(mLikelyPlaceNames[position])
                    .position(markerLatLng)
                    .snippet(markerSnippet));

            // Position the map's camera at the location of the marker.
            mMap.moveCamera(CameraUpdateFactory.newLatLng(markerLatLng));
        }
    };

















//    private ActivityResultLauncher requestLocationPermission = registerForActivityResult(
//            new ActivityResultContracts.RequestPermission(),
//            new ActivityResultCallback<Boolean>() {
//                @SuppressLint("MissingPermission")
//                @Override
//                public void onActivityResult(Boolean isGranted) {
//                    Log.d(TAG, "onActivityResult: ");
//
//                    if (isGranted) {
//                        mMap.setMyLocationEnabled(true);
//                        pickCurrentPlace();
//                    } else {
//                        toast(MainActivity.this, "Permission denied...!");
//                    }
//                }
//            }
//    );
//    public void toast(Context context, String message) {
//        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//    }
//
    private void addMarkerCustom(LatLng latLng, String title, String selectedAddress) {
//        Log.d(TAG, "addMarker Latitude: " + latLng.latitude);
//        Log.d(TAG, "addMarker Longtitude: " + latLng.longitude);
//        Log.d(TAG, "addMarker title: " + title);
//        Log.d(TAG, "addMarker address: " + selectedAddress);
        mMap.clear();

        try {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("" + title);
            markerOptions.snippet("" + selectedAddress);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

            TextView selectedPlaceTv = (TextView) findViewById(R.id.selectedPlaceTv);
            selectedPlaceTv.setText(selectedAddress);
        } catch (Exception e) {
            Log.e(TAG, "addMarker: ", e);
        }
    }
//
//    private boolean isGPSEnabled() {
//        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//        boolean gpsEnabled = false;
//        boolean networkEnabled = false;
//
//        try {
//            gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        } catch (Exception e) {
//            Log.e(TAG, "isGPSEnabled: ", e);
//        }
//
//        try {
//            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//        } catch (Exception e) {
//            Log.e(TAG, "networkEnabled: ", e);
//        }
//
//        return !(!gpsEnabled && !networkEnabled);
//    }
//
//
    private void addressFromLatLng(LatLng latLng) {
        Log.d(TAG, "addressFromLatLng: ");

        Geocoder geocoder = new Geocoder(this);

        try{
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

            Address address = addressList.get(0);
            String addressLine = address.getAddressLine(0);
            String countryName = address.getCountryName();
            String adminArea = address.getAdminArea();
            String subAdminArea = address.getSubAdminArea();
            String locality = address.getLocality();
            String subLocality = address.getSubLocality();
            String postalCode = address.getPostalCode();

            selectedAddress = "" + addressLine;

            addMarkerCustom(latLng, "" + subLocality, "" + addressLine);
        }catch (Exception e) {
            Log.e(TAG, "addressFromLatLng: ", e);
        }
    }
//
//
//    private void pickCurrentPlace() {
//        Log.d(TAG, "pickCurrentPlace: ");
//        if(mMap == null) {
//            return;
//        }
//
//        detectAndShowDeviceLocationMap();
//    }
//
//
//    @SuppressLint("MissingPermission")
//    private void detectAndShowDeviceLocationMap() {
//
//        try {
//            Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
//            locationResult.addOnSuccessListener(new OnSuccessListener<Location>() {
//                @Override
//                public void onSuccess(Location location) {
//                    if(location != null) {
//                        lastKnownlocation = location;
////                        selectedLatitude = location.getLatitude();
////                        selectedLongtitude = location.getLongitude();
//
//                        Log.d(TAG, "onSuccess: selectedLatitude: " + lastKnownlocation.getLatitude());
//                        Log.d(TAG, "onSuccess: seleectedLongtitude: " + lastKnownlocation.getLongitude());
//
//                        //Setup LatLng from selectedLatitude and selectedLongtitude
//                        LatLng latLng = new LatLng(lastKnownlocation.getLatitude(), lastKnownlocation.getLongitude());
//                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
//                        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
////                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
////                                new LatLng(lastKnownlocation.getLatitude(),
////                                        lastKnownlocation.getLongitude()), 15));
//
//                        addressFromLatLng(latLng);
//                    } else {
//                        Log.d(TAG, "onSuccess: Location is Null");
//                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, 15));
//                    }
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Log.e(TAG, "onFailure: ", e);
//                }
//            });
//        }catch (Exception e) {
//            Log.e(TAG, "detectAndShowDeviceLocationMap: ", e);
//        }
//    }





//        searchBar = (EditText) findViewById(R.id.seacrBar);
//
//        if(!Places.isInitialized()) {
//            Places.initialize(this, getString(R.string.APIKey));
//        }
//        placesClient = Places.createClient(this);
//        sessionToken = AutocompleteSessionToken.newInstance();
//        ListView listPlaces = (ListView) findViewById(R.id.listPlaces);
//
//        placesAdapter = new PlacesAdapter(this);
//        listPlaces.setAdapter(placesAdapter);
//        listPlaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if(placesAdapter.getCount() > 0) {
//                    detailPlace(placesAdapter.predictions.get(position).getPlaceId());
//                }
//            }
//        });
//        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    if(searchBar.length() > 0) {
//                        searchPlace();
//                    }
//                }
//                return false;
//            }
//        });
//
//    }
//    private void searchPlace() {
//        final LocationBias bias = RectangularBounds.newInstance(
//                new LatLng(22.458744, 88.208162),
//                new LatLng(22.738671, 88.524896)
//        );
//        final FindAutocompletePredictionsRequest newRequest = FindAutocompletePredictionsRequest
//                .builder()
//                .setSessionToken(sessionToken)
//                .setTypeFilter(TypeFilter.ESTABLISHMENT)
//                .setQuery(searchBar.getText().toString())
//                .setLocationBias(bias)
//                .setCountries("ID")
//                .build();
//        placesClient.findAutocompletePredictions(newRequest).addOnSuccessListener(new OnSuccessListener<FindAutocompletePredictionsResponse>() {
//            @Override
//            public void onSuccess(FindAutocompletePredictionsResponse findAutocompletePredictionsResponse) {
//                List<AutocompletePrediction> predictions = findAutocompletePredictionsResponse.getAutocompletePredictions();
//                placesAdapter.setPredictions((AutocompletePrediction) predictions);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                if(e instanceof ApiException) {
//                    ApiException apiException = (ApiException) e;
//                    Log.e("MainActivity", "Place not found: " + apiException.getStatusCode());
//                }
//            }
//        });
//    }
//
//    private void detailPlace(String placeID) {
//        final List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
//        final FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeID, placeFields);
//        placesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
//            @Override
//            public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
//                Place place = fetchPlaceResponse.getPlace();
//                LatLng latLng = place.getLatLng();
//                if(latLng != null) {
//                    Toast.makeText(MainActivity.this, "Latlng: ", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                if(e instanceof ApiException) {
//                    ApiException apiException = (ApiException) e;
//                    Log.e("MainActivity", "Place not found: " + e.getMessage());
//                    final int statusCode = apiException.getStatusCode();
//                }
//            }
//        });
//    }
//    private static class PlacesAdapter extends BaseAdapter {
//        private final List<AutocompletePrediction> predictions = new ArrayList<>();
//        private final Context context;
//
//        public PlacesAdapter(Context context) {
//            this.context = context;
//        }
//
//        public void setPredictions(AutocompletePrediction predictions) {
//            this.predictions.clear();
//            this.predictions.add(predictions);
//            notifyDataSetChanged();
//        }
//
//        @Override
//        public int getCount() {
//            return predictions.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return predictions.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View v = LayoutInflater.from(context).inflate(R.layout.list_item_places, (ViewGroup) convertView, false);
//            TextView txtShortAddress = v.findViewById(R.id.txtShortAddress);
//            TextView txtLongAddress = v.findViewById(R.id.txtLongAddress);
//
//            txtShortAddress.setText(predictions.get(position).getPrimaryText(null));
//            txtLongAddress.setText(predictions.get(position).getSecondaryText(null));
//            return v;
//        }
    }
