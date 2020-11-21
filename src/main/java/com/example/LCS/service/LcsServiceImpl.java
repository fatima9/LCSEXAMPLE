package com.example.LCS.service;

import static com.example.LCS.util.LcsServiceConstants.RUNTIME_EXCEPTION;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.LCS.exception.LcsServiceException;
import com.example.LCS.model.LcsRequest;
import com.example.LCS.model.LcsResponse;
import com.example.LCS.model.SetOfString;

import lombok.extern.slf4j.Slf4j;

/**
* This service is invoked by the controller to compute the lcs for a given set of strings
* 
* @author Fatima Rehan
*/
@Slf4j
@Service
public class LcsServiceImpl implements LcsService {

   /**
	* This method is used to generate the longest common subsequence 
	* @param lcsRequest object is the parameter to generateLCS method
	* @return LcsResponse object which is a set of longest common subsequence for the given strings
	*/
	@Override
	public LcsResponse generateLCS(LcsRequest lcsRequest) {
		try {
			List<SetOfString> setOfStrings = lcsRequest.getSetOfStrings();
			return computeLcs(setOfStrings);
		} catch (Exception ex) {
			log.error("An error has occurred while computing the LCS" + ex);
			throw new LcsServiceException(RUNTIME_EXCEPTION);
		}
	}

   /**
    * This method is used to compute the lcs for all the strings in the set
    * and is used by generateLCS method 
    * @param setOfStrings is the parameter to computeLcs method 
    * which is a list of SetOfString objects
    * @return LcsResponse object which is a set of longest common subsequence for the given strings
	*/
	private LcsResponse computeLcs(List<SetOfString> setOfStrings) {
		Set<String> setOfLcs = new HashSet<String>();
		String lcs = setOfStrings.get(0).getValue();
		for (int i = 1; i < setOfStrings.size(); i++) {
			lcs = longestCommonSubstring(setOfStrings.get(i), lcs);
		}
		setOfLcs.add(lcs);
		return buildLcsResponse(setOfLcs);
	}

   /**
	* This method is used to build the LcsResponse which is a set of lcs for the given set of 
	* strings
	* @param setOfLcs is the parameter to buildLcsResponse method 
	* which is a set of lcs generated
	* @return LcsResponse object which is a set of longest common subsequence for the given strings
    */
	private LcsResponse buildLcsResponse(Set<String> setOfLcs) {
		LcsResponse lcsResponse = new LcsResponse();
		List<SetOfString> lcsList = new ArrayList<SetOfString>();
		for (String lcs : setOfLcs) {
			SetOfString setOfString = new SetOfString();
			setOfString.setValue(lcs);
			lcsList.add(setOfString);
		}
		lcsResponse.setLcs(lcsList);
		return lcsResponse;
	}

   /**
	* This method actually applies the algorithm for computing the longest common substring 
	* for the two given strings. 
	* <p>
	* It uses dynamic programming approach to construct a two dimensional array, 
	* which stores the length of longest length substring at each index and gets the longest length
	* indices
	* <p>
	* @param setOfString1 object is the first parameter to longestCommonSubstring method
	* @param str2 is the second parameter to longestCommonSubstring method
	* @return String this returns the lcs string for the given 2 strings
    */
	private String longestCommonSubstring(SetOfString setOfString1, String str2) {
		String str1 = setOfString1.getValue();
		int result = 0;
		int x = -1;
		int y = -1;
		int[][] lengths = new int[str2.length() + 1][str1.length() + 1];
		for (int i = 1; i < str2.length() + 1; i++) {
			for (int j = 1; j < str1.length() + 1; j++) {
				if (str2.charAt(i - 1) == str1.charAt(j - 1)) {
					lengths[i][j] = lengths[i - 1][j - 1] + 1;
				} else {
					lengths[i][j] = 0;
				}

				if (lengths[i][j] > result) {
					result = lengths[i][j];
					x = i;
					y = j;
				}
			}

		}
		return buildSubstring(lengths, str1, result, x, y);
	}

   /**
    * This method actually builds the lcs string from the two dimensional array of 
    * consisting of the lengths of the longest common substring
	* @param lengths a two dimensional array is the first parameter to longestCommonSubstring
	* @param str is the second parameter to buildSubstring method
	* @param result longest length for the subsequence
	* is the second parameter to buildSubstring method
	* @param x the row index for the longest length for the subsequence in the two dimensional array
	* @param y the column index for the longest length for the subsequence in the two dimensional array
	* @return String a longest common substring generated from the two dimensional array of lengths
	*/
	private String buildSubstring(int[][] lengths, String str, int result, int x, int y) {
		StringBuilder seq = new StringBuilder();
		while (result != 0) {
			seq.insert(0, str.charAt(y - 1));
			x--;
			y--;
			result--;
		}
		return seq.toString();
	}

}
