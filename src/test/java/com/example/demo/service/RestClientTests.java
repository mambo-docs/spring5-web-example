package com.example.demo.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.time.Duration;

public class RestClientTests {

    private static final String SERVICE_KEY = "";

    @Test
    public void TEST_001() throws ParserConfigurationException, IOException, SAXException {
        RestTemplate restTemplate = new RestTemplate();

        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson")
                .queryParam("serviceKey", SERVICE_KEY)
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 10)
                .queryParam("startCreateDt", "20200915")
                .queryParam("endCreateDt", "20200916")
                .build(true);
        URI uri = uriComponents.toUri();

        String result = restTemplate.getForObject(uri, String.class);
        printResult(result);

        Assert.assertTrue(result != null);
    }

    @Test
    public void TEST_002() throws IOException, SAXException, ParserConfigurationException {
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson");
        uriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);

        WebClient webClient = WebClient.builder()
                .uriBuilderFactory(uriBuilderFactory)
                .build();

        String result = webClient.get()
                .uri(uriBuilder -> {
                    uriBuilder
                        .queryParam("serviceKey", SERVICE_KEY)
                        .queryParam("pageNo", 1)
                        .queryParam("numOfRows", 10)
                        .queryParam("startCreateDt", "20200915")
                        .queryParam("endCreateDt", "20200916");
                    return uriBuilder.build();
                })
                .exchange()
                .block(Duration.ofMinutes(1))
                .bodyToMono(String.class)
                .block();
        printResult(result);

        Assert.assertTrue(result != null);
    }

    private void printResult(String result) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();

        Document document = documentBuilder.parse(new InputSource(new StringReader(result)));
        Node response = document.getChildNodes().item(0);
        NodeList items = response.getChildNodes().item(1).getFirstChild().getChildNodes();

        int count = items.getLength();
        for(int i = 0; i < count; i++) {
            Node item = items.item(i);
            NodeList attributes = item.getChildNodes();
            for(int j = 0; j < attributes.getLength(); j++) {
                Node attr = attributes.item(j);
                String attrName = attr.getNodeName();
                String attrValue = attr.getTextContent();

                System.out.println(attrName + " : " + attrValue);
            }
            System.out.println("--------------------------");
        }
    }
}
