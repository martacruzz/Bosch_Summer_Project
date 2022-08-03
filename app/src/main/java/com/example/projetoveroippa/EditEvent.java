package com.example.projetoveroippa;

import static com.example.projetoveroippa.CalendarFragment.afterTodayDateCheck;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.projetoveroippa.databinding.FragmentEditEventBinding;
import com.example.projetoveroippa.databinding.FragmentFotgotPasswordBinding;
import com.example.projetoveroippa.object.Praesensa;

import java.util.Locale;


public class EditEvent extends Fragment {
    private FragmentEditEventBinding binding;
    int hour, minute;
    int month, day, year;

    public EditEvent() {
        // Required empty public constructor
    }

    public static EditEvent newInstance(String param1, String param2) {
        EditEvent fragment = new EditEvent();
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
        binding = FragmentEditEventBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonHourPickerEditEvent2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popTimePicker(view);
            }
        });
        binding.buttonDatePickerEditEvent2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popDatePicker(view);
            }
        });
        Spinner spinnerMessage = (Spinner) binding.spinnerMessagesEditEvent2;
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Praesensa.arrayMensagens);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMessage.setAdapter(adapter4);

        binding.editTextTextEventNameEditEvent2.setText(DataBase.activeEvent.name);
        binding.editTextTextEventDescriptionEditEvent2.setText(DataBase.activeEvent.description);
        binding.buttonDatePickerEditEvent2.setText(DataBase.activeEvent.date);
        binding.buttonHourPickerEditEvent2.setText(DataBase.activeEvent.hour);
        binding.spinnerMessagesEditEvent2.setSelection(DataBase.activeEvent.message);

        binding.buttonEditEvent2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean validator = true;
                String description = binding.editTextTextEventDescriptionEditEvent2.getText().toString();
                String name = binding.editTextTextEventNameEditEvent2.getText().toString();
                String date =binding.buttonDatePickerEditEvent2.getText().toString();
                String time =binding.buttonHourPickerEditEvent2.getText().toString();
                int message = binding.spinnerMessagesEditEvent2.getSelectedItemPosition();

                if (name.equals("") || description.equals("")) {
                    Toast.makeText(getContext(), getString(R.string.smth_missing), Toast.LENGTH_SHORT).show();
                    validator = false;
                } else if (!afterTodayDateCheck(date, time)) {
                    Toast.makeText(getContext(), getString(R.string.date_not_valid), Toast.LENGTH_SHORT).show();
                } else if (validator) {
                    Toast.makeText(getContext(), getString(R.string.event_created), Toast.LENGTH_SHORT).show();
                    DataBase.activeEvent.name = name;
                    DataBase.activeEvent.description = description;
                    DataBase.activeEvent.date = date;
                    DataBase.activeEvent.hour = time;
                    DataBase.activeEvent.message = message;
                    DataBase.saveData(getContext());
                    NavHostFragment.findNavController(EditEvent.this).navigate(R.id.action_editEvent_to_mainMenuEvents);

                }

            }
        });
    }
    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                binding.buttonHourPickerEditEvent2.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
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
                binding.buttonDatePickerEditEvent2.setText(day + "/" + month + "/" + year);
            }
        };
        year = 2023;
        int style = AlertDialog.THEME_HOLO_DARK;
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), style, onDateSetListener, year, month, day);
        datePickerDialog.setTitle(getString(R.string.select_date));
        datePickerDialog.show();
    }
}
