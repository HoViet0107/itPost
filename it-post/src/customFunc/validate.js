export function emailValidate(email) {
  // email

  if (!email || email === "" || email === null) {
    return "Email không được bỏ trống!";
  } else {
    // Kiểm tra định dạng email sử dụng regular expression
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (emailRegex.test(email)) {
      return true;
    } else {
      return "Email không hợp lệ";
    }
  }

  // phone

  // date of birth
}
