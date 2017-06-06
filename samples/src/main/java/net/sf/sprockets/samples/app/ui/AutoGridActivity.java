package net.sf.sprockets.samples.app.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.widget.TextView;

import net.sf.sprockets.app.ui.SprocketsActivity;
import net.sf.sprockets.samples.R;
import net.sf.sprockets.samples.databinding.ListBinding;
import net.sf.sprockets.samples.widget.ItemNumberAdapter;
import net.sf.sprockets.widget.AutoGridLayoutManager;
import net.sf.sprockets.widget.HeaderFooterSpanSizeLookup;

public class AutoGridActivity extends SprocketsActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListBinding bind = DataBindingUtil.setContentView(this, R.layout.list);
        AutoGridLayoutManager layout = new AutoGridLayoutManager(this, R.dimen.grid_item_size);
        HeaderFooterSpanSizeLookup spans = new HeaderFooterSpanSizeLookup(bind.list, 1, 1);
        layout.setSpanSizeLookup(spans);
        bind.list.setLayoutManager(layout);
        bind.list.setAdapter(new ItemNumberAdapter() { // add header/footer
            @Override
            public int getItemCount() {
                return super.getItemCount() + spans.getHeaderFooterCount();
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position - spans.getHeaderCount());
                if (spans.isHeaderOrFooter(position)) {
                    ((TextView) holder.itemView).setText(getString(
                            spans.isHeader(position) ? R.string.header : R.string.footer));
                }
            }
        });
    }
}
