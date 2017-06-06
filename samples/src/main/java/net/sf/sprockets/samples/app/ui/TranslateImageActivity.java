package net.sf.sprockets.samples.app.ui;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.sf.sprockets.app.Fragments;
import net.sf.sprockets.app.ui.SprocketsActivity;
import net.sf.sprockets.app.ui.SprocketsFragment;
import net.sf.sprockets.samples.R;
import net.sf.sprockets.samples.databinding.PageNumberBinding;
import net.sf.sprockets.samples.databinding.TranslateImageBinding;
import net.sf.sprockets.view.TranslateImagePageChangeListener;

public class TranslateImageActivity extends SprocketsActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TranslateImageBinding bind = DataBindingUtil.setContentView(this, R.layout.translate_image);
        bind.pager.setAdapter(new PageAdapter());
        bind.pager.addOnPageChangeListener(
                new TranslateImagePageChangeListener(bind.pager, bind.image));
    }

    private class PageAdapter extends FragmentPagerAdapter {
        private PageAdapter() {
            super(getFragmentManager());
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(position + 1);
        }
    }

    public static class PageFragment extends SprocketsFragment {
        private static final String PAGE = "page";

        private static PageFragment newInstance(int page) {
            PageFragment frag = new PageFragment();
            Bundle args = Fragments.arguments(frag);
            args.putInt(PAGE, page);
            return frag;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
            return inflater.inflate(R.layout.page_number, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            PageNumberBinding.bind(view).page
                    .setText(getString(R.string.page_n, getArguments().getInt(PAGE)));
        }
    }
}
