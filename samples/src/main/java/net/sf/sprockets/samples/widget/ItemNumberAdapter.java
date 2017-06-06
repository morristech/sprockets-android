package net.sf.sprockets.samples.widget;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.sf.sprockets.widget.ResourceAdapter;

import static android.view.Gravity.CENTER;

public class ItemNumberAdapter extends ResourceAdapter<ViewHolder> {
    public ItemNumberAdapter() {
        super(android.R.layout.simple_list_item_1);
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, View view) {
        return new ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView view = (TextView) holder.itemView;
        view.setText(String.valueOf(position + 1));
        view.setGravity(CENTER);
    }
}
