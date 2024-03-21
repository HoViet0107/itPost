import { Link } from "react-router-dom";
import reactLogo from "@/assets/react.svg";
import { useLocalStorage } from "@/localStorage/UseLocalStorage.js";
import "./header.scss";
import { jwtDecode } from "jwt-decode";

export default function Header() {
  // eslint-disable-next-line no-unused-vars
  const [jwt, setJwt] = useLocalStorage("", "jwt");

  const isTokenExp = () => {
    let decoded = "";
    if (jwt !== "") {
      decoded = jwtDecode(jwt);
    }
    /* lấy exp và so sánh với thời gian hiện tại*/
    const exp = decoded.exp * 1000; // Chuyển đổi giây thành milisecond
    const currentTIme = new Date().getTime();
    if (exp < currentTIme || jwt === "") {
      return true;
    } else {
      return false;
    }
  };
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
        {isTokenExp() === true ? (
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
