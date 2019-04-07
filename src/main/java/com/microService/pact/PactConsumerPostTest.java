package com.microService.pact;

import au.com.dius.pact.consumer.ConsumerPactTestMk2;
import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import com.microService.pact.util.ConsumerClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;

@PactFolder("D:/PycharmProjects/PACT/PACT")
public class PactConsumerPostTest extends ConsumerPactTestMk2 {

    private  String reqBody="\"jsonrpc\": \"2.0\",\n" +
            "            \"method\": \"user.login\",\n" +
            "            \"params\": {\n" +
            "                \"user\": \"Admin\",\n" +
            "                \"password\": \"zabbix\"\n" +
            "            },\n" +
            "            \"id\": 1,\n" +
            "            \"auth\": None\n" +
            "            }";

    private  String respBody=" \"body\": {\n" +
            "          \"jsonrpc\": \"2.0\",\n" +
            "          \"result\": \"d4e93c316df3eb4970e1e85afd9b7fe2\",\n" +
            "          \"id\": 1\n" +
            "        }";

    @Override
    @Pact(provider = "Provider",consumer = "test_consumer")
    protected RequestResponsePact createPact(PactDslWithProvider pactDslWithProvider) {
        Map<String, String> headers = new HashMap<String, String>();
//        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json;charset=UTF-8");

        return pactDslWithProvider
                .given("UserA exists and is not an administrator") // NOTE: Using provider states are optional, you can leave it out
                .uponReceiving("a request for UserA")
                .path("/zabbix/php/api_jsonrpc.php")
                .method("POST")
                .headers(headers)
                .body(reqBody)
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(respBody)
                .toPact();
    }

    @Override
    protected String providerName() {
        return "Provider";
    }

    @Override
    protected String consumerName() {
        return "test_consumer";
    }

    @Override
    protected void runTest(MockServer mockServer) throws IOException {
        String host="127.0.0.1";
        String url = mockServer.getUrl();

        System.out.println("mockServer url:"+ url);

        Map expectedResponse = new HashMap();
//            expectedResponse.put("responsetest", true);
//            expectedResponse.put("name", "harry");
      //  assertEquals(new ConsumerClient(url).postBody("/", zabbixBody,APPLICATION_JSON), expectedResponse); //String path, String body, ContentType mimeType
        //int ret = new ConsumerClient(url).postBodyStatus("/test",host,reqBody,APPLICATION_JSON);
        //assertEquals( 200,ret);

        String retCon = new ConsumerClient(url).postBody("/zabbix/php/api_jsonrpc.php",host,reqBody,APPLICATION_JSON);
        System.out.println("ConsumerClient ret:"+ retCon);
    }

}