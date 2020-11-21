package com.example.LCS.controller;

import static com.example.LCS.util.LcsServiceConstants.LCS_SERVICE_URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.LCS.model.LcsRequest;
import com.example.LCS.model.LcsResponse;
import com.example.LCS.service.LcsService;
import com.example.LCS.validator.LcsServiceValidator;


/**
* This is a rest controller called to generate the longest common subsequence
* 
* @author Fatima Rehan
*/
@Validated
@RestController
@RequestMapping(LCS_SERVICE_URI)
public class LcsServiceController {

	@Autowired
	private LcsService lcsService;

	@Autowired
	private LcsServiceValidator lcsServiceValidator;

   /**
	* This method is invoked on a post call to compute the lcs for set of strings. 
	* @param lcsRequest object is the parameter to generateLCS method which is passed as a request body.
	* @return LcsResponse a response entity of LcsResponse object
	*/
	@PostMapping
	public ResponseEntity<LcsResponse> generateLCS(@RequestBody @Valid LcsRequest lcsRequest) {

		lcsServiceValidator.validateLcsRequest(lcsRequest);

		LcsResponse lcsResponse = lcsService.generateLCS(lcsRequest);

		return new ResponseEntity<LcsResponse>(lcsResponse, HttpStatus.OK);
	}
}
