package cn.bdqn.itrip.controller;

import cn.itrip.common.DtoUtil;
import cn.itrip.dao.itripAreaDic.ItripAreaDicMapper;
import cn.itrip.dao.itripLabelDic.ItripLabelDicMapper;
import cn.itrip.dto.Dto;
import cn.itrip.pojo.ItripAreaDic;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("api")
public class hotCityController {
    @Resource
    ItripAreaDicMapper mapper;
    @Resource
    ItripLabelDicMapper mapper1;

     @RequestMapping("aa")
     @ResponseBody
     public void  test(){
         String i="asdasdada";
         System.out.println(i);
     }




     @RequestMapping(value="/hotel/queryhotcity/{type}",method = RequestMethod.GET,produces = "application/json")

    public @ResponseBody Dto searchHotCity(@PathVariable("type")Integer i){
        List<ItripAreaDic> list=mapper.hotCity(i);
        return DtoUtil.returnDataSuccess(list);
    }


    @RequestMapping(value = "hotel/queryhotelfeature",method = RequestMethod.GET,produces = "application/json")
    public @ResponseBody Dto getHotel(){

        return DtoUtil.returnDataSuccess( mapper1.getThotel());
    }

    @RequestMapping(value="/hotel/querytradearea/{cityId}",method = RequestMethod.GET,produces = "application/json")
    public @ResponseBody Dto searchArea(@PathVariable("cityId")Integer cityId){
        List<ItripAreaDic> list=mapper.Area(cityId);
        return DtoUtil.returnDataSuccess(list);
    }
}
