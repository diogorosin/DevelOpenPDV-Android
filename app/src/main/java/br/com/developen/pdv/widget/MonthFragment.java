package br.com.developen.pdv.widget;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
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
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import br.com.developen.pdv.R;
import br.com.developen.pdv.repository.SaleRepository;
import br.com.developen.pdv.room.SaleDAO;
import br.com.developen.pdv.utils.DB;
import br.com.developen.pdv.utils.StringUtils;

public class MonthFragment extends Fragment {


    private LineChart salesByPeriodChart;

    private BarChart salesByProgenyChart;

    private PieChart salesByUserChart;

    private TextView ticketCountTextView;

    private TextView saleBillingTextView;

    private TextView saleCountTextView;


    public MonthFragment() {}


    public static MonthFragment newInstance() {

        MonthFragment fragment = new MonthFragment();

        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ticketCountTextView = view.findViewById(R.id.fragment_main_tickets_textview);

        saleBillingTextView = view.findViewById(R.id.fragment_main_billing_textview);

        saleCountTextView = view.findViewById(R.id.fragment_main_sales_textview);

        SaleRepository saleRepository = ViewModelProviders.of(this).get(SaleRepository.class);

        saleRepository.getTicketCountOfMonth().observe(MonthFragment.this, new Observer<Integer>() {

            public void onChanged(Integer ticketCountOfMonth) {

                ticketCountTextView.setText(ticketCountOfMonth.toString());

            }

        });

        saleRepository.getSaleCountOfMonth().observe(MonthFragment.this, new Observer<Integer>() {

            public void onChanged(Integer saleCountOfMonth) {

                saleCountTextView.setText(saleCountOfMonth.toString());

            }

        });

        saleRepository.getSaleBillingOfMonth().observe(MonthFragment.this, new Observer<Double>() {

            public void onChanged(Double saleBillingOfMonth) {

                saleBillingTextView.setText(StringUtils.formatCurrencyWithSymbol(saleBillingOfMonth));

            }

        });

        saleRepository.getSalesByPeriodOfMonth().observe(MonthFragment.this, new Observer<List<SaleDAO.SalesByPeriodBean>>() {

            public void onChanged(List<SaleDAO.SalesByPeriodBean> salesByPeriodOfMonth) {

                setSalesByPeriodData(salesByPeriodOfMonth);

            }

        });

        saleRepository.getSalesByProgenyOfMonth().observe(MonthFragment.this, new Observer<List<SaleDAO.SalesByProgenyBean>>() {

            public void onChanged(List<SaleDAO.SalesByProgenyBean> salesByProgenyOfMonth) {

                setSalesByProgenyData(salesByProgenyOfMonth);

            }

        });

        saleRepository.getSalesByUserOfMonth().observe(MonthFragment.this, new Observer<List<SaleDAO.SalesByUserBean>>() {

            public void onChanged(List<SaleDAO.SalesByUserBean> salesByUserOfMonth) {

                setSalesByUserData(salesByUserOfMonth);

            }

        });

        {
            salesByPeriodChart = view.findViewById(R.id.fragment_main_salesbyperiod_chart);

            salesByPeriodChart.setBackgroundColor(Color.argb(0, 42, 42, 42));

            salesByPeriodChart.getDescription().setEnabled(false);

            salesByPeriodChart.setNoDataText("Gráfico indisponível");

            salesByPeriodChart.setNoDataTextColor(Color.rgb(200, 200, 200));

            salesByPeriodChart.setTouchEnabled(true);

            salesByPeriodChart.getLegend().setEnabled(false);

            salesByPeriodChart.setDragEnabled(true);

            salesByPeriodChart.setScaleEnabled(true);

            salesByPeriodChart.setPinchZoom(true);

            salesByPeriodChart.getAxisRight().setEnabled(false);

            XAxis xAxis = salesByPeriodChart.getXAxis();

            xAxis.setTextColor(Color.WHITE);

            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

            xAxis.setDrawGridLines(false);

            xAxis.setGranularity(1f);

            xAxis.setGranularityEnabled(true);

            xAxis.setLabelRotationAngle(-45);

            xAxis.setValueFormatter(new IAxisValueFormatter() {

                public String getFormattedValue(float value, AxisBase axis) {

                    String month = Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());

                    return String.valueOf((int) value) + " " + month.substring(0,1).toUpperCase() + month.substring(1, month.length());

                }

            });

            xAxis.setAxisMinimum(1);

            xAxis.setAxisMaximum(31);

            YAxis yAxis = salesByPeriodChart.getAxisLeft();

            yAxis.setDrawGridLines(true);

            yAxis.setTextColor(Color.WHITE);

            yAxis.setAxisMinimum(0);

            yAxis.setValueFormatter(new IAxisValueFormatter() {

                public String getFormattedValue(float value, AxisBase axis) {

                    return StringUtils.formatCurrencyWithSymbol((double) value);

                }

            });

            salesByPeriodChart.animateY(1000, Easing.EaseInOutBack);

        }

        {

            salesByProgenyChart = view.findViewById(R.id.fragment_main_salesbyprogeny_chart);

            salesByProgenyChart.setBackgroundColor(Color.argb(0, 42, 42, 42));

            salesByProgenyChart.setNoDataText("Gráfico indisponível");

            salesByProgenyChart.setNoDataTextColor(Color.rgb(200, 200, 200));

            salesByProgenyChart.getDescription().setEnabled(false);

            salesByProgenyChart.getLegend().setEnabled(false);

            salesByProgenyChart.setDragEnabled(true);

            salesByProgenyChart.setScaleEnabled(true);

            salesByProgenyChart.setPinchZoom(true);

            salesByProgenyChart.setTouchEnabled(true);

            salesByProgenyChart.setMarker(new SalesByProgenyMarkerView(getContext(), R.layout.fragment_main_markerview));

            salesByProgenyChart.getXAxis().setEnabled(false);

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

            salesByProgenyChart.animateY(1500, Easing.EaseInOutBack);

        }

