package com.qtone.util.schoolArea;

import org.springframework.stereotype.Controller;

@Controller
public class SchoolAreaController {



//    @Resource
//    DictSchoolAreaMapper dictSchoolAreaMapper;
//    @Resource
//    DictHjySchoolAreaMapper dictHjySchoolAreaMapper;
//
//    @RequestMapping(value = "/match/area", method = RequestMethod.POST)
//    @ResponseBody
//    public void saveResource() throws Exception {
//        matchArea();
//    }
//
//
//
//    public  void matchArea() throws IOException {
//        String fileName = "D:\\用户目录\\我的文档\\WeChat Files\\wxid_bttt2mh8x5ax22\\FileStorage\\File\\2022-09\\code(1).txt";
//        String str = new String(Files.readAllBytes(Paths.get(fileName)));
//        String[] arr = str.split("\n");
//        List<HjyArea> areas = new ArrayList<>();
//        for(int i=1;i<arr.length;i++) {
//            String s = arr[i];
//            try {
//                String[] strings =  s.split(",");
//                HjyArea hjyArea = new HjyArea();
//                hjyArea.setId(strings[0]);
//                hjyArea.setCode(strings[1]);
//                hjyArea.setName(strings[2]);
//                hjyArea.setRank(strings[3]);
//                hjyArea.setProvinceId(strings[4]);
//                hjyArea.setProvinceCode(strings[5]);
//                hjyArea.setCityId(strings[6]);
//                hjyArea.setCityCode(strings[7]);
//                areas.add(hjyArea);
//            } catch (Exception e) {
//                System.out.println(s);
//                e.printStackTrace();
//                break;
//            }
//        }
//        String errorString = "";
//        for(HjyArea hjyArea :areas) {
//            DictSchoolAreaExample dictSchoolAreaExample = new DictSchoolAreaExample();
//            dictSchoolAreaExample.createCriteria().andCodeEqualTo(hjyArea.getCityCode());
//            List<DictSchoolArea> schoolAreas = dictSchoolAreaMapper.selectByExample(dictSchoolAreaExample);
//            if(schoolAreas.isEmpty()) {
//                errorString += schoolAreas.toString()+"\\n";
//                continue;
//            }
//            DictSchoolArea dictSchoolArea = new DictSchoolArea();
//            dictSchoolArea.setCode(hjyArea.getCode());
//            dictSchoolArea.setParentId(schoolAreas.get(0).getId());
//            dictSchoolArea.setDistrict(hjyArea.getName());
//            dictSchoolArea.setAreaName(hjyArea.getName());
//            dictSchoolAreaMapper.insertSelective(dictSchoolArea);
//        }
//
//        System.out.println(errorString);
//    }
}
