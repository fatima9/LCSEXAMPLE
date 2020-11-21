package com.example.LCS.model;

import static com.example.LCS.util.LcsServiceConstants.LCS_REQUEST_NOTEMPTY;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class LcsRequest {


	@NotEmpty(message = LCS_REQUEST_NOTEMPTY)
	private List<@Valid SetOfString> setOfStrings;
}
