package cn.bdqn.itrip.controller;

import cn.bdqn.itrip.dao.DaoSolr;
import cn.bdqn.itrip.entity.ItripHotelVO;
import cn.bdqn.itrip.entity.SearchHotCityVO;
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
import java.util.List;

@Controller
@RequestMapping("api")
public class Search {

    @RequestMapping(value = "/hotellist/searchItripHotelListByHotCity",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto getList(@RequestBody SearchHotCityVO vo) throws IOException, SolrServerException {
        DaoSolr daoSolr=new DaoSolr();
        SolrQuery solrQuery=new SolrQuery();
        solrQuery.addFilterQuery("cityId:"+vo.getCityId());
        List<ItripHotelVO> list=daoSolr.getList(solrQuery,vo.getCount());
        return DtoUtil.returnDataSuccess(list);
    }
}
