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
import com.microService.pact.util.httpserver;
import com.sun.net.httpserver.HttpServer;
import org.apache.http.HttpRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

import static com.github.restdriver.clientdriver.RestClientDriver.giveEmptyResponse;
import static com.github.restdriver.clientdriver.RestClientDriver.onRequestTo;

@RunWith(PactRunner.class)
@Provider("ProviderBaidu")
@PactFolder("D:/code/pact/target/pacts")
public class PACTProviderGetTest {
    // NOTE: this is just an example of embedded service that listens to requests, you should start here real service
    @ClassRule
    public static final ClientDriverRule embeddedPactSender = new ClientDriverRule(80);
    private static final Logger LOGGER = LoggerFactory.getLogger(PACTProviderGetTest.class);
    @TestTarget
    public final Target ProviderTarget = new HttpTarget("http", "127.0.0.1",80);

    @BeforeClass
    public static void setUpService() throws IOException {
        //Run DB, create schema
        //Run service
        HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);
        server.createContext("/", new httpserver.TestHandler());
        server.start();
        //...
    }

    @Before
    public void before() {
        // Rest data
        // Mock dependent service responses
        // ...
        String reqBody="";

        embeddedPactSender.addExpectation(
                onRequestTo("/").withMethod(ClientDriverRequest.Method.GET)//.withHeader("Content-Type", "application/json;charset=UTF-8").withBody(reqBody,"application/json-rpc")
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
        System.out.print("~~~~1~~~~~~~~Now service in 'state 1' state: ");
        LOGGER.info("Now service in default state");
    }

    @State("get from baidu")
    public void toSecondState(Map params){
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