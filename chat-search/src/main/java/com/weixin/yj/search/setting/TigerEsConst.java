package com.weixin.yj.search.setting;

/**
 * 混合式教学搜索常量
 * Created by wei on 15-9-25.
 */
public class TigerEsConst {

    /** 混合式教学主索引库名称 */
    public static final String INDEX_TIGER_MASTER = "tiger";
    /** type课程 */
    public static final String TYPE_COURSE = "course";
    /**type 容器*/
    public static final String TYPE_CONTAINER_COURSE = "containerCourse";
    /** type课程学习记录 */
    public static final String TYPE_COURSE_STUDY = "courseStudy";
    /** 索引字段定义 —— 课程ID*/
    public static final String FIELD_COURSE_ID = "courseId";
    /**索引字段定义 —— 课程用户ID*/
    public static final String FIELD_COURSE_USER_ID = "courseUserId";
    /** 索引字段定义 —— 课程标题*/
    public static final String FIELD_COURSE_TITLE = "courseTitle";
    /**学段ID*/
    public static final String FIELD_PHASE_ID = "phaseId";
    /** 索引字段定义 —— 年级ID*/
    public static final String FIELD_GRADE_ID = "gradeId";
    /**索引字段定义 —— 学科ID*/
    public static final String FIELD_SUBJECT_ID = "subjectId";
    /** 索引字段定义 —— 教材ID*/
    public static final String FIELD_TEACH_MATERIAL_ID = "teachMaterialId";
    /** 索引字段定义 —— 章ID*/
    public static final String FIELD_CHAPTER_ID = "chapterId";
    /** 索引字段定义 —— 节ID*/
    public static final String FIELD_SECTION_ID = "sectionId";
    /** 索引字段定义 —— 多个知识点ID*/
    public static final String FIELD_TERM_IDS = "termIds";
    /** 索引字段定义 —— 用户ID*/
    public static final String FIELD_USER_ID = "userId";
    /**索引字段定义 -- 用户名*/
    public static final String FIELD_USER_NAME = "userName";
    /** 索引字段定义 —— 课程简介*/
    public static final String FIELD_COURSE_SUMMARY = "summary";
    /** 索引字段定义 —— 状态*/
    public static final String FIELD_STATUS = "status";
    /**索引字段定义 -- 隐藏状态*/
    public static final String FIELD_HIDE_STATUS = "hideStatus";
    /** 索引字段定义 —— 封面文字*/
    public static final String FIELD_ICON_TITLE = "iconTitle";
    /** 索引字段定义 —— 封面地址*/
    public static final String FIELD_ICON_URL = "iconUrl";
    /** 索引字段定义 —— 学习人数*/
    public static final String FIELD_STUDY_COUNT = "studyCount";
    /**索引字段定义 -- 课程权值*/
    public static final String FIELD_COURSE_WIGHT = "courseWight";
    /** 索引字段定义 —— 课程得分*/
    public static final String FIELD_COURSE_SCORE = "courseScore";
    /** 索引字段定义 —— 机构ID*/
    public static final String FIELD_UNIT_ID = "unitId";
    /** 索引字段定义 —— 机构Path*/
    public static final String FIELD_UNIT_PATH = "unitPath";
    /** 索引字段定义 —— 系统节点*/
    public static final String FIELD_FROM_SYS_CODE = "fromSysCode";
    /**课程完成状态*/
    public static final String FIELD_COMPLETE_RATE = "completeRate";
    /**学案设计数量*/
    public static final String FIELD_STUDY_DESIGN_COUNT = "studyDesignCount";
    /**教学设计数量*/
    public static final String FIELD_TEACH_DESIGN_COUNT = "teachDesignCount";

    /**学案设计完成状态*/
    public static final String FIELD_STUDY_DESIGN_COMPLETE_RATE = "studyDesignCompleteRate";

    /** 索引字段定义 —— 创建时间*/
    public static final String FIELD_CREATE_TIME = "createTime";
    /** 索引字段定义 —— 修改时间*/
    public static final String FIELD_MODIFY_TIME = "modifyTime";
    /** 索引字段定义 —— 容器ID*/
    public static final String FIELD_CONTAINER_ID = "containerId";
    /** 索引字段定义 —— 容器类型*/
    public static final String FIELD_CONTAINER_TYPE = "containerType";

    /**排序类型-正序*/
    public static int ORDER_TYPE_ASC = 0;
    /**排序类型-倒序*/
    public static int ORDER_TYPE_DESC = 1;
}
