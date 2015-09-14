package validators;

public class PhoneNumberValidator {

	public static boolean validatePhoneNumber(String phoneNo) {
		// validate phone numbers of format "1234567890"
		if (phoneNo.matches("^(?:0091|\\+91|0)[7-9][0-9]{9}$"))
			return true;
		else
			return false;
	}
}
