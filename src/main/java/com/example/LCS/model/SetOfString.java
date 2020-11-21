package com.example.LCS.model;

import static com.example.LCS.util.LcsServiceConstants.VALUE_NOT_EMPTY;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class SetOfString {

	@NotBlank(message = VALUE_NOT_EMPTY)
	private String value;

}
