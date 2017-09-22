package com.iontrading.rom.nlp.ner.trainingsetgeneration.enums;


/*
 * In training set generation we could possibly not have all the quantities to be used,
 * and so can add abstract methods in this enum to refine to a fewer number of quantities.
 */
public enum Quantities {
	
	Q1("5.3mm"),
//	Q9("10mm"),

//	Q7("$16.5MM"),

	Q2("2.4m"),
//	Q8("24m"),

//	Q5("$16.5M"),
	Q6("$16.5 M"),
	
	Q3("8,000,000"),
	
	Q4("45mil"),
	
	Q10("5.3mios"),
//	Q11("24mios"),
	
//	Q_Bill_1("20bil"),
	Q_Bill_2("10 bil"),
	
	Q_Billion_3("30 billion"),
//	Q_Billion_4("50billion"),
	
	Q_BIO_1("20bio"),
//	Q_BIO_2("30 bio"),
	Q_BIO_3("2.4 bio"),
	
	Q_BIOS_1("20bios"),
//	Q_BIOS_2("30 bios"),
	Q_BIOS_3("2.4 bios"),
	
	Q_BLN_1("10bln"),
	Q_BLN_2("12 bln"),
	Q_BLN_3("4.5 bln"),
	
	Q_BN_1("10bn"),
	Q_BN_2("12 bn"),
	Q_BN_3("3.4 bn"),
	
	Q_B_1("40b"),
//	Q_B_2("50 b"),
//	Q_B_3("5.6 b"),
	
	Q_K_1("30k"),
//	Q_K_2("2.6k"),
//	Q_K_3("30 k"),
//	Q_K_4("2.6 k"),
	
	Q_NUMBER_1("20,000,000"),
	Q_NUMBER_2("80000000"),
//	Q_NUMBER_3("500,000"),	
	Q_NUMBER_4("10,000,000,000");
	
	
	
	private String description;
	
	Quantities(String description){
		this.description=description;
	}
	
	public String getDescription(){
		return description;
	}
}
