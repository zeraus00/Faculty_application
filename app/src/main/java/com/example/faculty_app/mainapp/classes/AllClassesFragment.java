package com.example.faculty_app.mainapp.classes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faculty_app.R;
import com.example.faculty_app.mainapp.home.HomePageActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment responsible for displaying all classes.
 * Follows the structural pattern of Fragment_Notification for consistency.
 */
public class AllClassesFragment extends Fragment {

    private RecyclerView recyclerView;
    private HomePageActivity.ClassAdapter adapter;
    private List<HomePageActivity.ClassModel> allClassList;

    public AllClassesFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate fragment_all_classes.xml
        View view = inflater.inflate(R.layout.fragment_all_classes, container, false);

        // 1. Initialize Views (Matching the pattern in Fragment_Notification)
        recyclerView = view.findViewById(R.id.allClassesRecyclerView);

        // 2. Setup Layout Manager
        if (getContext() != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            // Important: Disable nested scrolling if using NestedScrollView in XML
            recyclerView.setNestedScrollingEnabled(false);
        }

        // 3. Populate data list using the Model defined in Home_Page
        allClassList = new ArrayList<>();
        allClassList.add(new HomePageActivity.ClassModel("#606 · DS-3202", "Machine Learning", "7:00 AM - 9:00 AM | AV 308b"));
        allClassList.add(new HomePageActivity.ClassModel("#607 · CS-301", "Automata Theory", "10:00 AM - 12:00 PM | RM 402"));
        allClassList.add(new HomePageActivity.ClassModel("#608 · OS-101", "Operating Systems", "1:00 PM - 3:00 PM | LB 204"));
        allClassList.add(new HomePageActivity.ClassModel("#609 · DS-3203", "Data Science", "3:00 PM - 5:00 PM | AV 308b"));
        allClassList.add(new HomePageActivity.ClassModel("#610 · IT-402", "Network Security", "8:00 AM - 10:00 AM | Lab 1"));
        allClassList.add(new HomePageActivity.ClassModel("#611 · CS-202", "Data Structures", "10:00 AM - 12:00 PM | RM 302"));

        // 4. Set the Adapter defined in Home_Page
        adapter = new HomePageActivity.ClassAdapter(allClassList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}