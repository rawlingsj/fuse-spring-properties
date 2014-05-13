package com.fuse.spring.example.workshop.impl;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class MyRouteBuilder extends RouteBuilder {

	
	@Override
	public void configure() throws Exception {
		
		from("activemq:fuse-spring-example-in-queue").tracing().log("The message contains ${body}").setHeader("name").xpath("//name/text()").setHeader("amount").xpath("//amount/text()").setHeader("customer").xpath("//customer/text()").process(new Processor() {
			
			public void process(Exchange exchange) throws Exception {

				String name = exchange.getIn().getHeader("name", String.class);
				String amount = exchange.getIn().getHeader("amount", String.class);
				String customer = exchange.getIn().getHeader("customer", String.class);
				
				String sqlString = "insert into incoming_orders (part_name, quantity, customer) values ('%s','%s','%s')";
				String sql = String.format(sqlString, name, amount, customer);
				
				exchange.getIn().setBody(sql);
			}
		})
			.to("jdbc:dataSource").to("mock:result");;

	}

}
