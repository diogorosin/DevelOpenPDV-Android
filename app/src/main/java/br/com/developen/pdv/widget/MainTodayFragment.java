package br.com.developen.pdv.widget;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import br.com.developen.pdv.R;
import br.com.developen.pdv.repository.SaleRepository;
import br.com.developen.pdv.room.SaleDAO;
import br.com.developen.pdv.utils.StringUtils;

public class MainTodayFragment extends Fragment {


    private LineChart salesByPeriodChart;


    public MainTodayFragment() {}


    public static MainTodayFragment newInstance() {

        MainTodayFragment fragment = new MainTodayFragment();

        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_main_today, container, false);

        SaleRepository saleRepository = ViewModelProviders.of(this).get(SaleRepository.class);

        saleRepository.getTodaySales().observe(MainTodayFragment.this, new Observer<List<SaleDAO.TodaySalesBean>>() {

            public void onChanged(List<SaleDAO.TodaySalesBean> todaySalesBeans) {

                setData(todaySalesBeans);

            }

        });

        salesByPeriodChart = view.findViewById(R.id.fragment_main_today_salesbyperiod_chart);

        salesByPeriodChart.setBackgroundColor(Color.argb(0,42,42,42));

        salesByPeriodChart.getDescription().setEnabled(false);

        salesByPeriodChart.setTouchEnabled(true);

        salesByPeriodChart.getLegend().setEnabled(false);

        salesByPeriodChart.setDragEnabled(true);

        salesByPeriodChart.setScaleEnabled(true);

        salesByPeriodChart.setPinchZoom(true);

        XAxis xAxis = salesByPeriodChart.getXAxis();

        xAxis.setTextColor(Color.WHITE);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setDrawGridLines(false);

        xAxis.setGranularity(1f);

        xAxis.setValueFormatter(new IAxisValueFormatter() {

            public String getFormattedValue(float value, AxisBase axis) {

                long millis = TimeUnit.HOURS.toMillis((long) value);

                return StringUtils.formatShortTime(new Date(millis));

            }

        });

        xAxis.setAxisMinimum(0);

        xAxis.setAxisMaximum(24);

        YAxis yAxis = salesByPeriodChart.getAxisLeft();

        salesByPeriodChart.getAxisRight().setEnabled(false);

        yAxis.setDrawGridLines(true);

        yAxis.setTextColor(Color.WHITE);

        yAxis.setAxisMinimum(0);

        yAxis.setValueFormatter(new IAxisValueFormatter() {

            public String getFormattedValue(float value, AxisBase axis) {

                return StringUtils.formatCurrencyWithSymbol((double) value);

            }

        });

        return view;

    }


    private void setData(List<SaleDAO.TodaySalesBean> todaySales) {

        ArrayList<Entry> values = new ArrayList<>();

        for (SaleDAO.TodaySalesBean todaySalesBean: todaySales)

            values.add(new Entry(Integer.valueOf(todaySalesBean.getHour()), todaySalesBean.getTotal().floatValue()));

        LineDataSet set1;

        if (salesByPeriodChart.getData() != null &&
                salesByPeriodChart.getData().getDataSetCount() > 0) {

            set1 = (LineDataSet) salesByPeriodChart.getData().getDataSetByIndex(0);

            set1.setValues(values);

            set1.notifyDataSetChanged();

            salesByPeriodChart.getData().notifyDataChanged();

            salesByPeriodChart.notifyDataSetChanged();

        } else {

            set1 = new LineDataSet(values, "DataSet 1");

            set1.setDrawIcons(false);

            set1.setColor(Color.WHITE);

            set1.setCircleColor(Color.WHITE);

            set1.setLineWidth(1f);

            set1.setCircleRadius(3f);

            set1.setDrawCircleHole(true);

            set1.setFormLineWidth(1f);

            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));

            set1.setFormSize(15.f);

            set1.setValueTextSize(12f);

            set1.setValueTextColor(Color.WHITE);

            set1.setValueFormatter(new IValueFormatter() {

                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

                    return StringUtils.formatCurrencyWithSymbol((double) value);

                }

            });

            set1.setDrawFilled(true);

            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {

                    return salesByPeriodChart.getAxisLeft().getAxisMinimum();

                }
            });

            set1.setFillColor(Color.WHITE);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();

            dataSets.add(set1);

            LineData data = new LineData(dataSets);

            salesByPeriodChart.setData(data);

        }

    }


}