package com.example.projetoveroippa;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.projetoveroippa.databinding.FragmentMainMenuEventsBinding;
import com.example.projetoveroippa.databinding.FragmentQrCodeBinding;
import com.example.projetoveroippa.object.Pokemon;
import com.google.gson.Gson;
import com.google.zxing.Result;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;




public class qrCodeFragment extends Fragment {

    private CodeScanner mCodeScanner;

    private SensorManager sensorManager = null;
    private Float acceleration = 0f;
    private Float currentAcceleration = 0f;
    private Float lastAcceleration = 0f;

    private Pokemon pokemon = null;


    private FragmentQrCodeBinding binding;

    public qrCodeFragment() {
        // Required empty public constructor
    }


    public static qrCodeFragment newInstance(String param1, String param2) {
        qrCodeFragment fragment = new qrCodeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);

        Objects.requireNonNull(sensorManager)
            .registerListener(sensorListener, sensorManager
            .getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        acceleration = 10f;
        currentAcceleration = SensorManager.GRAVITY_EARTH;
        lastAcceleration = SensorManager.GRAVITY_EARTH;



    }
    private SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            Float x = sensorEvent.values[0];
            Float y = sensorEvent.values[1];
            Float z = sensorEvent.values[2];
            lastAcceleration = currentAcceleration;

            // Getting current accelerations
            // with the help of fetched x,y,z values
            Float currentAcceleration = (float) Math.sqrt((x * x + y * y + z * z));
            Float delta = currentAcceleration - lastAcceleration;
            acceleration = acceleration * 0.9f + delta;

            // Display a Toast message if
            // acceleration value is over 12
            if (acceleration > 12) {
                //Toast.makeText(getContext(), "Shake event detected", Toast.LENGTH_SHORT).show();
                if(pokemon != null){
                    binding.nameTv.setText(pokemon.name);
                    binding.descriptionTv.setText(pokemon.description);
                    Picasso.get().load(pokemon.url).into(binding.imageView3);
                }

            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }

    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        binding = FragmentQrCodeBinding.inflate(inflater, container, false);

        final Activity activity = getActivity();

        mCodeScanner = new CodeScanner(activity, binding.scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, result.getText(), Toast.LENGTH_SHORT).show();
                        pokemon = getPokemon("http://192.168.88.76:3000/pokemons/" + result.getText()); //MUDAR IP VER NAS DEFINIÇÕES DA NET
                        Picasso.get().load("https://www.emp.de/dw/image/v2/BBQV_PRD/on/demandware.static/-/Sites-master-emp/default/dw3d0bb8f3/images/4/8/6/6/486631a.jpg?sfrm=png").into(binding.imageView3);

                    }
                });
            }
        });
        binding.scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

        return binding.getRoot();


    }

    @Override
    public void onResume() {
        mCodeScanner.startPreview();
        sensorManager.registerListener(sensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();

    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        sensorManager.unregisterListener(sensorListener);
        super.onPause();
    }



    Pokemon getPokemon(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("ApiKey", "AIzaSyCoUDdflZ7cTQJXtxr3LntWlfFtvtAS0lk")
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            Gson gson = new Gson();
            if(response.isSuccessful()){
                Pokemon pokemon = gson.fromJson(response.body().string(), Pokemon.class);
                return pokemon;
            }

        } catch (Exception e) {
            Toast.makeText(getContext(),getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();

        }
        return new Pokemon();
    }

}

