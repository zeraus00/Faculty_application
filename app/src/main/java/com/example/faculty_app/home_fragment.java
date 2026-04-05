package com.example.faculty_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView; // Import TextView

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class home_fragment extends Fragment {

    public home_fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_fragment, container, false);

        // Handle the See All Click
        TextView seeAll = view.findViewById(R.id.seeAll);
        seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to AllClassesFragment
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new AllClassesFragment()) // Ensure ID matches your MainActivity container
                        .addToBackStack(null) // Allows user to press back to return home
                        .commit();
            }
        });

        // 2. Initialize the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.classesRecyclerView);
        if (getContext() != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        // 3. Populate data
        List<Home_Page.ClassModel> classList = new ArrayList<>();
        classList.add(new Home_Page.ClassModel("#606 · DS-3202", "Machine Learning", "7:00 AM - 9:00 AM | AV 308b"));
        classList.add(new Home_Page.ClassModel("#607 · CS-301", "Automata Theory", "10:00 AM - 12:00 PM | RM 402"));
        classList.add(new Home_Page.ClassModel("#608 · OS-101", "Operating Systems", "1:00 PM - 3:00 PM | LB 204"));

        // 4. Set the Adapter
        Home_Page.ClassAdapter adapter = new Home_Page.ClassAdapter(classList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}