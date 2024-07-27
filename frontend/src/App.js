import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import ProjectList from './components/ProjectList';
import ProjectDetail from './components/ProjectDetail';
import EmployeeList from './components/EmployeeList';
import TimesheetList from './components/TimesheetList';

const App = () => {
    return (
        <Router>
            <div>
                <Routes>
                    <Route exact path="/" element={<ProjectList />} />
                    <Route path="/projects/:id" element={<ProjectDetail />} />
                    <Route path="/projects/:id/employees" element={<EmployeeList />} />
                    <Route path="/projects/:id/timesheets" element={<TimesheetList />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
