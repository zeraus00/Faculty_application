package com.example.faculty_app.mainapp.classes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faculty_app.R;
import com.example.faculty_app.mainapp.classes.data.local.models.ClassDto;
import com.example.faculty_app.mainapp.classes.data.local.ClassesViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment responsible for displaying all classes.
 * Follows the structural pattern of Fragment_Notification for consistency.
 */
public class AllClassesFragment extends Fragment {

    private ClassAdapter adapter;
    private final List<ClassDto> classList = new ArrayList<>();

    public AllClassesFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate fragment_all_classes.xml
        return inflater.inflate(R.layout.fragment_all_classes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ClassesViewModel viewModel =
                new ViewModelProvider(requireActivity()).get(ClassesViewModel.class);
        // 1. Initialize Views (Matching the pattern in Fragment_Notification)
        RecyclerView recyclerView = view.findViewById(R.id.allClassesRecyclerView);

        // 2. Setup Layout Manager
        if (getContext() != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            // Important: Disable nested scrolling if using NestedScrollView in XML
            recyclerView.setNestedScrollingEnabled(false);
        }

        // 3. Set the Adapter defined in Home_Page
        adapter = new ClassAdapter(classList);
        recyclerView.setAdapter(adapter);

        viewModel.getClassList().observe(getViewLifecycleOwner(), this::updateClassList);

    }

    private void updateClassList(ArrayList<ClassDto> dto) {
        classList.clear();
        if (dto != null)
            classList.addAll(dto);
        adapter.notifyDataSetChanged();
    }
}