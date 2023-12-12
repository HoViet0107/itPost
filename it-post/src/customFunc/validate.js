export function validateField(value, fieldType) {
  if (!value || value.trim() === "") {
    return `${fieldType} không được bỏ trống!`;
  }

  const noWhiteSpaceRepeatRegex = /^\S+(\s+\S+)*$/;
  if (!noWhiteSpaceRepeatRegex.test(value)) {
    return `${fieldType} không được chứa khoảng trắng trùng lặp và không được bắt đầu hoặc kết thúc bằng khoảng trắng.`;
  }

  if (fieldType === "Họ tên") {
    // Kiểm tra họ tên không chứa ký tự số hoặc ký tự đặc biệt
    const nameRegex = /^[a-zA-Z\u00C0-\u1EF9\s]*$/;
    if (!nameRegex.test(value)) {
      return `${fieldType} không hợp lệ. Họ tên chỉ được chứa chữ cái và dấu cách.`;
    }
  } else if (fieldType === "Số điện thoại") {
    // Kiểm tra định dạng số điện thoại
    const phoneRegex = /^[0-9]+$/;
    if (!phoneRegex.test(value)) {
      return `${fieldType} không hợp lệ`;
    }
    if (value.length !== 10) {
      return `${fieldType} phải có đúng 10 chữ số`;
    }
  } else if (fieldType === "Email") {
    // Kiểm tra định dạng email
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(value)) {
      return `${fieldType} không hợp lệ`;
    }
  } else if (fieldType === "Mật khẩu") {
    // Kiểm tra định dạng mật khẩu
    const passwordRegex = /^(?=.*[A-Z])(?=.*\d)[A-Za-z\d]{3,16}$/;
    if (!passwordRegex.test(value)) {
      return `${fieldType} không hợp lệ. Yêu cầu: ít nhất 1 ký tự viết hoa, 1 chữ số, từ 3 đến 16 ký tự.`;
    }
  }

  return true;
}
