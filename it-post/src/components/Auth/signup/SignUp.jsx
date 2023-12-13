import { useState } from "react";
import "./style.scss";
import { ToastContainer, toast } from "react-toastify";
import { validateField } from "@/customFunc/validate";
import ajax from "@/service/ajax";

export function SignUp() {
  const [isVisible, setIsVisible] = useState({
    name: true,
    phone: true,
    dob: true,
    email: true,
    pass_word: true,
    confirmPassword: true,
  });

  const [user, setUser] = useState({
    name: "",
    nick_name: "",
    phone: "",
    dob: null,
    email: "",
    pass_word: "",
    avatar_link: "",
    banner_link: "",
  });
  const [confirmPw, setConfirmPw] = useState("");

  const updateUser = (props, value) => {
    const newUser = { ...user };
    newUser[props] = value;
    setUser(newUser);
  };

  const updateVisible = (props, value) => {
    const newVisible = { ...isVisible };
    newVisible[props] = value;
    setIsVisible(newVisible);
  };

  const persist = () => {
    ajax("auth/sign-up", null, "POST", user).then((message) => {
      console.log(message);
      toast.success(message.message);
    });
  };
  const sendSignUpRequest = () => {
    const validateName = validateField(user.name, "Họ tên");
    const validatePhone = validateField(user.phone, "Số điện thoại");
    const validateEmail = validateField(user.email, "Email");
    const validatePassword = validateField(user.pass_word, "Mật khẩu");

    if (validateName !== true) {
      toast.error(validateName);
      return;
    }

    if (user.pass_word !== confirmPw) {
      toast.error("Mật khẩu không trùng khớp!");
      return;
    }

    if (user.dob === null) {
      toast.error("Ngày sinh không được bỏ trống!");
      return;
    }

    if (validatePhone !== true) {
      toast.error(validatePhone);
      return;
    }

    if (validateEmail !== true) {
      toast.error(validateEmail);
      return;
    }

    if (validatePassword !== true) {
      toast.error(validatePassword);
      return;
    }

    persist();
  };

  return (
    <div className="sign-up-container">
      <div className="row">
        {/* name */}
        <div className="items">
          <span className="inner-item">
            <input
              className="local-input sign-up-input"
              title="Nhập họ tên của bạn!"
              placeholder={"Họ Tên"}
              onChange={(e) => {
                updateUser("name", e.target.value);
                if (e.target.value.length == 0) {
                  updateVisible("name", true);
                } else {
                  updateVisible("name", false);
                }
              }}
            />
            {isVisible.name === true ? (
              <span style={{ color: "red" }}>*</span>
            ) : (
              <span style={{ color: "white" }}>*</span>
            )}
          </span>
        </div>
        <span className="space"></span>
        {/* dob */}
        <span className="inner-item">
          <input
            className="dob"
            title=""
            type="date"
            placeholder="Ngày sinh"
            name="dob"
            onChange={(e) => {
              updateUser("dob", e.target.value);
              if (e.target.value.length == 0) {
                updateVisible("dob", true);
              } else {
                updateVisible("dob", false);
              }
            }}
          />
          {isVisible.dob === true ? (
            <span style={{ color: "red" }}>*</span>
          ) : (
            <span style={{ color: "white" }}>*</span>
          )}
        </span>
      </div>

      <div className="row">
        {/* phone */}
        <div className="items">
          <span className="inner-item">
            <input
              className="local-input sign-up-input"
              title="Nhập số điện thoại của bạn!"
              placeholder={"Số điện thoại"}
              type="tel"
              maxLength={10}
              onKeyDown={(evt) => evt.key === "e" && evt.preventDefault()}
              onChange={(e) => {
                updateUser("phone", e.target.value);
                if (e.target.value.length == 0) {
                  updateVisible("phone", true);
                } else {
                  updateVisible("phone", false);
                }
              }}
            />
            {isVisible.phone === true ? (
              <span className="info" style={{ color: "red" }}>
                *
              </span>
            ) : (
              <span style={{ color: "white" }}>*</span>
            )}
          </span>
        </div>
        <span className="space"></span>
        {/* nick name */}
        <div className="items">
          <span className="inner-item">
            <input
              className="local-input sign-up-input"
              title="Nhập nick name của bạn!"
              placeholder={"Nick name"}
              onChange={(e) => {
                updateUser("nick_name", e.target.value);
              }}
            />

            <span style={{ color: "white" }}>*</span>
          </span>
        </div>
      </div>

      {/* email */}
      <div className="items">
        <span className="inner-item">
          <input
            className="local-input sign-up-input"
            title="Nhập email của bạn. VD: email@example.com"
            style={{ width: "100%" }}
            placeholder={"Email"}
            onChange={(e) => {
              updateUser("email", e.target.value);
              if (e.target.value.length == 0) {
                updateVisible("email", true);
              } else {
                updateVisible("email", false);
              }
            }}
          />
          {isVisible.email === true ? (
            <span className="info" style={{ color: "red" }}>
              *
            </span>
          ) : (
            <span style={{ color: "white" }}>*</span>
          )}
        </span>
      </div>

      <div className="row">
        {/* pass word */}
        <div className="items">
          <span className="inner-item">
            <input
              className="local-input sign-up-input"
              title="Nhập mật khẩu. Mật khẩu tối đa 16 ký tự. Phải chứa tối thiểu 1 ký tự in hoa và số!"
              placeholder={"Mật khẩu"}
              onChange={(e) => {
                updateUser("pass_word", e.target.value);
                if (e.target.value.length == 0) {
                  updateVisible("pass_word", true);
                } else {
                  updateVisible("pass_word", false);
                }
              }}
            />
            {isVisible.pass_word === true ? (
              <span style={{ color: "red" }}>*</span>
            ) : (
              <span style={{ color: "white" }}>*</span>
            )}
          </span>
        </div>
        <span className="space"></span>
        {/*confirm pass word */}
        <div className="items">
          <span className="inner-item">
            <input
              className="local-input sign-up-input"
              title="Nhập lại mật khẩu"
              placeholder={"Nhập lại mật khẩu"}
              onChange={(e) => {
                setConfirmPw(e.target.value);
                if (e.target.value.length == 0) {
                  updateVisible("confirmPassword", true);
                } else {
                  updateVisible("confirmPassword", false);
                }
              }}
            />
            {isVisible.confirmPassword === true ? (
              <span style={{ color: "red" }}>*</span>
            ) : (
              <span style={{ color: "white" }}>*</span>
            )}
          </span>
        </div>
      </div>

      <div className="local-btn sign-up-btn" onClick={sendSignUpRequest}>
        Đăng ký tài khoản
      </div>
      <ToastContainer autoClose={3000} />
    </div>
  );
}
