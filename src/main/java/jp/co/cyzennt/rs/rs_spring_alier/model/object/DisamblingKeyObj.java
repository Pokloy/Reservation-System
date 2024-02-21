package jp.co.cyzennt.rs.rs_spring_alier.model.object;

import java.util.HashMap;

import lombok.Data;

/**
 * This class is used for Checkbox disable Status Object for CheckBoxes. 
 * @author Alier Torrenueva
 * 01/26/2024
 */
@Data
public class DisamblingKeyObj {
	// Declares hashmap of string as key and boolean as value named "key"
	public HashMap<String, Boolean> key;
}
