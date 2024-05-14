import React, { useState, useContext } from 'react';
import { Navigate } from 'react-router-dom';
import { AuthContext } from '../auth/AuthContext';

const LoginPage = () => {

    const { isAuthenticated, loginInContext } = useContext(AuthContext);

    const [loginForm, setLoginForm] = useState({});


    async function tryLogin(e) {
        e.preventDefault();
        loginInContext({
            "loginForm": loginForm,
            "resetLoginForm": () => {
                document.getElementById("login_form").reset();
                setLoginForm({});
            }
        });
    }

    function addToLoginForm(e) {
        const { name, value } = e.target;
        setLoginForm((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    }

    return (
        !isAuthenticated ? (<div>
            <form id="login_form" onSubmit={tryLogin}>
                <input onChange={addToLoginForm} name='username' placeholder='username'></input>
                <input onChange={addToLoginForm} name='password' placeholder='password' type='password'></input>
                <button type="submit">Login default user</button>
            </form>
        </div>) : (<Navigate to="/" />)

    );
};

export default LoginPage;
