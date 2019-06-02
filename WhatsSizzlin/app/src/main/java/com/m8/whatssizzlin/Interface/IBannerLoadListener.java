package com.m8.whatssizzlin.Interface;

import com.m8.whatssizzlin.Model.Banner;

import java.util.List;

public interface IBannerLoadListener {
    void onBannerLoadSuccess(List<Banner> banners);
    void onBannerLoadFailed(String message);
}
