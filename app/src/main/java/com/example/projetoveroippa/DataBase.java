package com.example.projetoveroippa;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.core.content.ContextCompat;

import com.example.projetoveroippa.object.Event;
import com.example.projetoveroippa.object.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DataBase {
    private static ArrayList<User> users;
    static User utilizadorAtivo;
    static  Event activeEvent;
/*
    public static ArrayList<Event> events = new ArrayList<>();
*/

    private static ArrayList<User> loadData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("contas", null);
        Type type = new TypeToken<ArrayList<User>>() {
        }.getType();
        users = gson.fromJson(json, type);
        if (users == null) {
            users = new ArrayList<>();
        }
        String jsonActiveUser = sharedPreferences.getString("userAtivo", null);
        Type type2 = new TypeToken<User>() {
        }.getType();
        utilizadorAtivo=gson.fromJson(jsonActiveUser, type2);
       // loadDataEvents(context);
        return users;
    }

     public static void saveData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(users);
        editor.putString("contas", json);
        String jsonActiveUser = gson.toJson(utilizadorAtivo);
        editor.putString("userAtivo", jsonActiveUser);
        editor.apply();
        //saveDataEvents(context);
    }
   /* private static void saveDataEvents(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(events);
        editor.putString("Events", json);
        editor.apply();
    }*/
    /*private static void loadDataEvents (Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Events", null);
        Type type = new TypeToken<ArrayList<Event>>() {
        }.getType();
        events = gson.fromJson(json, type);
        if (events == null) {
            events = new ArrayList<>();
        }
    }*/

    public static ArrayList<User> getUsers(Context context) {
        if (users == null) {
            return loadData(context);
        } else {
            return users;
        }
    }

    public static void addAndSaveUser(Context context, User user) {
        users.add(user);
        saveData(context);
    }

}

