package com.example.motibook;

public class StatisticsData {
    int totalNum;
    int[] data;

    public StatisticsData() {
        totalNum = 0;
        data = new int[]{0,0,0,0,0,0,0,0,0,0};
    }
    public StatisticsData(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7, int arg8, int arg9) {
        totalNum = arg0 + arg1 + arg2 + arg3 + arg4 + arg5 + arg6 + arg7 + arg8 + arg9;
        data = new int[]{arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9};
    }
    public void totalNumUpdate() {
        totalNum = data[0] + data[1] + data[2] + data[3] + data[4] + data[5] + data[6] + data[7] + data[8] + data[9];
    }
}
