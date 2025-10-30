package com.example.java_tutorial;


import org.apache.olingo.client.api.ODataClient;
import org.apache.olingo.client.api.communication.request.retrieve.ODataEntitySetIteratorRequest;
import org.apache.olingo.client.api.domain.ClientEntity;
import org.apache.olingo.client.api.domain.ClientEntitySet;
import org.apache.olingo.client.api.domain.ClientEntitySetIterator;
import org.apache.olingo.client.core.ODataClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
public class NorthwindProxyController {
    @Value("${NW_DEST}")
    private String destinationUrl;

    @GetMapping("/products")
    public List<String> getProducts() throws Exception {
        ODataClient client = ODataClientFactory.getClient();

        ODataEntitySetIteratorRequest<ClientEntitySet, ClientEntity> request =
                client.getRetrieveRequestFactory()
                        .getEntitySetIteratorRequest(new URI(destinationUrl + "/Products"));

        ClientEntitySetIterator iterator = request.execute().getBody();

        List<String> productNames = new ArrayList<>();
        while (iterator.hasNext()) {
            ClientEntity entity = iterator.next();
            String productName = entity.getProperty("ProductName").getValue().toString();
            productNames.add(productName);
        }

        return productNames;
    }
}
