package com.example.faculty_app.mainapp.classes.ui;

import android.os.Bundle;
import android.util.Log;
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
import com.example.faculty_app.mainapp.classes.ClassAdapter;
import com.example.faculty_app.mainapp.classes.data.local.models.ClassDto;
import com.example.faculty_app.mainapp.classes.data.local.ClassesViewModel;
import com.example.faculty_app.mainapp.classes.data.local.models.RuntimeDto;
import com.example.faculty_app.mainapp.classes.services.ScheduleService;
import com.example.faculty_app.shared.ServiceCallback;
import com.example.faculty_app.shared.ServiceResult;

import java.util.ArrayList;

public class CurrentClassWithListFragment extends Fragment {
    TextView runtimeStatus;
    TextView runtimeName;
    TextView runtimeCode;
    TextView runtimeSchedule;
    TextView runtimeRoom;
    TextView seeAll;
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

        loadViews(view);

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
        viewModel.getRuntime().observe(getViewLifecycleOwner(), (this::updateRuntime));

        boolean mustRefresh = viewModel.getClassList().getValue() == null ||
                viewModel.getClassList().getValue().isEmpty();

        if (mustRefresh) {
            loadClasses();
        }

        boolean mustRefreshRuntime = viewModel.getRuntime().getValue() == null ||
                viewModel.getRuntime().getValue().runtimeStatus.equals("offline");

        if (mustRefreshRuntime) {
            loadRuntime();
        }
    }

    private void loadViews(View view) {
        runtimeStatus = view.findViewById(R.id.txt_runtime_status);
        runtimeName = view.findViewById(R.id.txt_runtime_name);
        runtimeCode = view.findViewById(R.id.txt_runtime_code);
        runtimeSchedule = view.findViewById(R.id.txt_runtime_schedule);
        runtimeRoom = view.findViewById(R.id.txt_runtime_room);

        viewModel = new ViewModelProvider(requireActivity()).get(ClassesViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.classesRecyclerView);
        if (getContext() != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        adapter = new ClassAdapter(classList);
        recyclerView.setAdapter(adapter);

        seeAll = view.findViewById(R.id.seeAll);
    }

    private void loadRuntime() {
        ScheduleService.getCurrentOrNextClass(new ServiceCallback<RuntimeDto>() {
            @Override
            public void onResult(ServiceResult<RuntimeDto> result) {
                if (!isAdded())
                    return;

                if (!result.getSuccess()) {
                    Toast.makeText(requireActivity(), result.getMessage(), Toast.LENGTH_LONG)
                         .show();
                    Log.e("CURRENT_CLASS_WITH_LIST_FRAGMENT",
                          "Failed retrieving runtime: " + result.getMessage(),
                          result.getCause());
                }

                var runtime = result.getData();

                viewModel.setRuntime(runtime);
            }
        });
    }

    private void updateRuntime(RuntimeDto runtime) {
        requireActivity().runOnUiThread(() -> {
            runtimeStatus.setText(runtime.runtimeStatus);
            runtimeName.setText(runtime.name);
            runtimeCode.setText(runtime.code);
            runtimeSchedule.setText(runtime.time);
            runtimeRoom.setText(runtime.room);
        });
    }

    private void loadClasses() {
        ScheduleService.getClassList(new ServiceCallback<ArrayList<ClassDto>>() {
            @Override
            public void onResult(ServiceResult<ArrayList<ClassDto>> result) {
                if (!isAdded())
                    return;

                boolean success = result.getSuccess();
                ArrayList<ClassDto> data = result.getData();

                if (!success) {
                    Toast.makeText(requireActivity(), result.getMessage(), Toast.LENGTH_LONG)
                         .show();
                    return;
                }

                Log.d("CURRENT_CLASS_WITH_LIST_FRAGMENT", "Success retrieving classes.");
                viewModel.setClassList(data);
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