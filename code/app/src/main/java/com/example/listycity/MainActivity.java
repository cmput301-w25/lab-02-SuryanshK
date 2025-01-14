package com.example.listycity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // variables
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    String selectedCity = null;         // var to store currently selected city

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // init and setting Content View to this View
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // innit the cityList by finding the id
        cityList = findViewById(R.id.city_list);

        // innit add and delete buttons
        Button addCityBtn = findViewById(R.id.add_city_button);
        Button deleteCityBtn = findViewById(R.id.delete_city_button);

        // init string data
        // Declare a string array consisting of cities which can be fed into the ‘ListView’ later.
        // apparently there's some distinction between '' vs "" (string vs String)?
        String []cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};
        // This will contain the data (the string array of cities).
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));
        // Add the data(string array containing city names) to the ‘dataList’
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        // connect the ‘ListView’ to the ‘ArrayAdapter’(cityAdapter) which will show each ‘TextView’ in the form of a scrolling list.
        cityList.setAdapter(cityAdapter);


        // Set up item click listener for the ListView (to select cities for deletion)
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedCity = dataList.get(position);  // store the clicked city
            Toast.makeText(MainActivity.this, selectedCity + " Selected", Toast.LENGTH_SHORT).show();
        });


        // Setup add city btn
        addCityBtn.setOnClickListener(v -> showAddCityDialog());

        //setup delete city btn
        deleteCityBtn.setOnClickListener(v -> {
            if (selectedCity != null) {                 // if there is a selected city
                dataList.remove(selectedCity);          // remove it from the datalist
                cityAdapter.notifyDataSetChanged();     // tell adapter some data has been changed
                selectedCity = null;                    // reset selectedCity to nothing
                Toast.makeText(MainActivity.this, "City Deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Please Select a City to Delete", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void showAddCityDialog() {
        AlertDialog.Builder bob_builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.city_input, null);
        EditText cityEditText = dialogView.findViewById(R.id.city_edit_text);

        bob_builder.setView(dialogView)
                .setTitle("Add City")
                .setPositiveButton("CONFIRM", (dialog, id) -> {
                    String cityName = cityEditText.getText().toString().trim();
                    if (!cityName.isEmpty()) {
                        dataList.add(cityName);         // add the new name to the dataList
                        cityAdapter.notifyDataSetChanged();     // teel adapter some data changed
                    }
                })
                .setNegativeButton("CANCEL", (dialog, id) -> dialog.cancel());

        AlertDialog dialog = bob_builder.create();
        dialog.show();
    }
}