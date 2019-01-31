package br.com.developen.pdv.widget;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SalePagerAdapter extends FragmentPagerAdapter {


    private Integer sale;


    public SalePagerAdapter(FragmentManager fm, Integer sale) {

        super(fm);

        this.sale = sale;

    }


    public Fragment getItem(int position) {

        switch (position){

            case 0:

                return SaleItemFragment.newInstance(sale);

            case 1:

                return SaleReceiptFragment.newInstance(sale);

            default:

                return null;

        }

    }


    public int getCount() {

        return 2;

    }


    public CharSequence getPageTitle(int position) {

        switch (position){

            case 0:

                return "Itens da Venda";

            case 1:

                return "Recebimentos";

            default:

                return "Indefinido";

        }

    }


}