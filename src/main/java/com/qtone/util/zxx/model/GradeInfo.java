package com.qtone.util.zxx.model;

import lombok.Data;

import java.util.List;
@Data
public class GradeInfo {
    private List<Period> tag_tree;
    @Data
    public static class Period{
        List<PeriodDetail> tags;
    }
    @Data
    public static class PeriodDetail{
        private String tag_id;
        private String tagName;
        List<grade> children;
    }
    @Data
    public static class grade{
        List<gradeDetail> tags;
    }
    @Data
    public static class gradeDetail{
        private String tag_id;
        private String tag_name;
        List<subject> children;
    }
    @Data
    public static class subject{
        List<subjectDetail> tags;
    }
    @Data
    public static class subjectDetail{
        private String tag_id;
        private String tag_name;
        List<version> children;
    }
    @Data
    public static class version{
        List<versionDetail> tags;
    }
    @Data
    public static class versionDetail{
        private String tag_id;
        private String tag_name;
        List<book> children;
    }
    @Data
    public static class book{
        List<bookDetail> tags;
    }
    @Data
    public static class bookDetail{
        private String tag_id;
        private String tag_name;
    }

}
