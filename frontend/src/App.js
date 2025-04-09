import React from 'react';
import logo from './logo.svg';
import './App.css';
import { UserProvider } from './context/UserContext';
import RecipeList from './components/RecipeList';
import LoginButton from './components/LoginButton';
// import UserProfile from './components/UserProfile';

function App() {
  return (
    <UserProvider>
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <LoginButton />
          {/* <UserProfile /> */}
        </header>
        <main>
          <RecipeList />
        </main>
      </div>
    </UserProvider>
  );
}

export default App;
