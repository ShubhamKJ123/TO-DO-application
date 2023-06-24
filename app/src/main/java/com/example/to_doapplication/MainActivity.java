package com.example.to_doapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Task> tasks;
    private TaskAdapter adapter;
    private EditText editTextTask;
    private ListView listViewTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasks = new ArrayList<>();
        adapter = new TaskAdapter();

        editTextTask = findViewById(R.id.editTextTask);
        listViewTasks = findViewById(R.id.listViewTasks);
        listViewTasks.setAdapter(adapter);

        Button buttonAddTask = findViewById(R.id.buttonAddTask);
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });
    }

    private void addTask() {
        String taskName = editTextTask.getText().toString().trim();
        if (!taskName.isEmpty()) {
            Task task = new Task(taskName);
            tasks.add(task);
            adapter.notifyDataSetChanged();
            editTextTask.setText("");
        } else {
            Toast.makeText(this, "Please enter a task.", Toast.LENGTH_SHORT).show();
        }
    }

    private class TaskAdapter extends ArrayAdapter<Task> {

        public TaskAdapter() {
            super(MainActivity.this, R.layout.list_item_task, tasks);
        }

        @Override

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_task, parent, false);
            }

            Task currentTask = getItem(position);

            TextView textViewTask = convertView.findViewById(R.id.textViewTask);
            textViewTask.setText(currentTask.getName());

            ImageView imageViewPriority = convertView.findViewById(R.id.imageViewPriority);
            if (currentTask.isHighPriority()) {
                imageViewPriority.setImageResource(R.drawable.ic_priority_high);
            } else {
                imageViewPriority.setImageResource(R.drawable.ic_priority_low);
            }

            ImageView imageViewDelete = convertView.findViewById(R.id.imageViewDelete);
            imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tasks.remove(currentTask);
                    notifyDataSetChanged();
                }
            });

            return convertView;


    }
    }

    private class Task {
        private String name;
        private boolean highPriority;

        public Task(String name) {
            this.name = name;
            this.highPriority = false;
        }

        public String getName() {
            return name;
        }

        public boolean isHighPriority() {
            return highPriority;
        }
    }
}
