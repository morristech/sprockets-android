package net.sf.sprockets.samples.app.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.sf.sprockets.app.ui.SprocketsActivity;
import net.sf.sprockets.samples.R;
import net.sf.sprockets.samples.databinding.ListBinding;
import net.sf.sprockets.widget.ResourceAdapter;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class SamplesActivity extends SprocketsActivity {
    static final int[] sLabels =
            {R.string.app_bar_floating_action_button_behavior, R.string.auto_grid_layout_manager,
                    R.string.panes_activity, R.string.sprockets_preference_fragment,
                    R.string.translate_image_page_change_listener,
                    R.string.user_header_navigation_fragment, R.string.test_activity};
    static final Class<?>[] sActivities =
            {AppBarFabBehaviorActivity.class, AutoGridActivity.class, PanesSampleActivity.class,
                    SettingsActivity.class, TranslateImageActivity.class,
                    UserHeaderNavigationActivity.class, TestActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListBinding bind = DataBindingUtil.setContentView(this, R.layout.list);
        bind.list.setLayoutManager(new LinearLayoutManager(this));
        bind.list.addItemDecoration(new DividerItemDecoration(this, VERTICAL));
        bind.list.setAdapter(
                new ResourceAdapter<SamplesHolder>(android.R.layout.simple_list_item_1) {
                    @Override
                    public int getItemCount() {
                        return sLabels.length;
                    }

                    @Override
                    public SamplesHolder onCreateViewHolder(ViewGroup parent, int type, View view) {
                        return new SamplesHolder(SamplesActivity.this, view);
                    }

                    @Override
                    public void onBindViewHolder(SamplesHolder holder, int position) {
                        holder.mView.setText(getString(sLabels[position]));
                    }
                });
    }

    class SamplesHolder extends ViewHolder {
        final TextView mView;

        SamplesHolder(Context context, View view) {
            super(view);
            mView = (TextView) view;
            mView.setOnClickListener(
                    v -> startActivity(new Intent(context, sActivities[getAdapterPosition()])));
        }
    }
}
