import React from 'react';
import { useUser } from '../context/UserContext';

function LoginButton() {
  const { user } = useUser();
  
  const handleLogin = () => {
    // Redirect to Spring Boot OAuth login endpoint
    window.location.href = 'http://localhost:8080/oauth2/authorization/google';
  };
  
  const handleLogout = () => {
    // Call your backend logout endpoint
    fetch('http://localhost:8080/logout', { 
      method: 'POST',
      credentials: 'include'
    }).then(() => {
      window.location.reload();
    });
  };
  
  return (
    <div>
      {user ? (
        <button onClick={handleLogout}>Logout</button>
      ) : (
        <button onClick={handleLogin}>Login with Google</button>
      )}
    </div>
  );
}

export default LoginButton;