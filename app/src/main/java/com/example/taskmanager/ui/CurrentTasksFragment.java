package com.example.taskmanager.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanager.Adapter.TaskAdapter;
import com.example.taskmanager.R;
import com.example.taskmanager.Utility.TaskModel;

import java.util.ArrayList;
import java.util.List;


public class CurrentTasksFragment extends Fragment {
    private RecyclerView taskRecyclerView;
    private TaskAdapter taskAdapter;
    private List<TaskModel> tasksList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_current_tasks, container, false);

        taskRecyclerView =rootView.findViewById(R.id.taskRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        taskAdapter =  new TaskAdapter(this);
        taskRecyclerView.setAdapter(taskAdapter);

        TaskModel task = new TaskModel(0, 0, "Test Task", "Dummy Task", "1",
                "Deadline: 10-5-2024", "10pm");

        tasksList = new ArrayList<>();
        tasksList.add(task);
        tasksList.add(task);
        tasksList.add(task);
        tasksList.add(task);

        taskAdapter.setTasks(tasksList);
        return rootView;
    }
}