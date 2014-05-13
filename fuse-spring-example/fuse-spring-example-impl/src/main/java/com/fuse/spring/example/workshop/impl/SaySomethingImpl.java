package com.fuse.spring.example.workshop.impl;

import com.fuse.spring.example.workshop.SaySomething;

public class SaySomethingImpl implements SaySomething {

	@Override
	public String sayCheese() {
		return "Cheese";
	}

	@Override
	public String sayWine() {
		return "Wine";
	}

}
