function submitAttendance() {
	

    	
    var formData = {
        employeeId: parseInt(document.getElementById("employeeId").value),
        checkIn: document.getElementById("checkIn").value,
        breakTime: document.getElementById("breakTime").value,
        endTime: document.getElementById("endTime").value
    };

	
    
    fetch('/attendance/submit', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
    })
    .then(response => response.text())
    .then(data => {
        alert(data);
    })
    .catch((error) => {
        console.error('Error:', error);
    });
    
    
}
/**
 * 
 */