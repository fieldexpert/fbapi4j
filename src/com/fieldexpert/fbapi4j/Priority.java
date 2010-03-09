package com.fieldexpert.fbapi4j;

public enum Priority {
	MUST_FIX_1(1), MUST_FIX_2(2), MUST_FIX_3(3), FIX_IF_TIME_4(4), FIX_IF_TIME_5(5), //
	FIX_IF_TIME_6(6), DO_NOT_FIX(7);

	private int value;

	private Priority(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
