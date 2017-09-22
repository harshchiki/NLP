package com.iontrading.rom.nlp.ner.trainingsetgeneration.enums;

public enum Dates {
	DATE_1("25-may-2017"),
//	DATE_2("25-May-2017"),
	DATE_3("25/may/2017"),
	DATE_4("25-05-2017"),
	DATE_5("25/05/2017"),
	DATE_6("25-5-2017"),
	DATE_7("25/5/2017"),
//	DATE_8("25/"),
	
	DATE_9("tod"),
	DATE_10("today"),
	
	DATE_11("tom"),
	DATE_12("tomm"),
	DATE_13("tomorrow"),
	
//	DATE_14("Tod"),	
//	DATE_15("Today"),
//	
//	DATE_16("Tom"),
//	DATE_17("Tomm"),
//	DATE_18("Tomorrow"),
	
//	DATE_19("TOD"),
//	DATE_20("TODAY"),
//	
//	DATE_21("TOM"),
//	DATE_22("TOMM"),
//	DATE_23("TOMORROW")
	;

	private String description;
	
	Dates(String description){
		this.description=description;
	}
	
	public String getDescription(){
		return description;
	}
}
