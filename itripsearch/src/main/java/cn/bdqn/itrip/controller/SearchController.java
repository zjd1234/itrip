package cn.bdqn.itrip.controller;

import cn.bdqn.itrip.dao.DaoSolr;
import cn.bdqn.itrip.entity.ItripHotelVO;
import cn.bdqn.itrip.entity.Page;
import cn.bdqn.itrip.entity.SearchHotCityVO;
import cn.bdqn.itrip.entity.SearchHotelVO;
import cn.itrip.common.DtoUtil;
import cn.itrip.dto.Dto;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("api")
public class SearchController {

    @RequestMapping(value = "/hotellist/searchItripHotelPage",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto<Page<ItripHotelVO>> getList(@RequestBody SearchHotelVO vo) throws IOException, SolrServerException {
        DaoSolr solr=new DaoSolr();
        SolrQuery solrQuery=new SolrQuery();
        //关键字
        if(vo.getKeywords()!=null){
            solrQuery.addFilterQuery("keywords:"+vo.getKeywords());
        }
        //目的地
        if (vo.getDestination()!=null){
            solrQuery.addFilterQuery("destination:"+vo.getDestination());
        }
        //星级
        if (vo.getHotelLevel()!=null){
            solrQuery.addFilterQuery("hotelLevel:"+vo.getHotelLevel());
        }
        if (vo.getMinPrice()!=null){
            solrQuery.addFilterQuery("minPrice:["+vo.getMinPrice()+" TO *]");
        }
        if (vo.getMaxPrice()!=null){
            solrQuery.addFilterQuery("maxPrice:[* TO "+vo.getMaxPrice()+"]");
        }
        if (vo.getFeatureIds()!=null){
            String [] list=vo.getFeatureIds().split(",");
            String str="";
            for (int i=0;i<list.length;i++){
                if (i==0){
                   str="featureIds:*,"+list[i]+",*";
                }else {
                    str="or featureIds:*,"+list[i]+",*";
                }
            }
            solrQuery.addFilterQuery(str);
        }

        if (vo.getTradeAreaIds()!=null){
            String [] list=vo.getTradeAreaIds().split(",");
            String str="";
            for (int i=0;i<list.length;i++){
                if (i==0){
                    str="tradingAreaIds:*,"+list[i]+",*";
                }else {
                    str="or tradingAreaIds:*,"+list[i]+",*";
                }
            }
            solrQuery.addFilterQuery(str);
        }
        if (vo.getPageNo()==null){
            vo.setPageNo(1);
        }
        if (vo.getPageSize()==null){
            vo.setPageSize(5);
        }
        Page<ItripHotelVO> page=solr.getPage(solrQuery,vo.getPageNo(),vo.getPageSize());

        return DtoUtil.returnDataSuccess(page);
    }
}
