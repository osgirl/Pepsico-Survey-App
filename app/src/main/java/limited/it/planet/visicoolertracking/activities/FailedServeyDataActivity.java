package limited.it.planet.visicoolertracking.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import limited.it.planet.visicoolertracking.R;
import limited.it.planet.visicoolertracking.adapter.ServeyAdapter;
import limited.it.planet.visicoolertracking.database.LocalStorageDB;
import limited.it.planet.visicoolertracking.util.ServeyModel;

public class FailedServeyDataActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ServeyAdapter mAdapter;
    private List<ServeyModel> searchResult;
    LocalStorageDB localStorageDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failed_servey_data);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        localStorageDB = new LocalStorageDB(FailedServeyDataActivity.this);
        localStorageDB.open();
        searchResult = localStorageDB.checkFailedDataFromTable("failled");;


        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ServeyAdapter(FailedServeyDataActivity.this, searchResult);
        mRecyclerView.setAdapter(mAdapter);
    }
}
