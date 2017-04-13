package com.weixin.yj.search.setting;

import java.util.ArrayList;
import java.util.List;

/**
 * ES通用常量处理类
 *
 */
public class ESConst {

    /** 索引数据中必须带的id标识符，用于生成doucment的id */
    public static final String ID_FORK = "_id";

    /** 索引数据中设置parent的标识符，用于生成doucment的parent */
    public static final String PARENT_FORK = "_parent";

    /** river涉及到的id列表 */
    public static final List<String> RIVER_ID_LIST = new ArrayList<String>() {
        {
            this.add("_custom");
            this.add("_status");
            this.add("_meta");
        }
    };


    /** 本地搜索服务客户端配置前缀 */
    public static final String CONF_ES_PREFIX_LOCAL = "elasticsearch";

    /** mapping properties */
    public static final String MAPPING_PROPERTIES = "properties";
    /** mapping type */
    public static final String MAPPING_TYPE = "type";

    /** 总的索引库名称 */
    public static final String INDEX_TOTAL = "h_master";//
    /** estar_index索引下的一个类型(表)——gather */
    public static final String TYPE_GATHER = "gather";
    /** estar_index索引下的一个类型(表)——term_info（年级、学科、教材、章节知识点信息） */
    public static final String TYPE_CS_TERMINFO = "cs_term_info";

    /** 用于搜索页面统计的索引库名称 */
    public static final String INDEX_SEARCHPAGE = "h_searchpage";
    /** h_searchpage索引下的一个类型(表)——searchkey_stat(搜索词排行) */
    public static final String TYPE_SEARCH_STAT = "searchkey_stat";

    /** 用于协作组统计的索引库名称 */
    public static final String INDEX_CONTAINER = "h_container";
    /** h_container索引下的一个类型(表)——container_term_stat(协作组知识点达人排行) */
    public static final String TYPE_CONTAINER_TERM = "container_term";

    /** 题库主索引库名称 */
    public static final String INDEX_WHALE_MASTER = "whale_master";
    /** 题库收藏索引库名称 */
    public static final String INDEX_WHALE_FAVORITE = "whale_favorite";
    /** 题库热搜知识点排行 */
    public static final String INDEX_WHALE_HOT = "whale_hot";

    /** 索引库mapping——hunter索引库 */
    public static final String MAPPING_HUNTER = "/es/mapping.json";
    /** 索引库mapping——whale索引库 */
    public static final String MAPPING_WHALE = "/es/whale_mapping.json";

    /** 内容类型——文章 */
    public static final String CONT_TYPE_ARITCLE = "article";
    /** 内容类型——视频 */
    public static final String CONT_TYPE_VIDEO = "video";
    /** 内容类型——资源 */
    public static final String CONT_TYPE_RESOURCE = "resource";
    /** 内容类型)——集备 */
    public static final String CONT_TYPE_CAMEL = "camel";
    /** 内容类型)——评课 */
    public static final String CONT_TYPE_EVALUATION = "evaluation";
    /** 内容类型)——问答 */
    public static final String CONT_TYPE_FIREFLY = "firefly";
    /** 内容类型——作业*/
    public static final String CONT_TYPE_LEOPARD = "leopard";
    /** 内容类型——题库-试卷(并不用于contType)*/
    public static final String CONT_TYPE_WHALE_PAPER = "paper";
    /** 内容类型——题库-试卷(并不用于contType)*/
    public static final String CONT_TYPE_WHALE_QUESTION = "question";
    /** 内容类型——第三方资源*/
    public static final String CONT_TYPE_THIRD_PART = "3rdpart";
    /** 内容类型——微课 */
    public static final String CONT_TYPE_WEEK = "week";
    /** 应用类型——作业*/
    public static final String APP_CODE_LEOPARD = "LEOPARD";
    /** 应用类型——作业*/
    public static final String APP_CODE_WHALE = "WHALE";
    /** 应用类型——百度*/
    public static final String APP_CODE_BAIDU = "BAIDU";

    /** 索引使用到的分隔符 */
    public static final String SEPRATOR = "_";

    /** termInfo的索引id前缀 */
    public static final String ID_PREFIX_TERM = "term" + SEPRATOR;

