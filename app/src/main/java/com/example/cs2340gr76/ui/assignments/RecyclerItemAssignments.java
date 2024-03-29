package com.example.cs2340gr76.ui.assignments;

import android.app.AlertDialog;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs2340gr76.R;
import com.example.cs2340gr76.ui.assignments.Adapter.AssignmentsAdapter;

public class RecyclerItemAssignments extends ItemTouchHelper.SimpleCallback {
    private AssignmentsAdapter adapter;

    public RecyclerItemAssignments(AssignmentsAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getBindingAdapterPosition();
        if (direction == ItemTouchHelper.LEFT) {
            AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
            builder.setTitle("Delete Assignment");
            builder.setMessage("Are you sure you want to delete this assignment?");
            builder.setPositiveButton("Confirm", (dialog, which) -> adapter.deleteItem(position));
            builder.setNegativeButton("Cancel", (dialog, which) -> adapter.notifyItemChanged(viewHolder.getBindingAdapterPosition()));
            AlertDialog dialog = builder.create();
            dialog.show();
            adapter.notifyDataSetChanged();

        } else {
            adapter.editItem(position);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        Drawable icon;
        ColorDrawable background;

        View itemView = viewHolder.itemView;
        int backgroundCornerOffSet = 20;

        if (dX > 0) {
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.edit);
            background = new ColorDrawable(ContextCompat.getColor(adapter.getContext(), R.color.teal_700));
        } else {
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.delete);
            background = new ColorDrawable(Color.RED);
        }

        assert icon != null;
        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

        if (dX > 0) {
            int iconLeft = itemView.getLeft() + iconMargin;
            int iconRight = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) dX) + backgroundCornerOffSet, itemView.getBottom());
        } else if (dX < 0) {
            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffSet, itemView.getTop(),
                    itemView.getRight(), itemView.getBottom());
        } else {
            background.setBounds(0,0,0,0);
        }
        background.draw(c);
        icon.draw(c);
    }
}
