package com.microService.pact;

import au.com.dius.pact.consumer.ConsumerPactTestMk2;
import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import com.microService.pact.util.ConsumerClient;
import org.junit.Rule;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@PactFolder("D:/PycharmProjects/PACT/PACT")
public class PactConsumerGetTest extends ConsumerPactTestMk2 {
    @Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("ProviderBaidu", "localhost", 8080, this);

    private  String reqBody="";

    private  String respBody=" {\"responsetest\": true, \"name\": \"harry\"}";

    @Override
    @Pact(provider = "ProviderBaidu",consumer = "ConsumerGet")
    protected RequestResponsePact createPact(PactDslWithProvider pactDslWithProvider) {
        Map<String, String> headers = new HashMap<String, String>();
//        headers.put("Accept", "application/json");
        //headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("testreqheader", "testreqheadervalue");

        return pactDslWithProvider
                .given("get from baidu") // NOTE: Using provider states are optional, you can leave it out
                .uponReceiving("a request for client2baidu")
                    .path("/")
                    .method("GET")
                    .headers(headers)
                .willRespondWith()
                    .status(200)
                    .headers(headers)
                    .body(respBody)
                .toPact();
    }

    @Override
    protected String providerName() {
        return "ProviderBaidu";
    }

    @Override
    protected String consumerName() {
        return "ConsumerGet";
    }

    @Override
    protected void runTest(MockServer mockServer) throws IOException {
        String host="127.0.0.1";
        String url = mockServer.getUrl();

        System.out.println("mockServer url:"+ url);

        Map expectedResponse = new HashMap();
        expectedResponse.put("responsetest", true);
        expectedResponse.put("name", "harry");

        Map retCon = new ConsumerClient(url).getAsMap("/","");
        assertEquals(new ConsumerClient(mockServer.getUrl()).getAsMap("/", ""), expectedResponse);
        System.out.println("ConsumerClient ret:"+ retCon);
    }
}