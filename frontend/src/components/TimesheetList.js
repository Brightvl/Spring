import React, { useEffect, useState } from 'react';

function TimesheetList() {
    const [timesheets, setTimesheets] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/timesheets')
            .then(response => response.json())
            .then(data => {
                if (Array.isArray(data)) {
                    setTimesheets(data);
                } else {
                    setTimesheets([]);
                }
            })
            .catch(error => console.error('Error fetching timesheets:', error));
    }, []);

    return (
        <div>
            <h1>Timesheet List</h1>
            {timesheets.length === 0 ? (
                <p>No timesheets found.</p>
            ) : (
                <ul>
                    {timesheets.map(timesheet => (
                        <li key={timesheet.id}>
                            {timesheet.projectId} - {timesheet.minutes} minutes on {timesheet.createdAt}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

export default TimesheetList;
