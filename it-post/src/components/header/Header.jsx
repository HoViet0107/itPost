import { Link } from "react-router-dom";
import reactLogo from "../../assets/react.svg";
import "./header.scss";
import { useState } from "react";

export default function Header() {
  const [isLogin, setIsLogin] = useState(true);
  return (
    <div className="header-container">
      <div className="header-item">
        <Link to="">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </Link>
      </div>
      <div className="header-item">
        <Link className="navigate" to="/home">
          Home
        </Link>
        <Link className="navigate" to="/friends">
          Friends
        </Link>
        <Link className="navigate" to="/groups">
          Groups
        </Link>
      </div>
      <div className="header-item ">
        {isLogin ? (
          <Link className="navigate" to="/login">
            Login
          </Link>
        ) : (
          <div>Profile</div>
        )}
      </div>
    </div>
  );
}
