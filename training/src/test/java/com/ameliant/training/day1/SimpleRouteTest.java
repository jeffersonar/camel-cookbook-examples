package com.ameliant.training.day1;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SimpleRouteTest {

    CamelContext context;

    @Before
    public void setUp() throws Exception {
        context = new DefaultCamelContext();
        context.addRoutes(new SimpleRoute());
        context.start();
    }

    @After
    public void tearDown() throws Exception {
        context.stop();
    }

    @Test
    public void testRoute() throws InterruptedException {
        MockEndpoint mockOut =
                (MockEndpoint) context.getEndpoint("mock:out");
        mockOut.setExpectedMessageCount(1);
        mockOut.message(0).body().isEqualTo("Hello Oslo");

        ProducerTemplate template = context.createProducerTemplate();
        template.sendBody("direct:in", "Oslo");

        mockOut.assertIsSatisfied();
    }

}