    /** gather索引文档参数名定义——用户id */
    public static final String GATHER_USERID = "userId";
    /** gather索引文档参数名定义——用户名 */
    public static final String GATHER_USERNAME = "userName";
    /** gather索引文档参数名定义——机构id */
    public static final String GATHER_UNITID = "unitId";
    /** gather索引文档参数名定义——机构路径 */
    public static final String GATHER_UNITPATH = "unitPath";
    /** gather索引文档参数名定义——内容id */
    public static final String GATHER_CONTID = "contId";
    /** gather索引文档参数名定义——内容标题 */
    public static final String GATHER_CONTTITLE = "contTitle";
    /** gather索引文档参数名定义——内容类型 @see ESConst.CONT_TYPE_xxx */
    public static final String GATHER_CONTTYPE = "contType";
    /** gather索引文档参数名定义——内容数据 */
    public static final String GATHER_CONTENT = "content";
    /** gather索引文档参数名定义——赞的数量 */
    public static final String GATHER_LIKECOUNT = "likeCount";
    /** gather索引文档参数名定义——评论的数量 */
    public static final String GATHER_COMMENTCOUNT = "commentCount";
    /** gather索引文档参数名定义——转载的数量 */
    public static final String GATHER_FORWARDCOUNT = "forwardCount";
    /** gather索引文档参数名定义——访问的数量 */
    public static final String GATHER_VIEWCOUNT = "viewCount";
    /** gather索引文档参数名定义——下载的数量 */
    public static final String GATHER_DOWNLOADCOUNT = "downloadCount";
    /** gather索引文档参数名定义——收藏的数量 */
    public static final String GATHER_COLLECTCOUNT = "collectCount";
    /** gather索引文档参数名定义——创建时间*/
    public static final String GATHER_CREATETIME = "createTime";
    /** gather索引文档参数名定义——容器id*/
    public static final String GATHER_CONTAINERID = "containerId";
    /** gather索引文档参数名定义——容器类型*/
    public static final String GATHER_CONTAINERTYPE = "containerType";
    /** gather索引文档参数名定义——容器名称*/
    public static final String GATHER_CONTAINERNAME = "containerName";
    /** gather索引文档参数名定义——应用Code*/
    public static final String GATHER_APPCODE = "appCode";
    /** gather索引文档参数名定义——应用对象id*/
    public static final String GATHER_APPOBJID = "appObjId";
    /** gather索引文档参数名定义——应用对象名称*/
    public static final String GATHER_APPOBJNAME = "appObjName";
    /** gather索引文档参数名定义——自定义得分排序字段*/
    public static final String GATHER_SCORESORT = "scoreSort";
    /** gather索引文档参数名定义——容器属性字段，0 公开协作组;1 私密协作组*/
    public static final String GATHER_CONTAINERPROP = "containerProp";
    /** gather索引文档参数名定义——内容对应的容器内容id*/
    public static final String GATHER_CONTAINERCONTID = "containerContId";
    /** gather索引文档参数名定义——内容对应的应用内容id*/
    public static final String GATHER_APPCONTID = "appContId";
    /** gather索引文档参数名定义——入学年份，只用于作业*/
    public static final String GATHER_JOINYEAR = "joinYear";
    /** gather索引文档参数名定义——节点编号*/
    public static final String GATHER_CLIENTCODE = "clientCode";
    /** gather索引文档参数名定义——个人内容权限，0-公开，1- 粉丝可见  2好友可见  3自己可见  4、指定好友*/
    public static final String GATHER_PERSONALCONTAUTH = "personalContAuth";
    /** gather索引文档参数名定义——容器内容权限，0 公开 1 组内可见*/
    public static final String GATHER_CONTAINERCONTAUTH = "containerContAuth";

