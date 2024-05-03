import { useState, useEffect } from 'react';

import { listEmployees, addEmployee, updtEmployee, deleteEmployee } from './service/employee/employee.service';

import { login, logout } from './service/user/user.service';

import './App.css';

function App() {

  const [sessionToken, setSessionToken] = useState(null);

  const [employees, setEmployees] = useState([]);


  useEffect(() => {
    refresh_token();
  }, []);


  function refresh_token() {
    setSessionToken(sessionStorage.getItem("jwt_token"));
  }


  async function _login() {
    let succeded = await login();
    succeded && refresh_token();
  }

  async function _logout() {
    let succeded = await logout();
    succeded && refresh_token();
  }



  const show_employees = async () => {
    let response = await listEmployees();
    setEmployees(response);
  };



  // ====================== components ======================
  const login_page = () => {
    return (
      <div>
        <button onClick={_login}>Login default user</button>
      </div>
    );
  };

  const main_page = () => {
    return (<>
      <div>
        <button onClick={_logout}>Logout</button>
      </div>

      <br />

      <div>
        TODO:
        <ul>
          <li>Gestione secrets lato BE: usa vault (al momento sono nelle properties) _
            <i>https://spring.io/projects/spring-vault#samples</i>
          </li>
          <li>Commit di entrambi i progetti: basic e jwt</li>
          <li>Gestione errore per token scaduto - redirect to login</li>
          <li>Gestione errore per user-password errate - messaggio</li>
          <li>Gestione costanti FE per jwt token e interazione con sessionStorage</li>
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
    </>);
  };


  return (
    <div className="App">
      {sessionToken ? main_page() : login_page()}
    </div>
  );
}

export default App;
