package com.example.projetoveroippa;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.projetoveroippa.databinding.FragmentCalendarBinding;
import com.example.projetoveroippa.databinding.FragmentSetNewPasswordBinding;
import com.example.projetoveroippa.object.Event;
import com.example.projetoveroippa.object.Praesensa;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class CalendarFragment extends Fragment {
    private FragmentCalendarBinding binding;
    int hour, minute;
    int month, day, year;

    public CalendarFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
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
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonHourPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popTimePicker(view);
            }
        });
        binding.buttonDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popDatePicker(view);
            }
        });
        binding.buttonAddNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = "";
                String noDate = "Select Date";
                String noTime = "Select Time";
                boolean validator = true;
                Event newEvent = new Event();
                newEvent.name = binding.editTextTextEventNameCreateEvent.getText().toString();
                newEvent.date = binding.buttonDatePicker.getText().toString();
                newEvent.hour = binding.buttonHourPicker.getText().toString();
                newEvent.description = binding.editTextTextEventDescriptionCreateEvent.getText().toString();
                newEvent.message = binding.spinnerMessages2.getSelectedItemPosition();


                if (newEvent.name.equals(s) || newEvent.date.equals(noDate) || newEvent.hour.equals(noTime) || newEvent.description.equals(s)) {
                    Toast.makeText(getContext(), getString(R.string.smth_missing), Toast.LENGTH_SHORT).show();
                    validator = false;
                } else if (!afterTodayDateCheck(newEvent.date, newEvent.hour)) {
                    Toast.makeText(getContext(),getString(R.string.date_not_valid), Toast.LENGTH_SHORT).show();
                } else if (validator) {
                    Toast.makeText(getContext(), getString(R.string.event_created), Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(CalendarFragment.this).navigate(R.id.action_calendarFragment_to_mainMenuEvents);
                    DataBase.utilizadorAtivo.events.add(newEvent);
                    DataBase.saveData(getContext());
                }

            }
        });
        Spinner spinnerMessage = (Spinner) binding.spinnerMessages2;
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Praesensa.arrayMensagens);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMessage.setAdapter(adapter2);
    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                binding.buttonHourPicker.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };
        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), style, onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle(getString(R.string.select_time));
        timePickerDialog.show();
    }

    public void popDatePicker(View view) {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                month = selectedMonth + 1;
                day = selectedDay;
                year = selectedYear;
                binding.buttonDatePicker.setText(day + "/" + month + "/" + year);
            }
        };
        year = 2023;
        int style = AlertDialog.THEME_HOLO_DARK;
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), style, onDateSetListener, year, month, day);
        datePickerDialog.setTitle(getString(R.string.select_date));
        datePickerDialog.show();
    }

    public static boolean afterTodayDateCheck (String date, String hour) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");


        try {
            Date DateEvent1 = sdf.parse(date + " " + hour);
            Date DateEvent2 = new Date();
            if (DateEvent1.after(DateEvent2)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return false;

    }
}
