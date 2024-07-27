import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';

const ProjectDetail = () => {
    const { id } = useParams();
    const [project, setProject] = useState(null);

    useEffect(() => {
        fetch(`http://localhost:8080/projects/${id}`)
            .then(response => response.json())
            .then(data => setProject(data));
    }, [id]);

    if (!project) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <h1>{project.name}</h1>
            <div>
                <Link to={`/projects/${id}/employees`}>View Employees</Link>
            </div>
            <div>
                <Link to={`/projects/${id}/timesheets`}>View Timesheets</Link>
            </div>
        </div>
    );
}

export default ProjectDetail;
