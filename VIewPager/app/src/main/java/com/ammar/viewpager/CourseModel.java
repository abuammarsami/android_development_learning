package com.ammar.viewpager;

public enum CourseModel {
    // The enum values are the courses that will be displayed in the ViewPager2.
    RED(R.string.red, R.layout.view_red),
    BLUE(R.string.blue, R.layout.view_blue),
    GREEN(R.string.green, R.layout.view_green);

    private int mTitleID;
    private int mLayoutID;

    CourseModel(int titleID, int layoutID) {
        mTitleID = titleID;
        mLayoutID = layoutID;
    }

    public int getTitleID() {
        return mTitleID;
    }

    public int getLayoutID() {
        return mLayoutID;
    }


}
