package com.fuse.spring.example.workshop.impl;

import java.util.Properties;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SaySomethingTest extends CamelSpringTestSupport {

	
	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/camel-context.xml");
	}
	
	// override this method to provide our custom properties we use in this unit test
	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent() {
	    Properties extra = new Properties();
	    extra.put("timer-endpoint", "direct:my-test-endpoint");
	    extra.put("service-endpoint", "direct:my-mock-service");
	    return extra;
	}
	 
	@Override
	protected Boolean ignoreMissingLocationWithPropertiesComponent() {
		return true;
	}

	@Test
	public void testSpringProperties() throws Exception {
		
		MockEndpoint endpointResult = getMockEndpoint("mock:result");
		endpointResult.expectedMessageCount(1);
		
		template.sendBody("direct:my-test-endpoint", null);

		endpointResult.assertIsSatisfied();
		
		assertEquals("Cheese", endpointResult.getExchanges().get(0).getIn().getBody());
	}
	
	  @Override
	  protected RouteBuilder createRouteBuilder() {
	      return new RouteBuilder() {
	          public void configure() {
	              from("{{service-endpoint}}").setBody().simple("Cheese", String.class);
	          }
	      };
	  }
}
