package com.qtone.util.zxx;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.alibaba.fastjson.JSONObject;
import com.qtone.util.dao.test.*;
import com.qtone.util.zxx.entity.*;
import com.qtone.util.zxx.model.*;
import com.qtone.util.zxx.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Controller
public class MainCLass {

    @Autowired
    UnitinfoMapper unitinfoMapper;
    @Autowired
    PublicResourceDictChapterMapper publicResourceDictChapterMapper;
    @Autowired
    PublicResourceDictGradeLevelMapper publicResourceDictGradeLevelMapper;
    @Autowired
    PublicResourceDictGradeMapper publicResourceDictGradeMapper;
    @Autowired
    PublicResourceDictMaterialMapper publicResourceDictMaterialMapper;
    @Autowired
    PublicResourceDictSubjectMapper publicResourceDictSubjectMapper;
    @Autowired
    PublicResourceSuiteMapper publicResourceSuiteMapper;
    @Autowired
    PublicResourceSuiteContentMapper publicResourceSuiteContentMapper;

    @Autowired
    HttpUtils httpUtils;

    @RequestMapping(value = "/save/resource", method = RequestMethod.POST)
    @ResponseBody
    public void saveResource() throws Exception, InterruptedException {
//        saveUnit();
//        saveGrade();
//        saveChapter();
//        saveVideo();
        getPingying();
    }

    public void saveUnit() throws IOException {

        String fileName = "C:\\Users\\Administrator.USER-20200720VX\\Desktop\\智学互动\\unitinfo.txt";
        String str = new String(Files.readAllBytes(Paths.get(fileName)));
        List<Unit> infoList = JSONObject.parseArray(str, Unit.class);
        for(Unit unit : infoList) {
            Unitinfo unitinfo = new Unitinfo();
            unitinfo.setUnitId(unit.getUnit_id());
            String tagIds = "";
            for(int i=0;i<unit.getTags().size();i++) {
                tagIds = tagIds+ unit.getTags().get(i).getId()+",";
            }
            unitinfo.setTagid(tagIds);
            unitinfoMapper.insertSelective(unitinfo);
        }
    }

