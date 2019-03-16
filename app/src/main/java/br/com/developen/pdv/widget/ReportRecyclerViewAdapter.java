package br.com.developen.pdv.widget;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.truizlop.sectionedrecyclerview.SimpleSectionedAdapter;

import androidx.recyclerview.widget.RecyclerView;
import br.com.developen.pdv.R;

public class ReportRecyclerViewAdapter
        extends SimpleSectionedAdapter<ReportRecyclerViewAdapter.ReportItemViewHolder> {

    protected String[][] reports = {{"Meeting", "Phone call", "Interview"},
            {"Basket match", "Grocery shopping", "Taking a nap"}};

    protected String getSectionHeaderTitle(int section) {

        return section == 0 ? "Financeiro" : "Comercial";

    }

    protected int getSectionCount() {
        return 2;
    }

    protected int getItemCountForSection(int section) {
        return 3;
    }

    protected ReportItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.activity_report_row, parent, false);

        return new ReportItemViewHolder(view);

    }

    protected void onBindItemViewHolder(ReportItemViewHolder holder, int section, int position) {

        holder.render(reports[section][position]);

    }

    class ReportItemViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        ReportItemViewHolder(View view) {

            super(view);

            textView = view.findViewById(R.id.activity_report_row_title);

        }

        void render(String text){

            textView.setText(text);

        }

    }

}