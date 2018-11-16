package tp.tpsparql.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

public class HTTPRequestBuilder {



    private URL url;
    private String urlS;
    private HttpURLConnection con;
    private Map<String, String> params;

    public HTTPRequestBuilder(String url, Map<String, String> params) throws IOException {
        this.urlS = url;

        this.params = params;



    }

    public HTTPRequestBuilder(String url) throws IOException {
        this.urlS = url;

        this.params = new TreeMap<>();




    }

    public String doGet() throws Exception {

        StringBuilder sb = new StringBuilder("?");

        for(Map.Entry<String, String> entry : params.entrySet()){

            sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            sb.append('=');
            sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            sb.append('&');

        }

        String resultString = sb.toString();
        resultString = resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;


        this.urlS = this.urlS+resultString;

        this.url = new URL(urlS);
        this.con = (HttpURLConnection) this.url.openConnection();
        this.con.setRequestMethod("GET");
        this.con.setRequestProperty("Content-Type", "application/json");
        this.con.setConnectTimeout(5000);
        this.con.setReadTimeout(5000);


        /* reading response */

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();


        return content.toString();


    }


    public void addParam(String name, String value){
        params.put(name, value);
    }


}
