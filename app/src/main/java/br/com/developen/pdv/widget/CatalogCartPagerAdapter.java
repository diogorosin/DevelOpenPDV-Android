package br.com.developen.pdv.widget;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class CatalogCartPagerAdapter extends FragmentPagerAdapter {

    public CatalogCartPagerAdapter(FragmentManager fm) {

        super(fm);

    }

    public Fragment getItem(int position) {

        switch (position){

            case 0:

                return CatalogCartItemFragment.newInstance();

            case 1:

                return CatalogCartReceiptFragment.newInstance();

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