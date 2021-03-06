package com.example.elasticsearchdemo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.elasticsearchdemo.common.lang.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class RestHighLevelUtils {

    @Autowired
    RestHighLevelClient esClient;

    /**
     * ????????????
     * @author wangzg
     * @date 2021/7/6 10:24
     * @param indexName
     * @return boolean
     */
    public boolean createIndex(String indexName) {
        try {
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
            CreateIndexResponse response = esClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            log.info("???????????? response ????????? {}", response.toString());
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ????????????????????????
     * @author wangzg
     * @date 2021/7/6 10:27
     * @param indexName
     * @return boolean
     */
    public boolean existIndex(String indexName) {
        try {
            GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
            return esClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ????????????
     * @author wangzg
     * @date 2021/7/6 10:31
     * @param indexName
     * @return boolean
     */
    public boolean deleteIndex(String indexName) {
        try {
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
            AcknowledgedResponse delete = esClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            log.info("????????????{}??????????????????{}", indexName, delete.isAcknowledged());
            return delete.isAcknowledged();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ??????id????????????
     * @author wangzg
     * @date 2021/7/6 10:34
     * @param indexName
     * @param id
     * @return boolean
     */
    public boolean deleteDocById(String indexName, String id) {
        try {
            DeleteRequest deleteRequest = new DeleteRequest(indexName, id);
            DeleteResponse deleteResponse = esClient.delete(deleteRequest, RequestOptions.DEFAULT);
            log.info("????????????{}???id???{}???????????????????????????{}", indexName, id, deleteResponse.status().toString());
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ??????????????????
     * @author wangzg
     * @date 2021/7/6 10:41
     * @param indexName
     * @param list 
     * @return boolean
     */
    public boolean multiAddDoc(String indexName, List<JSONObject> list) {
        try {
            BulkRequest bulkRequest = new BulkRequest();
            list.forEach(doc -> {
                String source = JSON.toJSONString(doc);
                IndexRequest indexRequest = new IndexRequest(indexName);
                indexRequest.source(source, XContentType.JSON);
            });
            BulkResponse bulkResponse = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            log.info("?????????{}?????????????????????????????????{}", indexName, !bulkResponse.hasFailures());
            return !bulkResponse.hasFailures();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ????????????
     * @author wangzg
     * @date 2021/7/6 10:45
     * @param indexName
     * @param docId
     * @param jsonObject
     * @return boolean
     */
    public boolean updateDoc(String indexName, String docId, JSONObject jsonObject) {
        try {
            UpdateRequest updateRequest = new UpdateRequest(indexName, docId).doc(JSON.toJSONString(jsonObject), XContentType.JSON);
            UpdateResponse updateResponse = esClient.update(updateRequest, RequestOptions.DEFAULT);
            int total = updateResponse.getShardInfo().getTotal();
            log.info("??????????????????????????????{}",total);
            return total > 0;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ??????id????????????
     * @author wangzg
     * @date 2021/7/6 10:52
     * @param indexName
     * @param docId
     * @return com.alibaba.fastjson.JSONObject
     */
    public JSONObject queryDocById(String indexName, String docId) {
        JSONObject jsonObject = new JSONObject();
        try {
            GetRequest getRequest = new GetRequest(indexName, docId);
            GetResponse getResponse = esClient.get(getRequest, RequestOptions.DEFAULT);
            jsonObject = (JSONObject) JSONObject.toJSON(getResponse.getSource());
        }catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * ?????????????????????map????????????????????????????????????????????????
     * @author wangzg
     * @date 2021/7/6 11:38
     * @param indexName
     * @param pageNum
     * @param pageSize
     * @param highName
     * @param andMap
     * @param orMap
     * @param dimAndMap
     * @param dimOrMap
     * @return com.example.elasticsearchdemo.common.lang.PageResult<java.util.List<com.alibaba.fastjson.JSONObject>>
     */
    public PageResult<List<JSONObject>> conditionSearch(String indexName, Integer pageNum, Integer pageSize, String highName, Map<String, Object> andMap, Map<String, Object> orMap, Map<String, Object> dimAndMap, Map<String, Object> dimOrMap) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indexName);
        //??????????????????
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = buildMultiQuery(andMap, orMap, dimAndMap, dimOrMap);
        sourceBuilder.query(boolQueryBuilder);
        //????????????
        if (!StringUtils.isEmpty(highName)) {
            buildHighlight(sourceBuilder, highName);
        }
        //????????????
        buildPageLimit(sourceBuilder, pageNum, pageSize);
        //????????????
        sourceBuilder.timeout(TimeValue.timeValueSeconds(60));
        searchRequest.source(sourceBuilder);

        //????????????
        SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits searchHits = searchResponse.getHits();
        List<JSONObject> resultList = new ArrayList<>();
        for (SearchHit hit : searchHits) {
            //????????????????????????
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //????????????
            if (!StringUtils.isEmpty(highName)) {
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                HighlightField highlightField = highlightFields.get(highName);
                if (highlightField != null) {
                    Text[] fragments = highlightField.fragments();
                    StringBuilder value = new StringBuilder();
                    for (Text text : fragments) {
                        value.append(text);
                    }
                    sourceAsMap.put(highName, value.toString());
                }
            }
            JSONObject jsonObject =  JSONObject.parseObject(JSONObject.toJSONString(sourceAsMap));
            resultList.add(jsonObject);
        }

        long total = searchHits.getTotalHits().value;
        PageResult<List<JSONObject>> pageResult = new PageResult<>();
        pageResult.setPageNum(pageNum);
        pageResult.setPageSize(pageSize);
        pageResult.setTotal(total);
        pageResult.setList(resultList);
        pageResult.setTotalPage(total==0?0: (int) (total % pageSize == 0 ? total / pageSize : (total / pageSize) + 1));

        return pageResult;
    }

    /**
     * ?????????????????????
     * @author wangzg
     * @date 2021/7/6 11:04
     * @param andMap
     * @param orMap
     * @param dimAndMap
     * @param dimOrMap
     * @return org.elasticsearch.index.query.BoolQueryBuilder
     */
    public BoolQueryBuilder buildMultiQuery(Map<String, Object> andMap, Map<String, Object> orMap, Map<String, Object> dimAndMap, Map<String, Object> dimOrMap) {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //?????????true???????????????
        boolean searchAllFlag = true;
        //???????????????and
        if (!CollectionUtils.isEmpty(andMap)) {
            for (Map.Entry<String, Object> entry : andMap.entrySet()) {
                MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(entry.getKey(), entry.getValue());
                boolQueryBuilder.must(matchQueryBuilder);
            }
            searchAllFlag = false;
        }
        //???????????????or
        if (!CollectionUtils.isEmpty(orMap)) {
            for (Map.Entry<String, Object> entry : andMap.entrySet()) {
                MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(entry.getKey(), entry.getValue());
                boolQueryBuilder.should(matchQueryBuilder);
            }
            searchAllFlag = false;
        }
        //???????????????and
        if (!CollectionUtils.isEmpty(dimAndMap)) {
            for (Map.Entry<String, Object> entry : andMap.entrySet()) {
                WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery(entry.getKey(), "*" + entry.getValue() + "*");
                boolQueryBuilder.must(wildcardQueryBuilder);
            }
            searchAllFlag = false;
        }
        //???????????????or
        if (!CollectionUtils.isEmpty(dimOrMap)) {
            for (Map.Entry<String, Object> entry : andMap.entrySet()) {
                WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery(entry.getKey(), "*" + entry.getValue() + "*");
                boolQueryBuilder.should(wildcardQueryBuilder);
            }
            searchAllFlag = false;
        }
        if (searchAllFlag) {
            MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
            boolQueryBuilder.must(matchAllQueryBuilder);
        }

        return boolQueryBuilder;
    }

    /**
     * ??????????????????
     * @author wangzg
     * @date 2021/7/6 11:12
     * @param sourceBuilder
     * @param highName
     */
    public void buildHighlight(SearchSourceBuilder sourceBuilder, String highName) {
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        //??????????????????
        highlightBuilder.field(highName);
        //??????????????????
        highlightBuilder.requireFieldMatch(false);
        //??????????????????
        highlightBuilder.preTags("<span style='color:red'>");
        //??????????????????
        highlightBuilder.postTags("</span>");

        sourceBuilder.highlighter(highlightBuilder);
    }

    /**
     * ????????????
     * @author wangzg
     * @date 2021/7/6 11:33
     * @param sourceBuilder
     * @param pageNum
     * @param pageSize
     */
    public void buildPageLimit(SearchSourceBuilder sourceBuilder, Integer pageNum, Integer pageSize) {
        if (sourceBuilder!=null && !StringUtils.isEmpty(pageNum) && !StringUtils.isEmpty(pageSize)) {
            sourceBuilder.from(pageSize * (pageNum-1) );
            sourceBuilder.size(pageSize);
        }
    }

}
