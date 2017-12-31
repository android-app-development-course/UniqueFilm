package com.example.lyz.uniquefilm.Analysis;

import com.example.lyz.uniquefilm.Information.BoxInfo;

import java.util.ArrayList;

/**
 * Created by lyz on 17-12-28.
 */

public class BoxGsonBean {

    public String success;
    public dataInfo data;


    public class dataInfo{
        public String updateInfo;
        public String totalBoxUnitInfo;
        public String splitTotalBox;
        public String serverTimestamp;
        public cryInfo crystal;
        public String totalBoxInfo;
        public ArrayList<BoxInfo> list;
        public String totalBoxUnit;
        public String totalBox;
        public String splitTotalBoxUnit;
        public String queryDate;
        public String serverTime;
        public String splitTotalBoxUnitInfo;
        public String splitTotalBoxInfo;

        public ArrayList<BoxInfo> getList() {
            return list;
        }

        public void setList(ArrayList<BoxInfo> list) {
            this.list = list;
        }
    }

    public class cryInfo{
        public String maoyanViewInfo;
        public String status;
        public String viewInfo;
    }
}
