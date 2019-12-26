package cn.bdqn.itrip.test;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Test {

    public String getDataByUrl(String url) throws IOException {
        HttpClient httpClient=HttpClients.createDefault();
        HttpGet httpGet=new HttpGet(url);
        HttpResponse httpResponse=httpClient.execute(httpGet);
        HttpEntity httpEntity=httpResponse.getEntity();
        String result="";
        if (httpEntity!=null){
            result=EntityUtils.toString(httpEntity,"utf-8");
        }
       return result;
    }

}
