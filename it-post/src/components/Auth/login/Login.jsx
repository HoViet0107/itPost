import { useEffect, useState } from "react";
import { useLocalStorage } from "../../../localStorage/UseLocalStorage.js";
import ajax from "../../../service/ajax.js";
import "./login.scss";
import { jwtDecode } from "jwt-decode";
import { validateField } from "../../../customFunc/validate.js";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useNavigate } from "react-router-dom";

// react toast: https://fkhadra.github.io/react-toastify/introduction/

export default function Login() {
  const [jwt, setJwt] = useLocalStorage("", "jwt");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const isTokenExp = () => {
    const decoded = jwtDecode(jwt);
    console.log(decoded);
    //   /* lấy exp và so sánh với thời gian hiện tại*/
    //   const exp = decoded.exp * 1000; // Chuyển đổi giây thành milisecond
    //   const currentTIme = new Date().getTime();
    //   return exp < currentTIme;
  };

  useEffect(() => {
    if (isTokenExp) {
      //   window.location.href = 'home'
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [email, password, jwt]);

  function handleInputChange(e, value) {
    if (value === "email") {
      setEmail(e.target.value);
    } else {
      setPassword(e.target.value);
    }
  }

  function sendLoginRequest() {
    const requestBody = {
      email: email,
      pass_word: password,
    };
    const eValidate = validateField(email, "email");

    if (eValidate && password.length > 3) {
      ajax("auth/sign-in", jwt, "POST", requestBody)
        .then((response) => {
          console.log(response);
          setJwt(response.token, "jwt");
        })
        .then(() => {
          // window.location.href = "home";
        })
        .catch((message) => {
          toast.error(message);
        });
    } else {
      toast.error(eValidate);
    }
  }

  return (
    <div className="login-container">
      <div className="login-form">
        <div>
          <input
            className="login-input local-input"
            placeholder="Email"
            onChange={(e) => handleInputChange(e, "email")}
          />
        </div>
        <div>
          <input
            className="login-input local-input"
            placeholder="Password"
            maxLength={16}
            minLength={3}
            onChange={(e) => handleInputChange(e, "password")}
          />
        </div>
        <button className="local-btn sign-in-btn" onClick={sendLoginRequest}>
          Đăng nhập
        </button>
        <p className="forget-password">Quên mật khẩu?</p>
        <button
          className="local-btn sign-up-btn"
          onClick={() => {
            navigate("../sign-up");
          }}
        >
          Tạo tài khoản!
        </button>
      </div>
      <ToastContainer autoClose={1500} />
    </div>
  );
}
