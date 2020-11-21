package com.example.LCS.validator;

import static com.example.LCS.util.LcsServiceConstants.VALUES_NOT_UNIQUE;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.LCS.exception.LcsServiceValidationException;
import com.example.LCS.model.LcsRequest;
import com.example.LCS.model.SetOfString;

import lombok.extern.slf4j.Slf4j;

/**
* This is a validator which is used to apply the custom validations for the request received
* 
* @author Fatima Rehan
*/
@Slf4j
@Component
public class LcsServiceValidator {

   /**
	* This method is invoked to validate the request for distinct set of strings. 
	* @param lcsRequest object is the parameter to validateLcsRequest method
	* @throws LcsServiceValidationException if the strings are not unique
	*/
	public void validateLcsRequest(LcsRequest lcsRequest) {
		log.info("Validating lcs request");
		List<SetOfString> setOfStrings = lcsRequest.getSetOfStrings();
		if (setOfStrings.stream().distinct().count() != setOfStrings.size()) {
			throw new LcsServiceValidationException(VALUES_NOT_UNIQUE);
		}
	}
}
