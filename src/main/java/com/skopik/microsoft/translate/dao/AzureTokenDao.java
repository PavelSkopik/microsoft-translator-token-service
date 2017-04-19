package com.skopik.microsoft.translate.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skopik.microsoft.translate.model.Token;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

@Repository
public class AzureTokenDao implements TokenDao {

    private static final Logger log = LoggerFactory.getLogger(AzureTokenDao.class);

    private static final int MAX_CONNECTIONS = 5;

    @Value("${microsoft.azure.subscription.key}")
    private String azureKey;

    @Value("${microsoft.azure.subscriptionkeyheader}")
    private String subscriptionHeader;

    @Value("${microsoft.azure.token.resource}")
    private String tokenResource;

    @Autowired
    private ObjectMapper objectMapper;

    private CloseableHttpClient httpClient;

    @PostConstruct
    public void init() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(MAX_CONNECTIONS);
        httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
    }

    @Override
    public Token getToken() {
        return executeRequest();
    }

    private Token executeRequest() {
        try {
            return httpClient.execute(createRequest(), new TokenRequestResponseHandler());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private HttpUriRequest createRequest() throws IOException {
        HttpUriRequest request = new HttpPost(tokenResource);
        request.addHeader(subscriptionHeader, azureKey);
        return request;
    }

    private class TokenRequestResponseHandler implements ResponseHandler<Token> {
        @Override
        public Token handleResponse(HttpResponse httpResponse) throws HttpResponseException, UnknownHostException, IOException {
            StatusLine statusLine = httpResponse.getStatusLine();

            if (statusLine.getStatusCode() >= 300) {
                throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            }

            if (httpResponse.getEntity() == null)
                throw new ClientProtocolException("Response contains no content.");

            return new Token(getStringFromInputStream(httpResponse.getEntity().getContent()));
        }

        private String getStringFromInputStream(InputStream is) {
            BufferedReader reader = null;
            StringBuilder builder = new StringBuilder();
            String line;

            reader = new BufferedReader(new InputStreamReader(is));
            try {
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
            return builder.toString();
        }
    }

}
