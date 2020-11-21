package com.example.LCS.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.LCS.exception.LcsServiceValidationException;
import com.example.LCS.model.LcsRequest;
import com.example.LCS.model.SetOfString;
import com.example.LCS.util.LcsTestData;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LcsServiceValidatorTest {

	@Autowired
	LcsServiceValidator lcsServiceValidator;

	private LcsRequest lcsRequest;

	@Autowired
	private LcsTestData lcsTestData;

	@BeforeEach
	public void setUp() {
		lcsRequest = lcsTestData.getLcsRequest();
	}

	@Test
	@DisplayName("validate lcs request on unique strings test")
	public void validateLcsRequestTest() {
		assertDoesNotThrow(() -> lcsServiceValidator.validateLcsRequest(lcsRequest));
	}

	@Test
	@DisplayName("validate lcs request on same strings test")
	public void validateLcsRequestOnSameStringsTest() {
		List<SetOfString> setOfStrings = new ArrayList<SetOfString>();
		SetOfString setOfString1 = new SetOfString();
		SetOfString setOfString2 = new SetOfString();
		setOfString1.setValue("ab");
		setOfString2.setValue("ab");
		setOfStrings.add(setOfString1);
		setOfStrings.add(setOfString2);
		lcsRequest.setSetOfStrings(setOfStrings);
		assertThrows(LcsServiceValidationException.class, () -> lcsServiceValidator.validateLcsRequest(lcsRequest));
	}
}
