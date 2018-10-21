package com.nutomic.syncthingandroid.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.nutomic.syncthingandroid.R;
import com.nutomic.syncthingandroid.views.TipListAdapter;
import com.nutomic.syncthingandroid.views.TipListAdapter.ItemClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Holds a RecyclerView that shows tips and tricks.
 */
public class TipsAndTricksActivity extends SyncthingActivity {

    private static final String TAG = "TipsAndTricksActivity";

    private RecyclerView mRecyclerView;
    private TipListAdapter mTipListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_and_tricks);
        mRecyclerView = findViewById(R.id.tip_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mTipListAdapter = new TipListAdapter(this);

        /**
         * Determine the app's private data folder on external storage if present.
         * e.g. "/storage/abcd-efgh/Android/[PACKAGE_NAME]/files"
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ArrayList<File> externalFilesDir = new ArrayList<>();
            externalFilesDir.addAll(Arrays.asList(getExternalFilesDirs(null)));
            externalFilesDir.remove(getExternalFilesDir(null));
            if (externalFilesDir.size() > 0) {
                String absExternalStorageAppFilesPath = externalFilesDir.get(0).getAbsolutePath();
                mTipListAdapter.add(getString(R.string.tip_write_to_sdcard_title),
                        getString(R.string.tip_write_to_sdcard_text, absExternalStorageAppFilesPath));
            }
        }

        // Fill tip title and text content.
        mTipListAdapter.add(getString(R.string.tip_sync_on_local_network_title), getString(R.string.tip_sync_on_local_network_text));
        mTipListAdapter.add(getString(R.string.tip_custom_sync_conditions_title), getString(R.string.tip_custom_sync_conditions_text));

        // Set onClick listener and add adapter to recycler view.
        mTipListAdapter.setOnClickListener(
            new ItemClickListener() {
                @Override
                public void onItemClick(View view, String itemTitle, String itemText) {
                    Log.v(TAG, "User clicked item with title \'" + itemTitle + "\'");
                    /**
                     * Future improvement:
                     * Collapse texts to the first three lines and open a DialogFragment
                     * if the user clicks an item from the tip list.
                     */
                }
            }
        );
        mRecyclerView.setAdapter(mTipListAdapter);
    }

}
