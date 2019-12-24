package cn.bdqn.itrip.dao;

import cn.bdqn.itrip.entity.ItripHotelVO;
import cn.bdqn.itrip.entity.Page;
import cn.itrip.common.DtoUtil;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import java.io.IOException;
import java.util.List;

public class DaoSolr {
        String url="http://localhost:8070/solr/hotel-Core/";
        HttpSolrClient solrClient;
        public DaoSolr(){
             solrClient=new HttpSolrClient(url);
            solrClient.setParser(new XMLResponseParser());
            solrClient.setConnectionTimeout(500);
        }

        public List<ItripHotelVO> getList(SolrQuery solrQuery,Integer count) throws IOException, SolrServerException {
            //查询条件
             solrQuery.setQuery("*:*");
            //初始位置
              solrQuery.setStart(0);
            //查几个
            solrQuery.setRows(6);
            QueryResponse queryResponse=solrClient.query(solrQuery);
            List<ItripHotelVO> list=queryResponse.getBeans(ItripHotelVO.class);
            return list;
        }

        public Page<ItripHotelVO> getPage(SolrQuery solrQuery,Integer pageNo,Integer pageSize) throws IOException, SolrServerException {
            //查询分页数据，存到list集合中
            solrQuery.setQuery("*:*");
            solrQuery.setStart((pageNo-1)*pageSize);
            solrQuery.setRows(pageSize);
            QueryResponse response=solrClient.query(solrQuery);
            List<ItripHotelVO> list=response.getBeans(ItripHotelVO.class);
            //获取总页数total
            SolrDocumentList solrDocumentList=response.getResults();
            Integer total=new Long(solrDocumentList.getNumFound()).intValue();
            //执行构造函数public Page(int curpage, Integer pagesize, Integer total)
            Page<ItripHotelVO> page=new Page<ItripHotelVO>(pageNo,pageSize,total);
            //将list集合数据存入page类中
            page.setRows(list);
            return  page;
        }
}
