package nlp.ner.trainingsetgeneration.enums;

public enum EnglishWishes {
	// Good morning
	GOOD_MORNING("Good Morning"),
//	GOOD_MORNING_COMMA("Good Morning,"),
//	GOOD_MORNING_EXCLAMATION("Good Morning!"),
//	GOOD_MORNING_EXCL("Good Morning!,"),
//	
	// Hi
	HI("Hi"),
//	HI_COMMA("Hi,"),
//	HI_EXCL("Hi!"),
//	HI_EXCL_COMMA("Hi!,"),
	
	// Hello
	HELLO("Hello"),
//	HELLO_COMMA("Hello,"),
//	HELLO_EXCL("Hello!"),
//	HELLO_EXCL_COMMA("Hello!,");
	;
	
	private String wish;
	
	EnglishWishes(String wish){
		this.wish=wish;
	}
	
	public String getWish(){
		return wish;
	}
}
