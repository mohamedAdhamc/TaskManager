package com.example.taskmanager.ui;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.taskmanager.Adapter.TaskAdapter;
import com.example.taskmanager.Database.RoomDB;
import com.example.taskmanager.R;

public class CompletedTaskFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_completed_task, container, false);

        RecyclerView taskRecyclerView = rootView.findViewById(R.id.taskRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        TaskAdapter taskAdapter =  new TaskAdapter(this);
        taskRecyclerView.setAdapter(taskAdapter);

        RoomDB instance = RoomDB.getInstance(requireContext());
        instance.taskDAO().getAllCompletedTasks().observe(getViewLifecycleOwner(), taskAdapter::setTasks);

        return rootView;
    }
}