    /** gather索引文档参数名定义——使用次数*/
    public static final String GATHER_USECOUNT = "useCount";
    /** gather索引文档参数名定义——打分人数*/
    public static final String GATHER_MARKUSERCOUNT = "markUserCount";
    /** gather索引文档参数名定义——试题、试卷总得分*/
    public static final String GATHER_MARKTOTALSCORE = "markTotalScore";
    /** gather索引文档参数名定义——难易度*/
    public static final String GATHER_PUBLISHTIME = "publishTime";
    /** gather索引文档参数名定义——试题题型*/
    public static final String GATHER_QUESTIONTYPE = "questionType";
    /** gather索引文档参数名定义——试题题型名称*/
    public static final String GATHER_QUESTIONTYPENAME = "questionTypeName";
    /** gather索引文档参数名定义——试题选项*/
    public static final String GATHER_QUESTIONITEM = "questionItem";
    /** gather索引文档参数名定义——收藏用户id（只用于whale_favorite）*/
    public static final String GATHER_FAVORITEUSERID = "favoriteUserId";
    /** gather索引文档参数名定义——内容分类，试题分类、试卷分类*/
    public static final String GATHER_CONTCATEGORY = "contCategory";
    /** gather索引文档参数名定义——内容分类名称*/
    public static final String GATHER_CONTCATEGORYNAME = "contCategoryName";
    /** gather索引文档参数名定义——难易度*/
    public static final String GATHER_DIFFICULTY = "difficulty";
    /** gather索引文档参数名定义——难易度名称*/
    public static final String GATHER_DIFFICULTYNAME = "difficultyName";
    /** gather索引文档参数名定义——教学要求*/
    public static final String GATHER_TEACHREQUIRE = "teachRequire";
    /** gather索引文档参数名定义——教学要求*/
    public static final String GATHER_TEACHREQUIRENAME = "teachRequireName";
    /** gather索引文档参数名定义——摘要*/
    public static final String GATHER_SUMMARY = "summary";
    /** gather索引文档参数名定义——试题解析*/
    public static final String GATHER_EXPLAINCONTENT = "explainContent";
    /** gather索引文档参数名定义——父内容id*/
    public static final String GATHER_CONTPID = "contPid";

    /*---------- 章、节、知识点相关 -----------*/
    /** cs_term_info索引文档参数名定义——学段id */
    public static final String CS_TERMINFO_GRADEID = "gradeId";
    /** cs_term_info索引文档参数名定义——学段名称 */
    public static final String CS_TERMINFO_GRADENAME = "gradeName";
    /** cs_term_info索引文档参数名定义——学科id */
    public static final String CS_TERMINFO_COURSEID = "courseId";
    /** cs_term_info索引文档参数名定义——学科名称 */
    public static final String CS_TERMINFO_COURSENAME = "courseName";
    /** cs_term_info索引文档参数名定义——教材id */
    public static final String CS_TERMINFO_BOOKID = "bookId";
    /** cs_term_info索引文档参数名定义——教材名称 */
    public static final String CS_TERMINFO_BOOKNAME = "bookName";
    /** cs_term_info索引文档参数名定义——章id */
    public static final String CS_TERMINFO_CHAPTERID = "chapterId";
    /** cs_term_info索引文档参数名定义——节id */
    public static final String CS_TERMINFO_SECTIONID = "sectionId";
    /** cs_term_info索引文档参数名定义——知识点id*/
    public static final String CS_TERMINFO_TERMID = "termId";
    /** cs_term_info索引文档参数名定义——知识点ids*/
    public static final String CS_TERMINFO_TERMIDS = "termIds";
    /** cs_term_info索引文档参数名定义——知识点名称*/
    public static final String CS_TERMINFO_TERMNAME = "termName";

    /** searchkey_stat索引文档参数名定义——关键字名称*/
    public static final String SEARCH_STAT_KEYWORD = "keyword";
    /** searchkey_stat索引文档参数名定义——关键字搜索数*/
    public static final String SEARCH_STAT_SEARCHCOUNT = "searchCount";

    /** 个人可见的容器类型*/
    public static final String CONTAINERTYPE_USER = "user";
    /** 容器属性——不公开（私密）*/
    public static final int CONTAINERPROP_PRIVATE = 1;

    /** 资源文件类型 */
    public static final int RES_TYPE_PPT = 1;
    public static final int RES_TYPE_WORD = 2;
    public static final int RES_TYPE_EXCEL = 3;
    public static final int RES_TYPE_TXT = 4;
    public static final int RES_TYPE_RAR = 5;
    public static final int RES_TYPE_PDF = 6;
    public static final int RES_TYPE_ZIP = 7;
    public static final int RES_TYPE_SWF = 8;
    public static final int RES_TYPE_IMAGE = 9;
    public static final int RES_TYPE_AUDIO = 10;

    /** 教师人数静态常量*/
    public static final String WHALE_USERCOUNT = "userCount";

    /** 分词查询效果 */
    public static float CUTOFF_FREQUENCY = 0.000001f;

}
