package com.ammar.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {
    // Data that needs to be preserved across configuration changes
    private int counter = 0;

    private MutableLiveData<Integer> countLiveData = new MutableLiveData<>();


//    //Whe the app first launched
//    public int getInitialCounter() {
//        return counter;
//    }


    //Whe the app first launched
    public MutableLiveData<Integer> getInitialCounter() {
        countLiveData.setValue(counter);
        return countLiveData;
    }


//    // When user clicks the button
//    public int getCounter() {
//        counter++;
//        return counter;
//    }

    // When user clicks the button
    public void getCounter(){
        counter++;
        countLiveData.setValue(counter);
    }



}
