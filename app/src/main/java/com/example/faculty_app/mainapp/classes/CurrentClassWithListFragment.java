package com.example.faculty_app.mainapp.classes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView; // Import TextView
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faculty_app.R;
import com.example.faculty_app.mainapp.classes.data.local.models.ClassDto;
import com.example.faculty_app.mainapp.classes.data.local.models.ClassesViewModel;
import com.example.faculty_app.mainapp.classes.data.repositories.ClassDtoListCallback;
import com.example.faculty_app.mainapp.classes.data.repositories.ScheduleRepository;

import java.util.ArrayList;

public class CurrentClassWithListFragment extends Fragment {
    private ClassesViewModel viewModel;
    private final ArrayList<ClassDto> classList = new ArrayList<>();
    private ClassAdapter adapter;


    public CurrentClassWithListFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_current_class_with_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        viewModel = new ViewModelProvider(requireActivity()).get(ClassesViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.classesRecyclerView);
        if (getContext() != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        adapter = new ClassAdapter(classList);
        recyclerView.setAdapter(adapter);

        // Handle the See All Click
        TextView seeAll = view.findViewById(R.id.seeAll);
        seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to AllClassesFragment
                getParentFragmentManager().beginTransaction()
                                          .replace(R.id.fragment_container,
                                                   new AllClassesFragment()) // Ensure ID matches
                                          // your MainActivity container
                                          .addToBackStack(null) // Allows user to press back to
                                          // return home
                                          .commit();
            }
        });

        viewModel.getClassesToday().observe(getViewLifecycleOwner(), (this::updateClassList));

        boolean mustRefresh = viewModel.getClassList().getValue() == null ||
                viewModel.getClassList().getValue().isEmpty();

        if (mustRefresh) {
            loadClasses();
        }
    }

    private void loadClasses() {
        ScheduleRepository.fetchClassDtoList(new ClassDtoListCallback() {
            @Override
            public void onSuccess(ArrayList<ClassDto> dto) {
                if (!isAdded())
                    return;
                viewModel.setClassList(dto);
            }

            @Override
            public void onFail(String message) {
                Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateClassList(ArrayList<ClassDto> dto) {
        classList.clear();
        if (dto != null)
            classList.addAll(dto);
        adapter.notifyDataSetChanged();
    }
}