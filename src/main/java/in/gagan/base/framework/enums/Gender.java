package in.gagan.base.framework.enums;

public enum Gender {
	
	M("Male"),
	F("Female"),
	O("Others");
	
	private final String gender;
	
	Gender(String gender) {
		this.gender = gender;
	}
	
	public String getGender() {
		return this.gender;
	}

}
