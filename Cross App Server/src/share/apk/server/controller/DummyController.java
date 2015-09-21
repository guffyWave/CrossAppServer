package share.apk.server.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import share.apk.server.dto.ApkShareUser;
import share.apk.server.exceptions.EmailIDException;
import share.apk.server.exceptions.EmptyStringException;
import share.apk.server.exceptions.PhoneNumberException;
import share.apk.server.management.ServerResult;

@Controller
public class DummyController {

	@RequestMapping(value = "/dummyURL", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> doWork() throws EmptyStringException, EmailIDException,
			PhoneNumberException {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		ApkShareUser asu = new ApkShareUser();
		asu.setEmailID("guffy1267@gmail.com");
		asu.setId(78523);
		asu.setGcmID("ASdfjgi4596fHIUHUU80sdf034udf3hs0HHsfdhdu343AS");
		asu.setPhoneNumber("+917042935653");

		// /------>> Result
		map.put("result", ServerResult.SUCCESS);
		map.put("messsage", "successfully returned shop.");
		map.put("user", asu);

		return map;
	}
}
