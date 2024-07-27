import React, { useEffect, useState } from 'react';

function EmployeeList() {
    const [employees, setEmployees] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/employees')
            .then(response => response.json())
            .then(data => {
                if (Array.isArray(data)) {
                    setEmployees(data);
                } else {
                    setEmployees([]);
                }
            })
            .catch(error => console.error('Error fetching employees:', error));
    }, []);

    return (
        <div>
            <h1>Employee List</h1>
            {employees.length === 0 ? (
                <p>No employees found.</p>
            ) : (
                <ul>
                    {employees.map(employee => (
                        <li key={employee.id}>
                            {employee.firstName} {employee.lastName}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

export default EmployeeList;
