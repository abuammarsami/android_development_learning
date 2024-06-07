package com.ammar.tablayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter {

        private int numOftabs;
        public MyPagerAdapter(@NonNull FragmentManager fm, int numOftabs) {
            super(fm);
            this.numOftabs = numOftabs;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    return new ChatFragment();
                case 1:
                    return new StatusFragment();
                case 2:
                    return new CallsFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return numOftabs;
        }

        // Returns the title of the tab according to the position.
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "Chat";
                case 1:
                    return "Status";
                case 2:
                    return "Calls";
                default:
                    return null;
            }
        }
}
