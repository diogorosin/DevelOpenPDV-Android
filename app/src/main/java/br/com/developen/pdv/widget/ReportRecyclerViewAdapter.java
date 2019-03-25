package br.com.developen.pdv.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.truizlop.sectionedrecyclerview.SimpleSectionedAdapter;

import androidx.recyclerview.widget.RecyclerView;
import br.com.developen.pdv.R;

public class ReportRecyclerViewAdapter
        extends SimpleSectionedAdapter<ReportRecyclerViewAdapter.ReportItemViewHolder> {


    public static final int SALES_BY_PROGENY_REPORT = 1;

    public static final int SALES_BY_CATALOG_REPORT = 2;

    public static final int SALES_BY_USER_REPORT = 3;

    public static final int SALEABLES_REPORT = 4;


    private String[][] reports = {{"Vendas por Produto/Serviço", "Vendas por Categoria", "Vendas por Usuário"},
            {"Relação de Produtos/Serviços"}};


    private View.OnClickListener salesByProgenyOnClickListener;

    private View.OnClickListener salesByCatalogOnClickListener;

    private View.OnClickListener salesByUserOnClickListener;

    private View.OnClickListener saleablesOnClickListener;


    public ReportRecyclerViewAdapter(View.OnClickListener salesByProgenyOnClickListener,
                                     View.OnClickListener salesByCatalogOnClickListener,
                                     View.OnClickListener salesByUserOnClickListener,
                                     View.OnClickListener saleablesOnClickListener){

        this.salesByProgenyOnClickListener = salesByProgenyOnClickListener;

        this.salesByCatalogOnClickListener = salesByCatalogOnClickListener;

        this.salesByUserOnClickListener = salesByUserOnClickListener;

        this.saleablesOnClickListener = saleablesOnClickListener;

    }


    protected String getSectionHeaderTitle(int section) {

        return section == 0 ? "Faturamento" : "Estoque";

    }


    protected int getSectionCount() {
        return 2;
    }


    protected int getItemCountForSection(int section) {

        return section == 0 ? 3 : 1;

    }


    protected ReportItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.activity_report_row, parent, false);

        return new ReportItemViewHolder(view);

    }


    public int getSectionItemViewType(int section, int position) {

        switch (section){

            case 0: {

                switch (position){

                    case 0: return SALES_BY_PROGENY_REPORT;

                    case 1: return SALES_BY_CATALOG_REPORT;

                    case 2: return SALES_BY_USER_REPORT;

                }

            }

            case 1: {

                switch (position){

                    case 0: return SALEABLES_REPORT;

                }

            }

        }

        return 0;

    }


    protected void onBindItemViewHolder(ReportItemViewHolder holder, int section, int position) {

        holder.render(reports[section][position]);

        switch (holder.getItemViewType()){

            case SALES_BY_PROGENY_REPORT: holder.itemView.setOnClickListener(salesByProgenyOnClickListener);
                break;

            case SALES_BY_CATALOG_REPORT: holder.itemView.setOnClickListener(salesByCatalogOnClickListener);
                break;

            case SALES_BY_USER_REPORT: holder.itemView.setOnClickListener(salesByUserOnClickListener);
                break;

            case SALEABLES_REPORT: holder.itemView.setOnClickListener(saleablesOnClickListener);
                break;

        }

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