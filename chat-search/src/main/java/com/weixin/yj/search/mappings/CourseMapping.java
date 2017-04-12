package com.weixin.yj.search.mappings;


import com.weixin.utils.util.JsonUtils;
import com.weixin.yj.search.setting.EsMappingConst;
import com.weixin.yj.search.setting.EsMappingJsonVo;
import com.weixin.yj.search.setting.EsMappingPropertiesVo;
import com.weixin.yj.search.setting.TigerEsConst;

import java.util.HashMap;
import java.util.Map;

/**
 * 课程映射
 * Created by wei on 15-9-25.
 */
public class CourseMapping {
    public static EsMappingJsonVo getMapping() {
        EsMappingJsonVo esMappingJsonVo = new EsMappingJsonVo();
        esMappingJsonVo.setIndex(TigerEsConst.INDEX_TIGER_MASTER);
        esMappingJsonVo.setType(TigerEsConst.TYPE_COURSE);
        //设置属性
        Map<String, EsMappingPropertiesVo> proMap = new HashMap<String, EsMappingPropertiesVo>();
        //课程ID
        proMap.put(TigerEsConst.FIELD_COURSE_ID,new EsMappingPropertiesVo(EsMappingConst.PRO_TYPE_INTEGER));
        //课程标题
        proMap.put(TigerEsConst.FIELD_COURSE_TITLE,new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK_MAX_WORD, true));
        //用户id
        proMap.put(TigerEsConst.FIELD_USER_ID, new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_NOT_ANALYZED, EsMappingConst.PRO_TYPE_STRING));
        //用户名称
        proMap.put(TigerEsConst.FIELD_USER_NAME, new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_IK_MAX_WORD, true));
        //学段id
        proMap.put(TigerEsConst.FIELD_PHASE_ID, new EsMappingPropertiesVo(EsMappingConst.PRO_TYPE_INTEGER));
        //年级id
        proMap.put(TigerEsConst.FIELD_GRADE_ID, new EsMappingPropertiesVo(EsMappingConst.PRO_TYPE_INTEGER));
        //学科id
        proMap.put(TigerEsConst.FIELD_SUBJECT_ID, new EsMappingPropertiesVo(EsMappingConst.PRO_TYPE_INTEGER));
        //教材ID
        proMap.put(TigerEsConst.FIELD_TEACH_MATERIAL_ID, new EsMappingPropertiesVo(EsMappingConst.PRO_TYPE_INTEGER));
        //章ID
        proMap.put(TigerEsConst.FIELD_CHAPTER_ID, new EsMappingPropertiesVo(EsMappingConst.PRO_TYPE_INTEGER));
        //节ID
        proMap.put(TigerEsConst.FIELD_SECTION_ID, new EsMappingPropertiesVo(EsMappingConst.PRO_TYPE_INTEGER));
        //知识点IDS
        proMap.put(TigerEsConst.FIELD_TERM_IDS,new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING, EsMappingConst.PRO_ANALYZED_WHITESPACE));
        //课程简介
        proMap.put(TigerEsConst.FIELD_COURSE_SUMMARY, new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_NO,EsMappingConst.PRO_TYPE_STRING));
        //课程状态
        proMap.put(TigerEsConst.FIELD_STATUS, new EsMappingPropertiesVo(EsMappingConst.PRO_TYPE_INTEGER));
        //课程图片标题
        proMap.put(TigerEsConst.FIELD_ICON_TITLE, new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_NO,EsMappingConst.PRO_TYPE_STRING));
        //课程简介
        proMap.put(TigerEsConst.FIELD_ICON_URL, new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_NO,EsMappingConst.PRO_TYPE_STRING));
        //课程学习人数
        proMap.put(TigerEsConst.FIELD_STUDY_COUNT, new EsMappingPropertiesVo(EsMappingConst.PRO_TYPE_INTEGER));
        //课程权值
        proMap.put(TigerEsConst.FIELD_COURSE_WIGHT, new EsMappingPropertiesVo(EsMappingConst.PRO_TYPE_DOUBLE));
        //课程分数
        proMap.put(TigerEsConst.FIELD_COURSE_SCORE, new EsMappingPropertiesVo(EsMappingConst.PRO_TYPE_DOUBLE));
        //学案设计数量
        proMap.put(TigerEsConst.FIELD_STUDY_DESIGN_COMPLETE_RATE,new EsMappingPropertiesVo(EsMappingConst.PRO_TYPE_DOUBLE));
        //机构id
        proMap.put(TigerEsConst.FIELD_UNIT_ID, new EsMappingPropertiesVo(EsMappingConst.PRO_TYPE_INTEGER));
        //机构path 使用标准分词器
        proMap.put(TigerEsConst.FIELD_UNIT_PATH, new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_ANALYZED, EsMappingConst.PRO_TYPE_STRING));
        //系统来自节点
        proMap.put(TigerEsConst.FIELD_FROM_SYS_CODE, new EsMappingPropertiesVo(EsMappingConst.PRO_INDEX_NOT_ANALYZED, EsMappingConst.PRO_TYPE_STRING));
        //创建时间
        proMap.put(TigerEsConst.FIELD_CREATE_TIME, new EsMappingPropertiesVo(EsMappingConst.PRO_TYPE_LONG));
        //修改时间
        proMap.put(TigerEsConst.FIELD_MODIFY_TIME, new EsMappingPropertiesVo(EsMappingConst.PRO_TYPE_LONG));

        Map<String, Object> mapping = new HashMap<String, Object>();
        mapping.put("properties", proMap);
        esMappingJsonVo.setMapping(mapping);
        return esMappingJsonVo;
    }

    public static void main(String[] args) {
        System.out.println(JsonUtils.toJson(getMapping()));
    }
}
