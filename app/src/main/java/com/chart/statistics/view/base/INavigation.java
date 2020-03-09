package com.chart.statistics.view.base;

import androidx.fragment.app.Fragment;

public interface INavigation {
    void showFragment(Fragment fragment);
    void backPressed();
}