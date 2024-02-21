
	// Array to store unchecked checkbox IDs
	var uncheckedCheckboxId = [];
	// Gets the ID of the reservation form
	var form = document.getElementById("reservationForm");
	// gets the ID of the checkboxes
	var checkboxIdListInput = document.getElementById("checkboxIdList");
	// used for determining the th:action on the reservation form whether it will be add or delete based on the check box state
	function updateFormAction(checkbox) {
	    // Update form action based on checkbox state
	    if (checkbox.checked) {
			// change the th:action to /main/reserved
	        form.action = '/main/reserved';
	    } else {
			// change the th:action to /main/deleted
	        form.action = '/main/deleted';
	
	        // Identify the unchecked checkbox
	        var container = checkbox.value;
	        
	        uncheckedCheckboxId.push(container);
	        // Set the value of the uncheckedCheckboxId input
	        document.getElementById("uncheckedCheckboxIdInput").value = uncheckedCheckboxId;
	    }
	    // Update the checkboxIdListInput value
	    checkboxIdListInput.value = previouslyCheckedIds.join(',');
	}
	


	
	
	
	
	
	
	

