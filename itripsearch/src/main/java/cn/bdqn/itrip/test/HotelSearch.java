package cn.bdqn.itrip.test;

import cn.bdqn.itrip.entity.HotelEntity;
import cn.bdqn.itrip.entity.ItripHotelVO;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;


import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class HotelSearch implements Serializable {
    public static void main(String[] args) throws IOException, SolrServerException {
        //请求solr访问地址
        HttpSolrClient solrClient=new HttpSolrClient("http://localhost:8070/solr/hotel-Core/");
        //设置解析器
        solrClient.setParser(new XMLResponseParser());
        //建立连接最大时间
        solrClient.setConnectionTimeout(500);
        //创建查询对象
        SolrQuery solrQuery=new SolrQuery();
        //查询条件
       /* solrQuery.setQuery("keyword:北京");*/
        solrQuery.setQuery("*:*");
        //初始位置
      /*  solrQuery.setStart(0);*/
        //查几个
        /*solrQuery.setRows(10);*/
        //排序：按照id升序排列
        solrQuery.setSort("id",SolrQuery.ORDER.asc);
        QueryResponse queryResponse=solrClient.query(solrQuery);
        List<ItripHotelVO> list=queryResponse.getBeans(ItripHotelVO.class);
        for (ItripHotelVO i:list) {
            System.out.println(i.getId()+":"+i.getHotelName()+"--"+i.getExtendPropertyNames());
        }
    }
}
