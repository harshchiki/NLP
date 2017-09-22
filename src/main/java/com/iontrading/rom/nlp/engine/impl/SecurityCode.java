package com.iontrading.rom.nlp.engine.impl;

public final class SecurityCode {
	private final String code;
	private final String codeType;
	
	
	public SecurityCode(final String code, final String codeType) {
		this.code = code;
		this.codeType = codeType;
	}
	
	public SecurityCode(final String code){
		this.code = code;
		this.codeType = "";
	}
	
	public String getCode(){
		return this.code;
	}
	
	@Override
	public int hashCode(){
		return this.code.hashCode();
	}
	
	@Override
	public boolean equals(Object otherCode){
		if(otherCode.getClass() == SecurityCode.class){
			SecurityCode otherSecCode = (SecurityCode) otherCode;
			if(this.code.equals(otherSecCode.getCode())){
				return true;
			}
		}
		
		if(otherCode.getClass() == String.class) {
			return this.code.equals(otherCode.toString());
		}
		
		return false;
	}
	
	
}
