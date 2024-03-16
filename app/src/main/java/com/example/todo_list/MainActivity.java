package com.example.todo_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddGoalDialog.OnGoalAddedListener {

    private RecyclerView recyclerView;
    private List<String> goalsList;
    private GoalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        goalsList = new ArrayList<>();
        adapter = new GoalAdapter(goalsList);
        recyclerView.setAdapter(adapter);

        ImageButton addGoalButton = findViewById(R.id.add_goal_button);
        addGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddGoalDialog();
            }
        });
    }

    private void openAddGoalDialog() {
        AddGoalDialog dialog = new AddGoalDialog();
        dialog.setOnGoalAddedListener(this); // Set the listener to the MainActivity
        dialog.show(getSupportFragmentManager(), "Add Goal Dialog");
    }

    @Override
    public void onGoalAdded(String goal) {
        goalsList.add(goal);
        adapter.notifyDataSetChanged();
    }

    private class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalViewHolder> {

        private List<String> goals;

        public GoalAdapter(List<String> goals) {
            this.goals = goals;
        }

        @NonNull
        @Override
        public GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_goal, parent, false);
            return new GoalViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull GoalViewHolder holder, int position) {
            final String goal = goals.get(position);
            holder.goalText.setText(goal);

            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = holder.getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        deleteGoal(adapterPosition);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return goals.size();
        }

        public void deleteGoal(int position) {
            if (position >= 0 && position < goals.size()) {
                goals.remove(position);
                notifyDataSetChanged();
            }
        }

        public class GoalViewHolder extends RecyclerView.ViewHolder {
            TextView goalText;
            ImageButton deleteButton;

            public GoalViewHolder(@NonNull View itemView) {
                super(itemView);
                goalText = itemView.findViewById(R.id.goal_text);
                deleteButton = itemView.findViewById(R.id.delete_button);
            }
        }
    }
}
