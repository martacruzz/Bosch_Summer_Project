package com.example.projetoveroippa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerListener;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.example.projetoveroippa.databinding.FragmentCallCenterBinding;
import com.example.projetoveroippa.databinding.FragmentMainMenuBinding;
import com.example.projetoveroippa.object.Praesensa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class CallCenter extends Fragment {
    private FragmentCallCenterBinding binding;
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json");

    public CallCenter() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CallCenter newInstance(String param1, String param2) {
        CallCenter fragment = new CallCenter();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCallCenterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*String[] arraySpinner = new String[]{"Zone 1", "Zone 3"};
        Spinner spinner = (Spinner) binding.spinner;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);*/

        Spinner spinnerMessage = (Spinner) binding.spinnerMessages;
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Praesensa.arrayMensagens);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMessage.setAdapter(adapter2);
        if (DataBase.activeEvent!=null){
            binding.spinnerMessages.setSelection(DataBase.activeEvent.message);
        }

        List<KeyPairBoolData> multiSpinner = new ArrayList<>();
        multiSpinner.add(new KeyPairBoolData(getString(R.string.zone_1), false));
        multiSpinner.add(new KeyPairBoolData(getString(R.string.zone_2), false));
        multiSpinner.add(new KeyPairBoolData(getString(R.string.zone_3), false));
        multiSpinner.add(new KeyPairBoolData(getString(R.string.zona4), false));

        MultiSpinnerSearch multiSelectSpinnerWithSearch = binding.multipleItemSelectionSpinner;
        multiSelectSpinnerWithSearch.setSearchEnabled(true);
        multiSelectSpinnerWithSearch.setSearchHint(getString(R.string.select_zones));
        multiSelectSpinnerWithSearch.setEmptyTitle("Not Data Found!");
        multiSelectSpinnerWithSearch.setShowSelectAllButton(true);
        multiSelectSpinnerWithSearch.setClearText(getString(R.string.close_clear));

        ArrayList<String> selectedZones = new ArrayList<>();


        multiSelectSpinnerWithSearch.setItems(multiSpinner, new MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> selectedItems) {
                selectedZones.clear();
                for (int i = 0; i < selectedItems.size(); i++) {
                    if (selectedItems.get(i).isSelected()) {
                        selectedZones.add(selectedItems.get(i).getName());
                    }
                }

            }
        });

        SeekBar seekBar = (SeekBar) binding.seekBarPriority;

        binding.seekBarPriority.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                TextView textViewPriority = binding.textViewPriority;
                textViewPriority.setText("Priority of " + String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.makeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedZones.isEmpty()) {
                    Toast.makeText(getContext(), getString(R.string.no_zones_selected), Toast.LENGTH_SHORT).show();
                } else {
                    String json = "{\"Priority\":" + seekBar.getProgress() + ",\"Zones\":" + zonesToString(selectedZones) + ",\"Message\":\"" + spinnerMessage.getSelectedItem() + "\"}";
                    post("https://praesensasummerprojectovar.azurewebsites.net/api/praesensa/prascl-0ea50e-ctrl.local/call", json);
                }


            }
        });

        binding.floatingActionButtonEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(CallCenter.this).navigate(R.id.action_callCenter_to_mainMenuEvents);
            }
        });

    }

    String post(String url, String json) {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("ApiKey", "AIzaSyCoUDdflZ7cTQJXtxr3LntWlfFtvtAS0lk")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (Exception e) {
            Toast.makeText(getContext(),getString(R.string.internet_connection), Toast.LENGTH_SHORT).show();
            return "Error";
        }

    }

    public String zonesToString(ArrayList<String> selectedZones) {
        String zonesConvertedToJson = "[";
        for (int i = 0; i < selectedZones.size() - 1; i++) {
            zonesConvertedToJson = zonesConvertedToJson + "\"" + selectedZones.get(i) + "\",";
        }
        zonesConvertedToJson = zonesConvertedToJson + "\"" + selectedZones.get(selectedZones.size() - 1) + "\"";
        zonesConvertedToJson = zonesConvertedToJson + "]";
        return zonesConvertedToJson;
    }


}












