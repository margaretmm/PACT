package com.microService.pact;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.TargetRequestFilter;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import com.github.restdriver.clientdriver.ClientDriverRequest;
import com.github.restdriver.clientdriver.ClientDriverRule;
import org.apache.http.HttpRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.github.restdriver.clientdriver.RestClientDriver.giveEmptyResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.onRequestTo;

@RunWith(PactRunner.class)
@Provider("Provider")
@PactFolder("D:/code/SprakProject-master/target/pacts")
public class PACTProviderPostTest {
    // NOTE: this is just an example of embedded service that listens to requests, you should start here real service
    @ClassRule
    public static final ClientDriverRule embeddedPactSender = new ClientDriverRule(80);
    private static final Logger LOGGER = LoggerFactory.getLogger(PACTProviderPostTest.class);
    @TestTarget
    public final Target ProviderTarget = new HttpTarget("http", "www.baidu.com",80);

    @BeforeClass
    public static void setUpService() {
        //Run DB, create schema
        //Run service
        //...
    }

    @Before
    public void before() {
        // Rest data
        // Mock dependent service responses
        // ...
        String reqBody="\"jsonrpc\": \"2.0\",\n" +
                "            \"method\": \"user.login\",\n" +
                "            \"params\": {\n" +
                "                \"user\": \"Admin\",\n" +
                "                \"password\": \"zabbix\"\n" +
                "            },\n" +
                "            \"id\": 1,\n" +
                "            \"auth\": None\n" +
                "            }";

        embeddedPactSender.addExpectation(
                onRequestTo("/zabbix/php/api_jsonrpc.php").withMethod(ClientDriverRequest.Method.POST)//.withHeader("Content-Type", "application/json;charset=UTF-8").withBody(reqBody,"application/json-rpc")
                , giveEmptyResponse()
//                , RestClientDriver.giveResponse(" \"body\": {\n" +
//                        "          \"jsonrpc\": \"2.0\",\n" +
//                        "          \"result\": \"d4e93c316df3eb4970e1e85afd9b7fe2\",\n" +
//                        "          \"id\": 1\n" +
//                        "        }").withStatus(200)
        );
//            ClientDriverRule embeddedProvider();
    }

    @State("default")
    public void toDefaultState() {
        // Prepare service before interaction that require "default" state
        // ...
        LOGGER.info("Now service in default state");
    }

    @State("UserA exists and is not an administrator")
    public void toSecondState(Map params) {
        // Prepare service before interaction that require "state 2" state
        // ...
        System.out.print("~~~~~~~~~~~~Now service in 'state 2' state: " + params);
        LOGGER.info("Now service in 'state 2' state: " + params);
    }

    @TargetRequestFilter
    public void exampleRequestFilter(HttpRequest request) {
        LOGGER.info("exampleRequestFilter called: " + request);
    }

    @After
    public void after() {
        embeddedPactSender.reset();
    }
}