import { useEffect, useState } from "react";
import { useLocalStorage } from "../../localStorage/UseLocalStorage";
import ajax from "../../service/ajax.js";
import "./login.scss";
import "../../globalStyle/global.scss";

export default function Login() {
  const [jwt, setJwt] = useLocalStorage("", "jwt");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  useEffect(() => {
    console.log(email, " ---- ", password);
  }, [email, password]);

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
    console.log(requestBody);

    ajax("auth/sign-in", jwt, "POST", requestBody)
      .then((response) => {
        console.log(response);
        setJwt(response.token, "jwt");
      })
      .then(() => {
        window.location.href = "home";
      })
      .catch((message) => {
        console.log("error: ", message);
      });
  }

  return (
    <div className="login-container">
      <div className="login-form">
        <div>
          <input
            placeholder="Email"
            onChange={(e) => handleInputChange(e, "email")}
          />
        </div>
        <div>
          <input
            placeholder="Password"
            onChange={(e) => handleInputChange(e, "password")}
          />
        </div>
        <button className="local-btn login" onClick={sendLoginRequest}>
          Đăng nhập
        </button>
        <p>Quên mật khẩu?</p>
        <button className="local-btn sign-up">Tạo tài khoản!</button>
      </div>
    </div>
  );
}
