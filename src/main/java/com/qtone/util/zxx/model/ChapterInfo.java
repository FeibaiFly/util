package com.qtone.util.zxx.model;

import lombok.Data;

import java.util.List;

@Data
public class ChapterInfo {
    private String activity_set_name;
    private List<node> nodes;

    @Data
    public static class node{
        private String node_id;
        private String node_name;
        private String node_type;
        private Integer order_no;
        private List<nodeDetail> child_nodes;
    }

    @Data
    public static class nodeDetail{
        private String node_id;
        private String node_name;
        private String node_type;
        private Integer order_no;
        private List<nodeDetail> child_nodes;
    }
}
