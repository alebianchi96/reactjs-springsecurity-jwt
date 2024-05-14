import { BrowserRouter, Route, Routes } from 'react-router-dom';
import AuthenticatedPage from './components/AuthenticatedPage';
import LoginPage from './components/LoginPage';
import { AuthProvider } from './auth/AuthContext';

import './App.css';


function App() {

  return (
    <div className="App">

      <AuthProvider>
        <BrowserRouter>
          <Routes>
            <Route path="/login" element={<LoginPage />} />
            <Route path="/" element={<AuthenticatedPage />} />
          </Routes>
        </BrowserRouter>
      </AuthProvider>

    </div>
  );
}

export default App;
