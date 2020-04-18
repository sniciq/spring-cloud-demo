package sn;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpService {
    final static Logger logger = LoggerFactory.getLogger(HttpService.class);

    CloseableHttpClient httpclient;
    CookieStore cookieStore;
    HttpContext httpContext;

    public HttpService() {
        try {
            this.init();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() throws Exception {
        httpclient = HttpClients.createDefault();
        cookieStore = new BasicCookieStore();
        httpContext = new BasicHttpContext();
        httpContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
    }

    public void dispose() {
        try {
            cookieStore.clear();
            httpclient.close();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public String doGet(String url, Map<String, String> headers) throws Exception {
        logger.info("get... " + url);
        if(url.startsWith("//")) {
            url = "http:" + url;
        }

        HttpGet get = new HttpGet(url);
        if(headers != null) {
            for(Map.Entry<String, String> entry : headers.entrySet()) {
                get.addHeader(entry.getKey(), entry.getValue());
            }
        }
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(get, httpContext);
            HttpEntity entity = response.getEntity();
            logger.info("get done " + url);
            return EntityUtils.toString(entity, "UTF-8");
        } finally {
            if(response != null) {
                response.close();
            }
        }
    }

    public static Image getImage(String url) throws Exception {
        CloseableHttpResponse response = null;
        CloseableHttpClient client = null;
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(500)
                    .setConnectTimeout(500)
                    .setSocketTimeout(500)
                    .build();

            HttpGet get = new HttpGet(url);
            get.setConfig(requestConfig);
            client = HttpClients.createDefault();
            response = client.execute(get);
            return ImageIO.read(response.getEntity().getContent());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
        finally {
            if(response != null) {
                response.close();
            }
            if(client != null) {
                client.close();
            }
        }
    }

    public String doPost(String url, Map<String, String> obj, Map<String, String> headers) throws Exception {
        HttpPost post = new HttpPost(url);
        RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(60000).build();
        post.setConfig(config);

        if(headers != null) {
            for(Map.Entry<String, String> entry : headers.entrySet()) {
                post.addHeader(entry.getKey(), entry.getValue());
            }
        }

        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        for(Map.Entry<String, String> entry : obj.entrySet()) {
            formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        post.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));

        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(post, httpContext);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, "UTF-8");
        }
        finally {
            if(response != null) {
                response.close();
            }
        }
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }
}
