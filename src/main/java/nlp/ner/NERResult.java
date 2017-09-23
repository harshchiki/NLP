package nlp.ner;

import java.util.Optional;


public class NERResult<T> {
	private String type;
	private Optional<T> value;
	
	public NERResult(final String type, final T value){
		this.value = Optional.of(value);
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public Optional<T> getValue() {
		return value;
	}	

	public void setType(String type) {
		this.type = type;
	}
	
	public void setValue(Optional<?> op) {
		this.value = (Optional<T>) op;
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		
		builder.append("type {"+this.type + "} value {" + (this.value.isPresent() ? this.value.get() : "" + "}"));
		
		return builder.toString();
	}
}
