package com.airensoft.skovp.sample.vo;

import java.util.List;

/**
 * Created by burt on 2014. 5. 21..
 */
public class PlayerAdResponse {

    static class MediaFile {
        public String   type;
        public int      width;
        public int      height;
        public long     bitrate;
        public String   url;
        public String   protocol;
    }

    public static class RollAd {
        public String       click_url;
        public String       duration;
        public MediaFile    media_file;

        public String getMediaURL() {
            return media_file.url;
        }
    }

    static class MyAds {
        List<RollAd> pre_roll_ads;
        List<RollAd> post_roll_ads;
    }

    static class Result {
        public String ad_type;
        public MyAds  my_ads;
    }

    public Result result;

    public List<RollAd> getPreRollAds() {
        return result.my_ads.pre_roll_ads;
    }

    public List<RollAd> getPostRollAds() {
        return result.my_ads.post_roll_ads;
    }
}
