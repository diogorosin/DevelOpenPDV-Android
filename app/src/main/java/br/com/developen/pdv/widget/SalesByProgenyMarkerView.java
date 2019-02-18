package br.com.developen.pdv.widget;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import br.com.developen.pdv.R;

public class SalesByProgenyMarkerView extends MarkerView {

    private TextView tvContent;

    public SalesByProgenyMarkerView(Context context, int layoutResource) {

        super(context, layoutResource);

        tvContent = findViewById(R.id.tv_content);

    }

    public void refreshContent(Entry e, Highlight highlight) {

        tvContent.setText(e.getData().toString());

        super.refreshContent(e, highlight);

    }

    private MPPointF mOffset;

    public MPPointF getOffset() {

        if(mOffset == null) {

           mOffset = new MPPointF(-(getWidth() / 2), -getHeight());

        }

        return mOffset;
    }


}