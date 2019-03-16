package br.com.developen.pdv.activity;

import android.os.Bundle;

import com.truizlop.sectionedrecyclerview.SectionedSpanSizeLookup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.developen.pdv.R;
import br.com.developen.pdv.widget.ReportRecyclerViewAdapter;

public class ReportActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_report);

        Toolbar toolbar = findViewById(R.id.activity_report_toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.report);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.activity_report_recyclerview);

        ReportRecyclerViewAdapter adapter = new ReportRecyclerViewAdapter();

        recyclerView.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        SectionedSpanSizeLookup lookup = new SectionedSpanSizeLookup(adapter, layoutManager);

        layoutManager.setSpanSizeLookup(lookup);

        recyclerView.setLayoutManager(layoutManager);

    }

}