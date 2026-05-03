package com.example.faculty_app.mainapp.attendance;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faculty_app.R;
import com.example.faculty_app.mainapp.attendance.adapters.ClassAttendanceAdapter;
import com.example.faculty_app.mainapp.attendance.data.local.models.classattendance.ClassAttendanceModel;
import com.example.faculty_app.mainapp.attendance.data.local.models.classattendance.ClassAttendanceViewModel;
import com.example.faculty_app.mainapp.attendance.data.local.models.sessions.SessionsModel;
import com.example.faculty_app.mainapp.attendance.services.AttendanceService;
import com.example.faculty_app.shared.ServiceCallback;
import com.example.faculty_app.shared.ServiceResult;

import java.util.List;

public class ClassAttendanceFragment extends Fragment {
    private int classId;
    private ClassAttendanceAdapter adapter;
    private ClassAttendanceViewModel viewModel;
    private ClassAttendanceModel classAttendance;
    private TextView txtTotal, txtPresent, txtAbsent, btnEdit;
    private boolean isEditMode = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_class_attendance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadBundle();
        loadViews(view);

        if (classId == -1) {
            Toast.makeText(requireActivity(), "Something went wrong.", Toast.LENGTH_LONG).show();
            Log.e("CLASS_ATTENDANCE_FRAGMENT", "Failed getting class id from bundle arguments.");
        }

        btnEdit.setOnClickListener(v -> {
            isEditMode = !isEditMode;

            if (isEditMode) {
                btnEdit.setText("Save & Submit");
                btnEdit.setBackgroundResource(R.drawable.bg_save_button);
                btnEdit.setTextColor(Color.WHITE);
            }
            else {
                btnEdit.setText("Edit");
                btnEdit.setBackgroundResource(R.drawable.bg_edit_button);
                btnEdit.setTextColor(Color.parseColor("#7A67F8"));

                //  todo: update ui here
                Toast.makeText(
                             getContext(),
                             "Attendance Submitted Successfully!",
                             Toast.LENGTH_SHORT
                              )

                     .show();
            }

            adapter.setEditMode(isEditMode);
        });
        viewModel.getSessions().observe(getViewLifecycleOwner(), (this::updateSessions));

        viewModel.getClassAttendance()
                 .observe(getViewLifecycleOwner(), (this::updateClassAttendance));


        var value = viewModel.getSessions().getValue();
        boolean refreshData = value == null || value.sessions.isEmpty();

        if (refreshData)
            loadSessions(classId);
    }

    private void loadBundle() {
        Bundle bundle = getArguments();

        if (bundle == null) {
            classId = -1;
            Toast.makeText(requireActivity(), "Something went wrong.", Toast.LENGTH_LONG).show();
            Log.e("CLASS_ATTENDANCE_FRAGMENT", "Failed getting bundle arguments.");
            return;
        }

        classId = bundle.getInt("classId", -1);
    }

    private void loadViews(View view) {
        txtTotal = view.findViewById(R.id.txtTotal);
        txtPresent = view.findViewById(R.id.txtPresent);
        txtAbsent = view.findViewById(R.id.txtAbsent);
        btnEdit = view.findViewById(R.id.btnEdit);

        viewModel = new ViewModelProvider(requireActivity()).get(ClassAttendanceViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.studentRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ClassAttendanceAdapter(this, classAttendance);
        recyclerView.setAdapter(adapter);
    }

    private void loadSessions(int classId) {
        AttendanceService.getClassSessions(
                classId, new ServiceCallback<SessionsModel>() {
                    @Override
                    public void onResult(ServiceResult<SessionsModel> result) {
                        if (!isAdded())
                            return;

                        if (!result.getSuccess()) {
                            Toast.makeText(
                                         requireActivity(),
                                         result.getMessage(),
                                         Toast.LENGTH_LONG
                                          )
                                 .show();
                            Log.e(
                                    "CLASS_ATTENDANCE_FRAGMENT",
                                    "Failed retrieving class attendance: " + result.getMessage(),
                                    result.getCause()
                                 );
                        }

                        var sessions = result.getData();

                        viewModel.setSessions(sessions);
                    }
                }
                                          );
    }

    private void updateSessions(SessionsModel data) {
        var recentId = data.sessions.getLast().id;

        loadClassAttendance(recentId);
    }

    private void loadClassAttendance(int sessionId) {
        AttendanceService.getClassAttendance(
                sessionId, new ServiceCallback<ClassAttendanceModel>() {
                    @Override
                    public void onResult(ServiceResult<ClassAttendanceModel> result) {
                        if (!isAdded())
                            return;

                        if (!result.getSuccess()) {
                            Toast.makeText(
                                         requireActivity(),
                                         result.getMessage(),
                                         Toast.LENGTH_LONG
                                          )
                                 .show();
                            Log.e(
                                    "CLASS_ATTENDANCE_FRAGMENT",
                                    "Failed retrieving class attendance: " + result.getMessage(),
                                    result.getCause()
                                 );
                        }

                        var classAttendance = result.getData();

                        viewModel.setClassAttendance(classAttendance);
                    }
                }
                                            );
    }

    private void updateClassAttendance(ClassAttendanceModel data) {
        requireActivity().runOnUiThread(() -> {
            classAttendance = data;
            var summary = data.getSummary();

            txtTotal.setText(summary.getTotal());
            txtPresent.setText(summary.getPresent());
            txtAbsent.setText(summary.getAbsent());
        });
    }
}