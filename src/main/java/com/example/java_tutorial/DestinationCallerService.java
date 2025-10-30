package com.example.java_tutorial;

import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpClientAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class DestinationCallerService {

    private static final String DESTINATION_NAME = "Northwind_app";
    private static final String REL_URL = "/";
    private static final Logger logger = LoggerFactory.getLogger(DestinationCallerService.class);

    public String callSecondApp() throws IOException {
        try {
            HttpDestination destination = DestinationAccessor.getDestination(DESTINATION_NAME)
                    .asHttp();
            HttpClient client = HttpClientAccessor.getHttpClient(destination);
            HttpGet httpGet = new HttpGet(destination.getUri() + REL_URL);
            HttpResponse httpResponse = client.execute(httpGet);

            return IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8);

            /*// 1. Retrieve the destination configuration from the BTP Destination Service
            final HttpDestination destination = DestinationAccessor
                    .getLoader()
                    .tryGetDestination(DESTINATION_NAME)
                    .asHttp()
                    .orElseThrow(() -> new ValidationException("Destination not found: " + DESTINATION_NAME));

            // 2. Get the HttpClient configured for the destination
            final HttpClient client = HttpClientAccessor.getHttpClient(destination);

            // 3. Create the HTTP request for the relative URL
            final HttpGet httpGet = new HttpGet(REL_URL);

            // 4. Execute the request
            final HttpResponse httpResponse = client.execute(httpGet);

            // 5. Read the response content
            final String responseString = IOUtils.toString(
                    httpResponse.getEntity().getContent(),
                    StandardCharsets.UTF_8
            );

            // Check HTTP status code
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                return "Error calling destination. Status: " + httpResponse.getStatusLine().getStatusCode() + ". Response: " + responseString;
            }

            return responseString;*/

        } catch (final Exception e) {
            logger.error(e.toString());

            return "Failed to execute call: " + e.getMessage();
        }
    }
}

