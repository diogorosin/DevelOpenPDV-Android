package br.com.developen.pdv.widget;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import br.com.developen.pdv.room.CatalogModel;

public class CatalogPagerAdapter extends FragmentPagerAdapter {

    private List<CatalogModel> catalogs = new ArrayList<>();

    public CatalogPagerAdapter(FragmentManager fm) {

        super(fm);

    }

    public SaleItemFragment getItem(int catalogPosition) {

        return SaleItemFragment.newInstance(catalogPosition);

    }

    public int getCount() {

        return catalogs.size();

    }

    public CharSequence getPageTitle(int position) {

        return catalogs.get(position).getDenomination();

    }

    public void setCatalogs(List<CatalogModel> catalogs){

        this.catalogs = catalogs;

        notifyDataSetChanged();

    }

}