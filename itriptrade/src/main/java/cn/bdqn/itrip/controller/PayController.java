package cn.bdqn.itrip.controller;

import cn.bdqn.itrip.test.Test;
import cn.bdqn.itrip.util.WXPayConfig;
import cn.bdqn.itrip.util.WXPayRequest;
import cn.bdqn.itrip.util.WXPayUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
@Controller
public class PayController {
    @Resource
    WXPayConfig wxPayConfig;

    //支付
    @RequestMapping(value="/pay1",method=RequestMethod.GET,produces= "application/json")
    @ResponseBody
    public Object pay() throws Exception {
        WXPayRequest wxPayRequest=new WXPayRequest(wxPayConfig);
        Map<String,String> map=new HashMap<>();
        map.put("body","微信支付中心");
        map.put("out_trade_no","451213154");
        map.put("total_fee","100");
        Map<String,String> map1=wxPayRequest.unifiedorder(map);
        String result="";
        if (map1.get("return_code").equals("SUCCESS")){
            result=map1.get("code_url");
        }
        return result;
    }


    //回调
    @RequestMapping(value = "/Pay2", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public void GetPay_1(HttpServletRequest request,HttpServletResponse response) {



        response.addHeader("Access-Control-Allow-Origin","*");
        WXPayRequest wxPayRequest = new WXPayRequest(this.wxPayConfig);
        Map<String, String> result = new HashMap<String, String>();
        Map<String, String> params = null;
        try {
            InputStream inputStream;
            StringBuffer sb = new StringBuffer();
            inputStream = request.getInputStream();
            String s;
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((s = in.readLine()) != null) {
                sb.append(s);
            }
            in.close();
            inputStream.close();
            params = WXPayUtil.xmlToMap(sb.toString());
            boolean flag = wxPayRequest.isResponseSignatureValid(params);
            if (flag) {
                String returnCode = params.get("return_code");
                if (returnCode.equals("SUCCESS")) {
                    String transactionId = params.get("transaction_id");
                    String outTradeNo = params.get("out_trade_no");
                    //业务操作
                    //修改数据库的字段

                } else {
                    String transactionId = params.get("transaction_id");
                    String outTradeNo = params.get("out_trade_no");
                    result.put("return_code", "FAIL");
                    result.put("return_msg", "支付失败");

                    //也需要修改数据库字段

                }
            } else {
                result.put("return_code", "FAIL");
                result.put("return_msg", "签名失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //后台通过httpclient请求url并返回数据
    @RequestMapping(value="/test",method=RequestMethod.GET,produces= "application/json")
    @ResponseBody
    public String test() throws IOException {
        Test test1=new Test();
        Object a=test1.getDataByUrl("http://localhost:8083/trade/pay1");
        return  a.toString();
    }

}
