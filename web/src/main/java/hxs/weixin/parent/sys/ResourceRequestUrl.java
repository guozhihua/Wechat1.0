package hxs.weixin.parent.sys;

/**
 * Created by zhusen on 2016/11/28.
 */
public class ResourceRequestUrl {

    private static String BASE_URL = "http://211.157.179.218:9091/llsfw";

    /**
     * 根据学科code获取教材信息
     */
    public static String baseBookTypeByGradeCodeandSubjectCode = BASE_URL + "/rest/baseBookTypeByGradeCodeandSubjectCode/{gradeCode}/{subjectCode}";

    /**
     * 根据教材类型，学科编号获取知识信息
     */
    public static String getBookKnowledgeInfo = BASE_URL + "/bookKnowledgeTreeController/rest/getBookKnowledgeInfo";

    /**
     * 根据上下册code获取单元列表
     */
    public static String getCtbList = BASE_URL + "/rest/getCtbList/{ctbCode}";

    /**
     * 根据学年、学科、教材、单元知识点code获取试卷列表
     */
    public static String getPapersForIdAndNames = BASE_URL + "/paperController/rest/getPapersForIdAndNames";

    /**
     * 根绝试卷code获取试卷信息
     */
    public static String getPaperWithCode = BASE_URL + "/paperController/rest/getPaperWithCode/code/{paperCode}";

    /**
     * 根据单元code获取视频信息
     */
    public static String bookKnowledgeTreeAndVideo = BASE_URL + "/rest/bookKnowledgeTreeAndVideo/{code}";
}
