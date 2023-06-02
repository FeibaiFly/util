package com.qtone.util.zxx.model;

import lombok.Data;

import java.util.List;

@Data
public class Unit {
    private String unit_id;

    private List<Tag> tags;
    @Data
    public static class Tag{
        private String id;
    }
}
