package com.example.LCS.service;

import com.example.LCS.model.LcsRequest;
import com.example.LCS.model.LcsResponse;

public interface LcsService {
	
	LcsResponse generateLCS(LcsRequest lcsRequest);
}
