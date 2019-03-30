package br.com.developen.pdv.widget;


import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.view.ActionMode;
import br.com.developen.pdv.R;
import br.com.developen.pdv.activity.SaleActivity;

public class SaleToolbarActionModeCallback implements ActionMode.Callback {


    private SaleRecyclerViewPagerAdapter adapter;

    private SaleActivity activity;


    public SaleToolbarActionModeCallback(SaleRecyclerViewPagerAdapter adapter, SaleActivity activity) {

        this.adapter = adapter;

        this.activity = activity;

    }


    public boolean onCreateActionMode(ActionMode mode, Menu menu) {

        mode.getMenuInflater().inflate(R.menu.activity_sale_menu, menu);

        return true;

    }


    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

        menu.findItem(R.id.activity_sale_menu_cancelsale).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return true;

    }


    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

        switch (item.getItemId()) {

            case R.id.activity_sale_menu_cancelsale:

                activity.cancelSales();

                break;

        }

        return false;

    }


    public void onDestroyActionMode(ActionMode mode) {

        adapter.removeSelection();

        activity.setNullToActionMode();

    }


}