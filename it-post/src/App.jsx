import "./App.css";
import Friend from "./components/friends/Friend";
import Group from "./components/groups/Group";
import Header from "./components/header/Header";
import HomePage from "./components/home/HomePage";
import { Routes, Route } from "react-router-dom";
import Login from "./components/Auth/login/Login";
import { SignUp } from "./components/Auth/signup/SignUp";
import "./globalStyle/global.scss";

function App() {
  return (
    <div className="app-container">
      <Header />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/home" element={<HomePage />} />
        <Route path="/friends" element={<Friend />} />
        <Route path="/groups" element={<Group />} />
        <Route path="/login" element={<Login />} />
        <Route path="/sign-up" element={<SignUp />} />
      </Routes>
    </div>
  );
}

export default App;
