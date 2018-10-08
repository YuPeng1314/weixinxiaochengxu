/**
 * @Title: EsConstants.java
 * @Package com.huayu.core.constant
 * @Description: TODO
 * Copyright: Copyright (c) 2016 
 * Company:华煜网络教育有限公司
 * 
 * @author asus
 * @date 2016年10月10日 上午9:27:52
 * @version V1.0
 */

package com.huayu.core.constant;

/**
 * @ClassName: EsConstants
 * @Description: ES操作的常量类
 * @author asus
 * @date 2016年10月10日 上午9:27:52
 *
 */

public interface EsConstants {
    /**
     * 批量增加索引的链接
     */
    String BULK_URL = "http://%s:%s/%s/%s/_bulk";

    String INDEX_URL = "http://%s:%s/%s/%s/%s";

    /**
     * 批量查询索引的链接
     */
    String QUERY_URL = "http://%s:%s/%s/%s/_query";

    String DELETE_URL = "http://%s:%s/%s/%s/%s";

    String UPDATE_URL = "http://%s:%s/%s/%s/%s/_update";
    /**
     * 增加索引的数据结构
     */
    String CREATE_INDEX = "{\"create\":{\"_id\":\"%s\"}}";
    String UPDATE_INDEX = "{\"update\":{\"_id\":%s}}";
    String DELETE_INDEX = "{\"delete\":{\"_id\":%s}}";
    /**
     * 换行符
     */
    String N = "\n";

    /**
     * 索引库名称
     */
    String INDEX_RES = "resource";
    /**
     * 专业课程类别
     */
    String PROFESSION = "profession";
    /**
     * 新闻类别
     */
    String ADS = "ads";
    /**
     * 政策类别
     */
    String POLICY = "policy";
    /**
     * 公告类别
     */
    String NOTICE = "notice";

    String THESIS = "thesis";
    /**
     * 查询URL
     */
    String SEARCH_URL = "http://%s:%s/%s/%s/_search";

    /**
     * 多字段查询
     */
    StringBuilder MUTILQUERYSTR = new StringBuilder()
            .append("{\"query\": {\"multi_match\": {\"query\": \"%s\",\"fields\": [%s]}},")
            .append("\"highlight\": {\"pre_tags\": [\"<font color='red'>\"],\"post_tags\": [\"</font>\"],\"fields\": {%s}},")
            .append("\"from\": %s,\"size\": %s}");

    String MUTILQUERYSTR2 = "{\"query\": {\"multi_match\": {\"query\": \"%s\",\"fields\": [%s]}},\"from\": %s,\"size\": %s}";
    String QUERYALL = "{\"query\": {\"match_all\": {}},\"from\": %s,\"size\": %s}";
    //单条件查询
    String SINGLEQUERY = "{\"query\": {\"term\": {\"%s\":\"%s\"}},\"from\": %s,\"size\": %s}";

    String COUNTQUERY = "{\"query\": {\"multi_match\": {\"query\": \"%s\",\"fields\": [%s]}}, \"size\": 0, \"aggs\": {\"group_by_state\": {\"terms\": { \"field\": \"_type\"}}}}";

    //过滤条件下的统计（size:0 只的是只返回统计结果，不返回条件数据）
    StringBuilder FILTERCOUNTQUERY = new StringBuilder()
            .append("{\"query\":{\"filtered\":{\"query\":{\"multi_match\":{\"query\":\"%s\",\"fields\":[%s]}},")
            .append("\"filter\":{\"bool\":{\"must\":{\"term\":{%s}}}}}},")
            .append("\"size\":0,\"aggs\":{\"group_by_state\":{\"terms\":{\"field\":\"_type\"}}}}");

    //过滤条件查询语句
    StringBuilder FILTEREDQUERY = new StringBuilder()
            .append("{\"query\":{\"filtered\":{\"query\":{\"multi_match\":{\"query\":\"%s\",\"fields\":[%s]}},")
            .append("\"filter\":{\"bool\":{\"must\":{\"term\":{%s}}}}}},")
            .append("\"highlight\":{\"pre_tags\":[\"<font color='red'>\"],\"post_tags\":[\"</font>\"],\"fields\":{%s}},")
            .append("\"from\": %s,\"size\": %s}");

    String UPDATEJSON = "{\"doc\":%s}";
}
