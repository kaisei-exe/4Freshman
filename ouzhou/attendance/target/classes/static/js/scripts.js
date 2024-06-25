


//提交打卡记录功能
function submitAttendance() {
	
		var formData = {
        employeeId: parseInt(document.getElementById("employeeId").value),
        checkIn: document.getElementById("checkIn").value,
        breakTime: document.getElementById("breakTime").value,
        endTime: document.getElementById("endTime").value
    };

	console.log('Form data:', formData);  // 打印表单数据 测试用
	
    
    
    
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

//按员工ID查询功能

console.log()
function searchAttendance() {
            let employeeId = document.getElementById("employeeIdInput").value;
            
            fetch(`/attendance/search?employeeId=${employeeId}`)
                .then(response => response.json())
                .then(data => {
                    let tableBody = document.querySelector('#attendanceTable tbody');
                    tableBody.innerHTML = ''; // 清空表格
                    data.forEach(record => {
                        let row = document.createElement('tr');
                        row.innerHTML =`
                            <td>${record.employeeId}</td>
                            <td>${formatDateTime(record.checkIn)}</td>
                            <td>${formatDateTime(record.breakTime)}</td>
                            <td>${formatDateTime(record.endTime)}</td>
                            <td>${calculateTotalWorkHours(record.checkIn, record.breakTime, record.endTime)}</td>
                        `;
                        tableBody.appendChild(row);
                    });
                })
                .catch(error => console.error('Error fetching attendance records:', error));
        }
        
        

// 计算工作时间
function calculateTotalWorkHours(checkInStr, breakTimeStr, endTimeStr) {
    let checkIn = new Date(checkInStr);       // 开始工作时间
    let breakTime = new Date(breakTimeStr);   // 开始休息时间
    let endTime = new Date(endTimeStr);       // 结束工作时间

    // 计算工作时间（不考虑休息时间）
    let workDuration = endTime.getTime() - checkIn.getTime();

    // 如果有休息时间，需要减去
   if (breakTimeStr && breakTime instanceof Date && !isNaN(breakTime.getTime())) {
        workDuration -= (1000 * 60 * 60); // 减去一小时的毫秒数
    }
    
    // 处理负数工作时长的情况
    if (workDuration < 0) {
        workDuration = 0; // 或者根据实际情况进行处理
    }

    // 将工作时间转换为小时和分钟
    let hours = Math.floor(workDuration / (1000 * 60 * 60));
    let minutes = Math.floor((workDuration % (1000 * 60 * 60)) / (1000 * 60));

    return `${hours}小时 ${minutes}分`;
}


//员工月份打卡时间

document.addEventListener("DOMContentLoaded", function() {
    const filterButton = document.getElementById("filterButton");

    filterButton.addEventListener("click", function() {
        const employeeId = document.getElementById("employeeIdInput").value;
        const year = document.getElementById("yearSelect").value;
   	    const month = document.getElementById("monthSelect").value;
        
		fetch(`/attendance/monthly/${employeeId}/${year}/${month}`)
            .then(response => response.json())
            .then(data => {
				
				console.log('Fetched data:', data);
                // 处理从后端获取的数据
                const tableBody = document.querySelector("table tbody");
                tableBody.innerHTML = ''; // 清空表格内容

                data.attendances.forEach(record => {
                    let row = document.createElement('tr');
                    row.innerHTML =`
                        <td>${record.employeeId}</td>
                        <td>${formatDateTime(record.checkIn)}</td>
                        <td>${formatDateTime(record.breakTime)}</td>
                        <td>${formatDateTime(record.endTime)}</td>
                        <td>${calculateTotalWorkHours(record.checkIn, record.breakTime, record.endTime)}</td>
                    `;
                    tableBody.appendChild(row);
                });
			
                // 显示总工作时长等其他逻辑
                document.getElementById("totalHours").textContent = "该月总工作时长 " + data.totalHours;
            })
            .catch(error => {
                console.error('Error fetching attendance records:', error);
            });
    });
});

function formatDateTime(dateTime) {
    const date = new Date(dateTime);
    return date.toLocaleString();
}
               