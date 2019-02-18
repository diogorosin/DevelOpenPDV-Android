package br.com.developen.pdv.widget;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
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

    private BarChart salesByProgenyChart;

    private PieChart salesByUserChart;

    private List<String> salesByProgenyChartLegend = new ArrayList<>();


    public MainTodayFragment() {}


    public static MainTodayFragment newInstance() {

        MainTodayFragment fragment = new MainTodayFragment();

        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        SaleRepository saleRepository = ViewModelProviders.of(this).get(SaleRepository.class);

        saleRepository.getSalesByPeriodOfToday().observe(MainTodayFragment.this, new Observer<List<SaleDAO.SalesByPeriodBean>>() {

            public void onChanged(List<SaleDAO.SalesByPeriodBean> salesByPeriodBeans) {

                setSalesByPeriodData(salesByPeriodBeans);

            }

        });

        saleRepository.getSalesByProgenyOfToday().observe(MainTodayFragment.this, new Observer<List<SaleDAO.SalesByProgenyBean>>() {

            public void onChanged(List<SaleDAO.SalesByProgenyBean> salesByProgenyBeans) {

                setSalesByProgenyData(salesByProgenyBeans);

            }

        });

        {
            salesByPeriodChart = view.findViewById(R.id.fragment_main_salesbyperiod_chart);

            salesByPeriodChart.setBackgroundColor(Color.argb(0, 42, 42, 42));

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

            xAxis.setGranularityEnabled(true);

            xAxis.setLabelRotationAngle(-45);

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

        }

        {

            salesByProgenyChart = view.findViewById(R.id.fragment_main_salesbyprogeny_chart);

            salesByProgenyChart.setBackgroundColor(Color.argb(0, 42, 42, 42));

            salesByProgenyChart.getDescription().setEnabled(false);

            salesByProgenyChart.getLegend().setEnabled(false);

            salesByProgenyChart.setDragEnabled(true);

            salesByProgenyChart.setScaleEnabled(true);

            salesByProgenyChart.setPinchZoom(true);

            salesByProgenyChart.setTouchEnabled(true);

            salesByProgenyChart.setMarker(new SalesByProgenyMarkerView(getContext(), R.layout.tvcontent));

            salesByProgenyChart.getXAxis().setEnabled(false);

            /*XAxis xAxis = salesByProgenyChart.getXAxis();

            xAxis.setTextColor(Color.WHITE);

            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

            xAxis.setDrawGridLines(false);

            xAxis.setGranularity(1);

            xAxis.setGranularityEnabled(true);

            xAxis.setLabelRotationAngle(-90);

            xAxis.setCenterAxisLabels(false);

            xAxis.setValueFormatter(new IAxisValueFormatter() {

                public String getFormattedValue(float value, AxisBase axis) {

                    String label = salesByProgenyChartLegend.get((int) value);

                    if (label.length() > 10)

                        return label.substring(0, 10) + "...";

                    else

                        return label;

                }

            }); */


            YAxis yAxis = salesByProgenyChart.getAxisLeft();

            salesByProgenyChart.getAxisRight().setEnabled(false);

            yAxis.setDrawGridLines(true);

            yAxis.setTextColor(Color.WHITE);

            yAxis.setAxisMinimum(0);

            yAxis.setValueFormatter(new IAxisValueFormatter() {

                public String getFormattedValue(float value, AxisBase axis) {

                    return StringUtils.formatCurrencyWithSymbol((double) value);

                }

            });


        }

        return view;

    }


    private void setSalesByPeriodData(List<SaleDAO.SalesByPeriodBean> todaySales) {

        ArrayList<Entry> values = new ArrayList<>();

        for (SaleDAO.SalesByPeriodBean salesByPeriodBean : todaySales)

            values.add(new Entry(Integer.valueOf(salesByPeriodBean.getPeriod()), salesByPeriodBean.getTotal().floatValue()));

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


    private void setSalesByProgenyData(List<SaleDAO.SalesByProgenyBean> salesByProgeny) {

        ArrayList<BarEntry> values = new ArrayList<>();

        int i = 0;

        for (SaleDAO.SalesByProgenyBean salesByProgenyBean : salesByProgeny) {

            values.add(new BarEntry(i, salesByProgenyBean.getTotal().floatValue(), salesByProgenyBean.getProgeny()));

            salesByProgenyChartLegend.add(i, salesByProgenyBean.getProgeny());

            i++;

        }

        BarDataSet set1;

        if (salesByProgenyChart.getData() != null &&
                salesByProgenyChart.getData().getDataSetCount() > 0) {

            set1 = (BarDataSet) salesByProgenyChart.getData().getDataSetByIndex(0);

            set1.setValues(values);

            set1.notifyDataSetChanged();

            salesByProgenyChart.getData().notifyDataChanged();

            salesByProgenyChart.notifyDataSetChanged();

        } else {

            set1 = new BarDataSet(values, "DataSet 1");

            set1.setDrawIcons(false);

            set1.setColor(Color.WHITE);

            set1.setBarShadowColor(Color.WHITE);

            set1.setFormLineWidth(1f);

            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));

            set1.setFormSize(15.f);

            set1.setValueTextSize(12f);

            set1.setValueTextColor(Color.WHITE);

            set1.setDrawValues(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();

            dataSets.add(set1);

            BarData data = new BarData(dataSets);

            salesByProgenyChart.setData(data);

        }

    }





}