    public void saveGrade() throws IOException {
        String gradeFileName = "C:\\Users\\Administrator.USER-20200720VX\\Desktop\\智学互动\\gradeInfo.txt";
        String str2 = new String(Files.readAllBytes(Paths.get(gradeFileName)));
        GradeInfo gradeInfos = JSONObject.parseObject(str2,GradeInfo.class);

        for(GradeInfo.Period period:gradeInfos.getTag_tree()){
            for(GradeInfo.PeriodDetail periodDetail:period.getTags()) {
                Integer periodType=0;
                switch (periodDetail.getTagName()){
                    case "小学":
                        periodType=1;
                        break;
                    case "初中":
                        periodType=2;
                        break;
                    case "小学（五•四学制）":
                        periodType=3;
                        break;
                    case "初中（五•四学制）":
                        periodType=4;
                        break;
                    case "高中":
                        periodType=5;
                        break;
                }


                for(GradeInfo.grade grade:periodDetail.getChildren())  {
                    //插入年级字典表
                    for(GradeInfo.gradeDetail gradeDetail:grade.getTags()) {
                        Integer gradeLevelId = 0;
                        PublicResourceDictGradeLevelExample gradeLevelExample = new PublicResourceDictGradeLevelExample();
                        gradeLevelExample.createCriteria().andLevelNameEqualTo(gradeDetail.getTag_name())
                                .andZxxIdEqualTo(gradeDetail.getTag_id()).andTypeEqualTo(periodType);
                        List<PublicResourceDictGradeLevel> gradeLevels = publicResourceDictGradeLevelMapper.selectByExample(gradeLevelExample);
                        if(gradeLevels.isEmpty()) {
                            PublicResourceDictGradeLevel gradeLevel = new PublicResourceDictGradeLevel();
                            gradeLevel.setLevelName(gradeDetail.getTag_name());
                            gradeLevel.setZxxId(gradeDetail.getTag_id());
                            gradeLevel.setType(periodType);
                            publicResourceDictGradeLevelMapper.insertSelective(gradeLevel);
                            gradeLevelId = gradeLevel.getId();
                        }else {
                            gradeLevelId = gradeLevels.get(0).getId();
                        }

                        for(GradeInfo.subject subject:gradeDetail.getChildren()) {
                            //插入学科字典表
                            for(GradeInfo.subjectDetail subjectDetail:subject.getTags()) {
                                Integer subjectId = 0;
                                PublicResourceDictSubjectExample subjectExample = new PublicResourceDictSubjectExample();
                                subjectExample.createCriteria().andTypeEqualTo(1).andGradeLevelIdEqualTo(gradeLevelId)
                                        .andZxxIdEqualTo(subjectDetail.getTag_id());
                                List<PublicResourceDictSubject> subjects = publicResourceDictSubjectMapper.selectByExample(subjectExample);
                                if(subjects.isEmpty()) {
                                    PublicResourceDictSubject subjectInfo = new PublicResourceDictSubject();
                                    subjectInfo.setType(1);
                                    subjectInfo.setGradeLevelId(gradeLevelId);
                                    subjectInfo.setZxxId(subjectDetail.getTag_id());
                                    subjectInfo.setSubjectName(subjectDetail.getTag_name());
                                    subjectInfo.setShortName(subjectDetail.getTag_name());
                                    publicResourceDictSubjectMapper.insertSelective(subjectInfo);
                                    subjectId = subjectInfo.getId();
                                }else {
                                    subjectId = subjects.get(0).getId();
                                }
                                for(GradeInfo.version version:subjectDetail.getChildren()) {
                                    //插入版本字典表
                                    for(GradeInfo.versionDetail versionDetail:version.getTags()) {
                                        Integer materialId = 0;
                                        PublicResourceDictMaterialExample materialExample = new PublicResourceDictMaterialExample();
                                        materialExample.createCriteria().andSubjectIdEqualTo(subjectId).andZxxIdEqualTo(versionDetail.getTag_id());
                                        List<PublicResourceDictMaterial> materials = publicResourceDictMaterialMapper.selectByExample(materialExample);
                                        if(materials.isEmpty()) {
                                            PublicResourceDictMaterial material = new PublicResourceDictMaterial();
                                            material.setZxxId(versionDetail.getTag_id());
                                            material.setSubjectId(subjectId);
                                            material.setMaterialName(versionDetail.getTag_name());
                                            publicResourceDictMaterialMapper.insertSelective(material);
                                            materialId = material.getId();
                                        }else {
                                            materialId = materials.get(0).getId();
                                        }

                                        for(GradeInfo.book book:versionDetail.getChildren()) {
                                            //插入册次字典表
                                            for(GradeInfo.bookDetail bookDetail:book.getTags()) {
                                                Integer bookId = 0;
                                                PublicResourceDictGradeExample gradeExample = new PublicResourceDictGradeExample();
                                                gradeExample.createCriteria().andMaterialIdEqualTo(materialId).andZxxIdEqualTo(bookDetail.getTag_id());
                                                List<PublicResourceDictGrade> grades = publicResourceDictGradeMapper.selectByExample(gradeExample);
                                                if(grades.isEmpty()) {
                                                    PublicResourceDictGrade gradeInfo = new PublicResourceDictGrade();
                                                    gradeInfo.setZxxId(bookDetail.getTag_id());
                                                    gradeInfo.setMaterialId(materialId);
                                                    gradeInfo.setGradeName(bookDetail.getTag_name());
                                                    publicResourceDictGradeMapper.insertSelective(gradeInfo);
                                                    bookId = gradeInfo.getId();
                                                }else {
                                                    bookId = grades.get(0).getId();
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }

        }

        System.out.println("dddddddd");
    }


    public void saveChapter() throws InterruptedException {

        List<ZxxId> zxxIds = unitinfoMapper.getZxxIdInfo();
        int i=0;
        for(ZxxId zxxId:zxxIds) {
            i++;
            String zhCNUrl = "https://s-file-1.ykt.cbern.com.cn/zxx/s_course/v2/business_courses/%s/course_relative_infos/zh-CN.json";
            List<String> unitIds = unitinfoMapper.getUnitId(zxxId.getGradeLevelId(),zxxId.getSubjectId(),zxxId.getMaterialId(),zxxId.getGradeId());
            if(unitIds.isEmpty()) {
                continue;
            }
            String unitId = unitIds.get(0);
            zhCNUrl = String.format(zhCNUrl,unitId);
            Map<String,Object> returnMap = httpUtils.getHttpMethod(zhCNUrl);
            if(!ObjectUtils.isEmpty(returnMap.get("course_detail"))) {
//                Thread.sleep(500);
                Map<String,Object> course_detail = (Map<String, Object>) returnMap.get("course_detail");
                String activity_set_id = (String) course_detail.get("activity_set_id");
                String fullUrl = "https://s-file-1.ykt.cbern.com.cn/zxx/s_course/v2/activity_sets/%s/fulls.json";
                fullUrl = String.format(fullUrl,activity_set_id);
                Map<String,Object> chapterMap = httpUtils.getHttpMethod(fullUrl);
                ChapterInfo chapterInfo = JSONObject.parseObject(JSONObject.toJSONString(chapterMap), ChapterInfo.class) ;
                PublicResourceDictChapter chapter = new PublicResourceDictChapter();
                chapter.setGradeId(zxxId.getId());
                chapter.setChapterName(chapterInfo.getActivity_set_name());
                chapter.setParentid(0);
                chapter.setLevel(1);
                chapter.setIsLast(0);
                chapter.setSort(i);
                chapter.setZxxId(unitId);
                publicResourceDictChapterMapper.insertSelective(chapter);
                for(ChapterInfo.node node: chapterInfo.getNodes()) {
                    PublicResourceDictChapter chapter2 = new PublicResourceDictChapter();
                    chapter2.setGradeId(zxxId.getId());
                    chapter2.setChapterName(node.getNode_name());
                    chapter2.setParentid(chapter.getId());
                    chapter2.setLevel(2);
                    chapter2.setIsLast(0);
                    chapter2.setSort(node.getOrder_no());
                    chapter2.setZxxId(node.getNode_id());
                    publicResourceDictChapterMapper.insertSelective(chapter2);
                    for(ChapterInfo.nodeDetail nodeDetail:node.getChild_nodes()) {
                        PublicResourceDictChapter chapter3 = new PublicResourceDictChapter();
                        chapter3.setGradeId(zxxId.getId());
                        chapter3.setChapterName(nodeDetail.getNode_name());
                        chapter3.setParentid(chapter2.getId());
                        chapter3.setLevel(3);
                        chapter3.setSort(nodeDetail.getOrder_no());
                        chapter3.setZxxId(nodeDetail.getNode_id());
                        chapter3.setIsLast(1);
                        if(nodeDetail.getChild_nodes().size()>0) {
                            chapter3.setIsLast(0);
                        }
                        publicResourceDictChapterMapper.insertSelective(chapter3);

                        String contentUrl = "https://bb.zxx.edu.cn/#bookID=%s&chapterID=%s";
                        if(nodeDetail.getChild_nodes().isEmpty()){
                            PublicResourceSuite suite = new PublicResourceSuite();
                            suite.setGradeLevelId(zxxId.getHdktGradeLevelId());
                            suite.setSubjectId(zxxId.getHdktSubjectId());
                            suite.setMaterialId(zxxId.getHdktMaterialId());
                            suite.setGradeId(zxxId.getHdktGradeId());
                            suite.setChapterId(chapter3.getId());
                            suite.setSuiteName(nodeDetail.getNode_name());
                            publicResourceSuiteMapper.insertSelective(suite);

                            contentUrl = String.format(contentUrl,activity_set_id,nodeDetail.getNode_id());
                            PublicResourceSuiteContent content = new PublicResourceSuiteContent();
                            content.setGradeLevelId(zxxId.getHdktGradeLevelId());
                            content.setSubjectId(zxxId.getHdktSubjectId());
                            content.setMaterialId(zxxId.getHdktMaterialId());
                            content.setGradeId(zxxId.getHdktGradeId());
                            content.setChapterId(chapter3.getId());
                            content.setSuiteId(suite.getId());
                            content.setContentName(nodeDetail.getNode_name());
                            content.setContentUrl(contentUrl);
                            content.setContentType(1);
                            publicResourceSuiteContentMapper.insertSelective(content);
                            continue;
                        }

                        for(ChapterInfo.nodeDetail nodeDetail2:nodeDetail.getChild_nodes()) {
                            PublicResourceDictChapter chapter4 = new PublicResourceDictChapter();
                            chapter4.setGradeId(zxxId.getId());
                            chapter4.setChapterName(nodeDetail2.getNode_name());
                            chapter4.setParentid(chapter3.getId());
                            chapter4.setLevel(4);
                            chapter4.setSort(nodeDetail2.getOrder_no());
                            chapter4.setZxxId(nodeDetail2.getNode_id());
                            chapter4.setIsLast(1);
                            if(nodeDetail2.getChild_nodes().size()>0) {
                                chapter4.setIsLast(0);
                            }
                            publicResourceDictChapterMapper.insertSelective(chapter4);

                            PublicResourceSuite suite = new PublicResourceSuite();
                            suite.setGradeLevelId(zxxId.getHdktGradeLevelId());
                            suite.setSubjectId(zxxId.getHdktSubjectId());
                            suite.setMaterialId(zxxId.getHdktMaterialId());
                            suite.setGradeId(zxxId.getHdktGradeId());
                            suite.setChapterId(chapter4.getId());
                            suite.setSuiteName(nodeDetail2.getNode_name());
                            publicResourceSuiteMapper.insertSelective(suite);

                            contentUrl = String.format(contentUrl,activity_set_id,nodeDetail2.getNode_id());
                            PublicResourceSuiteContent content = new PublicResourceSuiteContent();
                            content.setGradeLevelId(zxxId.getHdktGradeLevelId());
                            content.setSubjectId(zxxId.getHdktSubjectId());
                            content.setMaterialId(zxxId.getHdktMaterialId());
                            content.setGradeId(zxxId.getHdktGradeId());
                            content.setChapterId(chapter4.getId());
                            content.setSuiteId(suite.getId());
                            content.setContentName(nodeDetail2.getNode_name());
                            content.setContentUrl(contentUrl);
                            content.setContentType(1);
                            publicResourceSuiteContentMapper.insertSelective(content);
                        }
                    }
                }
            }
        }
    }


    public void saveVideo() throws Exception {
        PublicResourceDictChapterExample example =  new PublicResourceDictChapterExample();
        example.createCriteria().andZxxIdIsNotNull().andIsLastEqualTo(1);
        List<PublicResourceDictChapter> chapters = publicResourceDictChapterMapper.selectByExample(example);
        for(PublicResourceDictChapter chapter:chapters) {
            String url = "https://s-file.ykt.cbern.com.cn/zxx/s_course/v1/x_class_hour_activity/%s/resources.json";
            url = String.format(url,chapter.getZxxId());
            List<Map<String,Object>> videoMap = httpUtils.getHttpMethod2(url);
            List<VideoInfo> videoInfos = JSONObject.parseArray(JSONObject.toJSONString(videoMap),VideoInfo.class);
            for(VideoInfo videoInfo:videoInfos) {
                if(!videoInfo.getResource_type().equals("video")){
                    continue;
                }

                String m3u8Url = videoInfo.getVideo_extend().getUrls().get(0).getUrls().get(0);
                if(m3u8Url.contains(".m3u8")) {
//                    HttpUtils.downFile(m3u8Url, chapter.getZxxId(), "D:\\中小学视屏资源\\");
//                    m3u8Url = "http://hdkt-ugc.oss-cn-beijing.aliyuncs.com/test/ggzy/m3u8/"+chapter.getZxxId()+".m3u8";
                    continue;
                }

                //保存链接
                PublicResourceSuiteExample suiteExample = new PublicResourceSuiteExample();
                suiteExample.createCriteria().andChapterIdEqualTo(chapter.getId());
                List<PublicResourceSuite> suites = publicResourceSuiteMapper.selectByExample(suiteExample);
                if(suites.size()>0) {
                    PublicResourceSuiteContentExample contentExample = new PublicResourceSuiteContentExample();
                    contentExample.createCriteria().andSuiteIdEqualTo(suites.get(0).getId());
                    List<PublicResourceSuiteContent> contents =publicResourceSuiteContentMapper.selectByExample(contentExample);
                    if(contents.size()>0) {
                        PublicResourceSuiteContent content = contents.get(0);
                        content.setZxxUrl(content.getContentUrl());
                        content.setContentUrl(m3u8Url);
                        content.setDuration(videoInfo.getStudy_time()/60);
                        content.setCoverUrl(videoInfo.getVideo_extend().getFront_cover_url());
                        publicResourceSuiteContentMapper.updateByPrimaryKey(content);
                    }
                }
                System.out.println(m3u8Url);
                break;
            }
        }

    }

    /**
     * create by: zhangpk
     * description: TODO
     * create time: 14:51 2022/9/23
     * @Param: null
     * @return
     */
    public void getPingying() {
        List<PublicResourceDictChapter> chapters =  publicResourceDictChapterMapper.getLastChapter();
       for(PublicResourceDictChapter chapter:chapters) {
           if(StringUtils.isEmpty(chapter.getChapterName())) {
               continue;
           }
           String pingying = PinyinUtil.getFirstLetter(chapter.getChapterName(),"");
           String reg = "[^A-Za-z0-9]";
           pingying = pingying.replaceAll(reg, "");
           pingying = pingying.toUpperCase();
           publicResourceDictChapterMapper.updateZxChapterNameById(pingying,chapter.getId());
       }
    }

}
