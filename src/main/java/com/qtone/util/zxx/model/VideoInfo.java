package com.qtone.util.zxx.model;

import lombok.Data;

import java.util.List;

@Data
public class VideoInfo {
    private String resource_type;
    private Integer study_time;
    private VideoExtend video_extend;


    @Data
    public static class VideoExtend{
        private String front_cover_url;
        private List<UrlContent> urls;
    }

    @Data
    public static class UrlContent{
        List<String> urls;
    }
}
