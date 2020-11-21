package com.example.LCS.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.LCS.model.LcsRequest;
import com.example.LCS.model.LcsResponse;
import com.example.LCS.model.SetOfString;

@Component
public class LcsTestData {

	public LcsRequest getLcsRequest() {
		LcsRequest lcsRequest = new LcsRequest();
		List<SetOfString> setOfStrings = new ArrayList<SetOfString>();
		SetOfString setOfString1 = new SetOfString();
		SetOfString setOfString2 = new SetOfString();
		setOfString1.setValue("comcast");
		setOfString2.setValue("comcastic");
		setOfStrings.add(setOfString1);
		setOfStrings.add(setOfString2);
		lcsRequest.setSetOfStrings(setOfStrings);
		return lcsRequest;
	}

	public LcsResponse getLcsResponse() {
		LcsResponse lcsResponse = new LcsResponse();
		List<SetOfString> lcs = new ArrayList<SetOfString>();
		SetOfString setOfString = new SetOfString();
		setOfString.setValue("comcast");
		lcs.add(setOfString);
		lcsResponse.setLcs(lcs);
		return lcsResponse;
	}
}
