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

public class AllClassesFragment extends Fragment {

    public AllClassesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the layout with the header and search bar
        View view = inflater.inflate(R.layout.fragment_all_classes, container, false);

        // Initialize the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.allClassesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //full list of classes
        List<Home_Page.ClassModel> allClassList = new ArrayList<>();

        // all subjects
        allClassList.add(new Home_Page.ClassModel("#606 · DS-3202", "Machine Learning", "7:00 AM - 9:00 AM | AV 308b"));
        allClassList.add(new Home_Page.ClassModel("#607 · CS-301", "Automata Theory", "10:00 AM - 12:00 PM | RM 402"));
        allClassList.add(new Home_Page.ClassModel("#608 · OS-101", "Operating Systems", "1:00 PM - 3:00 PM | LB 204"));
        allClassList.add(new Home_Page.ClassModel("#609 · DS-3203", "Data Science", "3:00 PM - 5:00 PM | AV 308b"));
        allClassList.add(new Home_Page.ClassModel("#610 · IT-402", "Network Security", "8:00 AM - 10:00 AM | Lab 1"));
        allClassList.add(new Home_Page.ClassModel("#611 · CS-202", "Data Structures", "10:00 AM - 12:00 PM | RM 302"));

        // Set Adapter
        // Reusing the adapter from Home_Page
        Home_Page.ClassAdapter adapter = new Home_Page.ClassAdapter(allClassList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}