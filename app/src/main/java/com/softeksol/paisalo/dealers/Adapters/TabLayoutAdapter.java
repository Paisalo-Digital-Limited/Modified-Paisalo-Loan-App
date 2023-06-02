package com.softeksol.paisalo.dealers.Adapters;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.softeksol.paisalo.dealers.AddProductBankFrags.AddBankFragment;
import com.softeksol.paisalo.dealers.AddProductBankFrags.AddProductFragment;

public class TabLayoutAdapter extends FragmentPagerAdapter {

    Context mContext;
    int mTotalTabs;
    int OEMid;

    public TabLayoutAdapter(Context context , FragmentManager fragmentManager , int totalTabs,int OEMid) {
        super(fragmentManager);
        mContext = context;
        mTotalTabs = totalTabs;
        this.OEMid=OEMid;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Log.d("asasas" , position + "");
        switch (position) {
            case 0:
                return new AddProductFragment(OEMid);
            case 1:
                return new AddBankFragment(OEMid);
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return mTotalTabs;
    }
}