package com.example.taskmanager.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanager.Activity.ModifyTaskActivity;
import com.example.taskmanager.Database.RoomDB;
import com.example.taskmanager.R;
import com.example.taskmanager.Utility.TaskModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.fragment.app.Fragment;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<TaskModel> tasksList;
    private final Fragment tasksFragment;
    private final RoomDB instance;

    public TaskAdapter(Fragment fragment) {
        this.tasksFragment = fragment;
        instance = RoomDB.getInstance(tasksFragment.getContext());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {

        TaskModel task = tasksList.get(position);

        holder.title.setText(task.getTitle());

        if (!task.getCurrentDate().equals("Not defined")) {
            Context context = tasksFragment.requireContext();
            holder.deadline.setText(String.format(context.getString(R.string.deadline), task.getCurrentDate()));
        }

        holder.checkBox.setChecked(toBoolean(task.getStatus()));
        holder.checkBox.setOnClickListener(v ->changeTaskStatus(task, v));

        if (task.getEditable() && task.getStatus() == 0)  {
            holder.editBtn.setOnClickListener(v -> goTo_ModifyActivity(task));
        } else {
            holder.editBtn.setVisibility(View.INVISIBLE);
        }

        holder.deleteBtn.setOnClickListener(v -> deleteTask(position));
        holder.cardView.setOnClickListener(v -> goTo_ViewActivity(task));
    }

    @Override
    public int getItemCount() {
        int size;

        if (tasksList == null) {
            size = 0;
        } else {
            size = tasksList.size();
        }
        return size;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTasks(List<TaskModel> tasksList) {
        this.tasksList = tasksList;
        notifyDataSetChanged();
    }

    private void changeTaskStatus(TaskModel task, View v) {
        if (((CheckBox) v).isChecked()) {
            task.setStatus(1);
        } else {
            task.setStatus(0);
        }

        new Thread(() -> instance.taskDAO().updateTask(task)).start();
    }

    private void goTo_ModifyActivity(TaskModel task) {
        Intent intent = new Intent(tasksFragment.getActivity(), ModifyTaskActivity.class);

        intent.putExtra("task", task);

        String activityType = "Modify";
        intent.putExtra("activityType", activityType);

        tasksFragment.startActivity(intent);
    }

    private void goTo_ViewActivity(TaskModel task) {
        Intent intent = new Intent(tasksFragment.getActivity(), ModifyTaskActivity.class);

        intent.putExtra("task", task);

        String activityType = "View";
        intent.putExtra("activityType", activityType);

        tasksFragment.startActivity(intent);
    }

    private void deleteTask(int position) {
        // Remove item from dataset
        TaskModel task = tasksList.get(position);


        new Thread(() -> instance.taskDAO().deleteTask(task)).start();

        // Notify adapter of item removal
        notifyItemRemoved(position);
        // Prevents the app from closing after deleting all all the tasks
        notifyItemRangeChanged(position, tasksList.size());
    }

    private boolean toBoolean(int num) {
        return num != 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView title;
        TextView deadline;
        FloatingActionButton editBtn;
        FloatingActionButton deleteBtn;
        CardView cardView;

        ViewHolder(View view) {
            super(view);
            checkBox = view.findViewById(R.id.checkBox);
            title = view.findViewById(R.id.taskNameView);
            deadline = view.findViewById(R.id.deadlineTextView);
            editBtn = view.findViewById(R.id.editTaskFab);
            deleteBtn = view.findViewById(R.id.deleteTaskFab);
            cardView = view.findViewById(R.id.taskCard);
        }

    }
}
