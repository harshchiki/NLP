package com.iontrading.rom.nlp.ner.trainingsetgeneration.enums;

public enum Rates {
	
	R1("0.5%"),
	R2("0.2"),	
	R4("1.9%"),
	R5("18.56"),
	
	R_NEGATIVE_1("-0.5%"),
	R_NEGATIVE_2("-0.2");
	
	private String description;
	
	Rates(String description){
		this.description=description;
	}
	
	public String getDescription(){
		return description;
	}
}
