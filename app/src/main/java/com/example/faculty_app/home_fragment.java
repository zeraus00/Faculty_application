package com.example.faculty_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment responsible for displaying the Professor's Dashboard.
 * Renamed to match your existing file: home_fragment.java
 */
public class home_fragment extends Fragment {

    public home_fragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Updated to inflate your specific XML: fragment_home_fragment.xml
        View view = inflater.inflate(R.layout.fragment_home_fragment, container, false);

        // 1. Initialize the RecyclerView from fragment_home_fragment.xml
        RecyclerView recyclerView = view.findViewById(R.id.classesRecyclerView);

        if (getContext() != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        // 2. Populate data using the Model defined in Home_Page
        List<Home_Page.ClassModel> classList = new ArrayList<>();
        classList.add(new Home_Page.ClassModel("#606 · DS-3202", "Machine Learning", "7:00 AM - 9:00 AM | AV 308b"));
        classList.add(new Home_Page.ClassModel("#607 · CS-301", "Automata Theory", "10:00 AM - 12:00 PM | RM 402"));
        classList.add(new Home_Page.ClassModel("#608 · OS-101", "Operating Systems", "1:00 PM - 3:00 PM | LB 204"));

        // 3. Set the Adapter defined in Home_Page
        Home_Page.ClassAdapter adapter = new Home_Page.ClassAdapter(classList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}