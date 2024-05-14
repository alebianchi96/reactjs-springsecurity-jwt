import React, { useState, useContext } from 'react';
import { Navigate } from 'react-router-dom';
import { AuthContext } from '../auth/AuthContext';
import { listEmployees, addEmployee, updtEmployee, deleteEmployee } from '../service/employee/employee.service';

const AuthenticatedPage = () => {

    const { isAuthenticated, logoutInContext } = useContext(AuthContext);

    const [employees, setEmployees] = useState([]);

    const show_employees = async () => {

        try {
            let _res = await listEmployees();
            setEmployees(_res.data);
        } catch (error) {

            let { response } = error;

            if (response?.status === 403) {
                console.error("unauthorized");
                console.error(error);

                // TODO - redirect user to login_page
                logoutInContext();

            } else {
                console.log("err-listEmployees", error);

                // TODO - use logger

            }

        }


    };

    return (
        isAuthenticated ? (<>
            <div>
                <button onClick={logoutInContext}>Logout</button>
            </div>

            <br />

            <div>
                TODO:
                <ul>
                    <li>Gestione secrets lato BE: usa vault (al momento sono nelle properties) _
                        <i>https://spring.io/projects/spring-vault#samples</i>
                    </li>
                    <li>Commit di entrambi i progetti: basic e jwt</li>
                    <li>Gestione scadenza tocken dopo 3 secondi: non viene refreshato ad ogni chiamata</li>
                </ul>
            </div>

            <br />

            <div>
                <button onClick={show_employees}>call list Employees</button>
                <ul>
                    {employees?.map(e => (
                        <li key={e.employeeId}>{e.employeeId + ' - ' + e.name}</li>
                    ))}
                </ul>
            </div>

            <div>
                <button onClick={addEmployee}>add Employees</button>
                <button onClick={updtEmployee}>update Employees</button>
                <button onClick={deleteEmployee}>delete Employees</button>
            </div>
        </>) : (<Navigate to="/login" />)
    );
};

export default AuthenticatedPage;