        {

            salesByUserChart = view.findViewById(R.id.fragment_main_salesbyuser_chart);

            salesByUserChart.getDescription().setEnabled(false);

            salesByUserChart.setNoDataText("Gráfico indisponível");

            salesByUserChart.setNoDataTextColor(Color.rgb(200, 200, 200));

            salesByUserChart.getLegend().setEnabled(false);

            salesByUserChart.setTouchEnabled(true);

            salesByUserChart.setUsePercentValues(true);

            salesByUserChart.setDragDecelerationFrictionCoef(0.95f);

            salesByUserChart.setDrawHoleEnabled(false);

            salesByUserChart.setRotationAngle(0);

            salesByUserChart.setRotationEnabled(true);

            salesByUserChart.setHighlightPerTapEnabled(true);

            salesByUserChart.setEntryLabelTextSize(12f);

            salesByUserChart.animateY(2000, Easing.EaseInOutQuad);

        }

        return view;

    }


    private void setSalesByPeriodData(List<SaleDAO.SalesByPeriodBean> salesByPeriod) {

        if (salesByPeriod!=null && !salesByPeriod.isEmpty()) {

            ArrayList<Entry> values = new ArrayList<>();

            for (SaleDAO.SalesByPeriodBean salesByPeriodBean : salesByPeriod)

                values.add(new Entry(Integer.valueOf(salesByPeriodBean.getPeriod()), salesByPeriodBean.getTotal().floatValue()));

            LineDataSet dataset;

            dataset = new LineDataSet(values, "Dataset");

            dataset.setDrawIcons(false);

            dataset.setColor(Color.WHITE);

            dataset.setCircleColor(Color.WHITE);

            dataset.setLineWidth(1f);

            dataset.setCircleRadius(3f);

            dataset.setDrawCircleHole(true);

            dataset.setFormLineWidth(1f);

            dataset.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));

            dataset.setFormSize(15.f);

            dataset.setValueTextSize(12f);

            dataset.setValueTextColor(Color.WHITE);

            dataset.setValueFormatter(new IValueFormatter() {

                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

                    return StringUtils.formatCurrencyWithSymbol((double) value);

                }

            });

            dataset.setDrawFilled(true);

            dataset.setFillFormatter(new IFillFormatter() {

                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {

                    return salesByPeriodChart.getAxisLeft().getAxisMinimum();

                }

            });

            dataset.setFillColor(Color.WHITE);

            ArrayList<ILineDataSet> datasetList = new ArrayList<>();

            datasetList.add(dataset);

            LineData data = new LineData(datasetList);

            salesByPeriodChart.setData(data);

        }

    }


    private void setSalesByProgenyData(List<SaleDAO.SalesByProgenyBean> salesByProgeny) {

        if (salesByProgeny!=null && !salesByProgeny.isEmpty()) {

            List<BarEntry> values = new ArrayList<>();

            int i = 0;

            for (SaleDAO.SalesByProgenyBean salesByProgenyBean : salesByProgeny)

                values.add(new BarEntry(i++, salesByProgenyBean.getTotal().floatValue(),
                        salesByProgenyBean.getProgeny()));

            BarDataSet dataset;

            dataset = new BarDataSet(values, "Dataset");

            dataset.setDrawIcons(false);

            dataset.setColor(Color.WHITE);

            dataset.setBarShadowColor(Color.WHITE);

            dataset.setFormLineWidth(1f);

            dataset.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));

            dataset.setFormSize(15.f);

            dataset.setValueTextSize(12f);

            dataset.setValueTextColor(Color.WHITE);

            dataset.setDrawValues(false);

            List<IBarDataSet> datasetList = new ArrayList<>();

            datasetList.add(dataset);

            BarData data = new BarData(datasetList);

            salesByProgenyChart.setData(data);

        }

    }

    private void setSalesByUserData(List<SaleDAO.SalesByUserBean> salesByUser) {

        if (salesByUser!=null && !salesByUser.isEmpty()) {

            List<PieEntry> values = new ArrayList<>();

            for (SaleDAO.SalesByUserBean salesByUserBean : salesByUser)

                values.add(new PieEntry(salesByUserBean.getTotal().floatValue(),
                        salesByUserBean.getUser()));

            PieDataSet dataset = new PieDataSet(values, "Dataset");

            dataset.setDrawValues(true);

            dataset.setDrawIcons(false);

            dataset.setSliceSpace(1f);

            dataset.setIconsOffset(new MPPointF(0, 40));

            dataset.setSelectionShift(5f);

            ArrayList<Integer> colors = new ArrayList<>();

            int range = 128 / (salesByUser.size() + 1);

            int red = 254;

            int green = 254;

            int blue = 254;

            for (int i = 0; i < salesByUser.size(); i++) {

                colors.add(Color.rgb(red, green, blue));

                red = red - range;

                green = green - range;

                blue = blue - range;

            }

            dataset.setColors(colors);

            PieData data = new PieData(dataset);

            data.setValueFormatter(new IValueFormatter() {

                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

                    return DB.getInstance(getContext()).
                            userDAO().
                            getUserByIdentifier((Integer) entry.
                                    getData()).getName() + " (" + StringUtils.formatPercentage((double) value) + "%)";

                }

            });

            data.setValueTextSize(11f);

            data.setValueTextColor(Color.BLACK);

            salesByUserChart.setData(data);

            salesByUserChart.highlightValues(null);

            salesByUserChart.invalidate();

        }

    }

}