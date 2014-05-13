package com.fuse.spring.example.atoc.workshop.impl;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class RemoteInvocationTest extends CamelTestSupport {

	@Test
	public void ensureSpringConfigLoads() {

		template.sendBody("direct:enqueue", "this is a test message!!!!!");

	}

	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {

		return new RouteBuilder() {

			@Override
			public void configure() throws Exception {

				from("direct:enqueue")
						.to("activemq:fuse-spring-example-in-queue?username=admin&password=admin");

			}

		};

	}

}
