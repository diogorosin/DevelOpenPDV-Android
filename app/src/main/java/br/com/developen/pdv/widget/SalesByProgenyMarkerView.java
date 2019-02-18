package br.com.developen.pdv.widget;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import br.com.developen.pdv.R;
import br.com.developen.pdv.room.SaleableModel;
import br.com.developen.pdv.utils.App;
import br.com.developen.pdv.utils.DB;

public class SalesByProgenyMarkerView extends MarkerView {

    private TextView tvContent;

    private MPPointF mOffset;

    public SalesByProgenyMarkerView(Context context, int layoutResource) {

        super(context, layoutResource);

        tvContent = findViewById(R.id.fragment_main_markerview_textview);

    }

    public void refreshContent(Entry e, Highlight highlight) {

        SaleableModel saleableModel = DB.getInstance(App.getInstance()).
                saleableDAO().
                getSaleable((Integer) e.getData());

        tvContent.setText(saleableModel.getLabel());

        super.refreshContent(e, highlight);

    }

    public MPPointF getOffset() {

        if(mOffset == null) {

           mOffset = new MPPointF(-(getWidth() / 2), -getHeight());

        }

        return mOffset;
    }

}