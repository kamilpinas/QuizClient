package com.pdfgenerator.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class NetworkRequests {
    public static QuestionData getByGET(int id) throws Exception {
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpGet request = new HttpGet("http://127.0.0.1:8080/quiz/question/" + id);
        final Gson gson = new Gson();
        try {
            final HttpResponse response = client.execute(request);
            final HttpEntity entity = response.getEntity();
            final String json = EntityUtils.toString(entity);
            final Type type = new TypeToken<QuestionData>() {
            }.getType();
            final QuestionData files = gson.fromJson(json, type);
            System.out.println("Kod odpowiedzi serwera: " + response.getStatusLine().getStatusCode());
            return files;
        } catch (IOException e) {
            throw new Exception("Problem z zwróceniem JSONA");
        }
    }

    public static void answerData(final AnswerData answers) throws Exception {
        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpPut httpPut = new HttpPut("http://127.0.0.1:8080/quiz/calculate");
        Gson gson = new Gson();
        final String json = gson.toJson(answers);
        try {
            final StringEntity entity = new StringEntity(json);
            httpPut.setEntity(entity);
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json");
            final CloseableHttpResponse response = client.execute(httpPut);
            if (response.getStatusLine().getStatusCode() == 400) {
                System.err.println("STATUS 400");
            } else if (response.getStatusLine().getStatusCode() == 200) {
                System.err.println("STATUS 200,DATA SUCCESSFULLY SENT");
            }
            client.close();
        } catch (UnsupportedEncodingException e) {
            System.out.println("Houston, we have a problem with PUT unsupported encoding");
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            System.out.println("Houston, we have a problem with PUT client protocol");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Houston, we have a problem with PUT input output");
            e.printStackTrace();
        }
    }

    public static void answerDataList(final ArrayList<AnswerData> answers) throws Exception {
        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/quiz/report");
        Gson gson = new Gson();
        final String json = gson.toJson(answers);
        try {
            final StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            final CloseableHttpResponse response = client.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 400) {
                System.err.println("Status 400: Wynik testu negatywny.");
            } else if (response.getStatusLine().getStatusCode() == 200) {
                System.err.println("Status 200: Wynik testu pozytywny.");
            }
            client.close();
        } catch (UnsupportedEncodingException e) {
            System.out.println("Houston, we have a problem with POST unsupported encoding");
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            System.out.println("Houston, we have a problem with POST client protocol");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Houston, we have a problem with POST input output");
            e.printStackTrace();
        }
    }
}

