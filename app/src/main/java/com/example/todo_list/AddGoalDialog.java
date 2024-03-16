package com.example.todo_list;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AddGoalDialog extends AppCompatDialogFragment {

    private EditText goalEditText;
    private OnGoalAddedListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_goal, null);

        goalEditText = view.findViewById(R.id.goal_edit_text);

        builder.setView(view)
                .setTitle("Add Goal")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", null);

        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> {
            Button addButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            addButton.setOnClickListener(v -> {
                String goal = goalEditText.getText().toString().trim();
                if (!goal.isEmpty()) {
                    if(listener != null) {
                        listener.onGoalAdded(goal);
                    }
                    dialog.dismiss();
                } else {
                    goalEditText.setError("Please enter a goal");
                }
            });
        });

        return dialog;
    }

    public void setOnGoalAddedListener(OnGoalAddedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (OnGoalAddedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnGoalAddedListener");
        }
    }

    public interface OnGoalAddedListener {
        void onGoalAdded(String goal);
    }
}
