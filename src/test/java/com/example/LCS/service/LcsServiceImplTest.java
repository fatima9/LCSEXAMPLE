package com.example.LCS.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.example.LCS.exception.LcsServiceException;
import com.example.LCS.model.LcsRequest;
import com.example.LCS.model.LcsResponse;
import com.example.LCS.model.SetOfString;
import com.example.LCS.util.LcsTestData;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LcsServiceImplTest {

	@Autowired
	LcsService lcsService;

	private LcsRequest lcsRequest;

	@Autowired
	private LcsTestData lcsTestData;

	@BeforeEach
	public void setUp() {
		lcsRequest = lcsTestData.getLcsRequest();
	}

	@Test
	@DisplayName("Generate LCS Success Scenario")
	public void generateLCSOnSuccessTest() {
		LcsResponse lcsResponse = lcsService.generateLCS(lcsRequest);
		assertEquals("comcast", lcsResponse.getLcs().get(0).getValue());
	}

	@Test
	@DisplayName("Generate LCS on Null request")
	public void generateLCSOnNullRequestTest() {
		assertThrows(LcsServiceException.class, () -> lcsService.generateLCS(null));
	}

	@Test
	@DisplayName("Generate LCS on one empty string")
	public void generateLCSOnOneEmptyStringtest() {
		lcsRequest.getSetOfStrings().get(0).setValue(" ");
		LcsResponse lcsResponse = lcsService.generateLCS(lcsRequest);
		assertEquals("", lcsResponse.getLcs().get(0).getValue());
	}

	@Test
	@DisplayName("Generate LCS on one null string")
	public void generateLCSOnOneNullStringtest() {
		lcsRequest.getSetOfStrings().get(0).setValue(null);
		assertThrows(LcsServiceException.class, () -> lcsService.generateLCS(lcsRequest));
	}

	@Test
	@DisplayName("Generate LCS on unique strings")
	public void generateLCSOnUniqueStringstest() {
		List<SetOfString> setOfStrings = new ArrayList<SetOfString>();
		SetOfString setOfString1 = new SetOfString();
		SetOfString setOfString2 = new SetOfString();
		setOfString1.setValue("ab");
		setOfString2.setValue("cd");
		setOfStrings.add(setOfString1);
		setOfStrings.add(setOfString2);
		lcsRequest.setSetOfStrings(setOfStrings);
		LcsResponse lcsResponse = lcsService.generateLCS(lcsRequest);
		assertEquals("", lcsResponse.getLcs().get(0).getValue());
	}

	@Test
	@DisplayName("Generate LCS on only one string")
	public void generateLCSOnOnlyOneStringtest() {
		lcsRequest.getSetOfStrings().remove(1);
		LcsResponse lcsResponse = lcsService.generateLCS(lcsRequest);
		assertEquals("comcast", lcsResponse.getLcs().get(0).getValue());
	}

	@Test
	@DisplayName("Generate LCS with two sequences string")
	public void generateLCSWithTwoSequencesInStringTest() {
		List<SetOfString> setOfStrings = new ArrayList<SetOfString>();
		SetOfString setOfString1 = new SetOfString();
		SetOfString setOfString2 = new SetOfString();
		setOfString1.setValue("comcasttesting");
		setOfString2.setValue("castedtesting");
		setOfStrings.add(setOfString1);
		setOfStrings.add(setOfString2);
		lcsRequest.setSetOfStrings(setOfStrings);
		LcsResponse lcsResponse = lcsService.generateLCS(lcsRequest);
		assertEquals("testing", lcsResponse.getLcs().get(0).getValue());
	}
}
