package net.sf.sprockets.samples.app.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.sf.sprockets.app.Fragments;
import net.sf.sprockets.app.ui.PanesActivity;
import net.sf.sprockets.app.ui.SprocketsFragment;
import net.sf.sprockets.samples.R;
import net.sf.sprockets.samples.databinding.PaneBinding;

public class PanesSampleActivity extends PanesActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDefaultContentView();
    }

    @Override
    public Fragment getFragment(int pane) {
        return PaneFragment.newInstance(pane);
    }

    public static class PaneFragment extends SprocketsFragment {
        private static final String PANE = "pane";

        private static PaneFragment newInstance(int pane) {
            PaneFragment frag = new PaneFragment();
            Bundle args = Fragments.arguments(frag);
            args.putInt(PANE, pane);
            return frag;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
            return inflater.inflate(R.layout.pane, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            PaneBinding.bind(view).text
                    .setText(getString(R.string.pane, getArguments().getInt(PANE)));
        }
    }
}
