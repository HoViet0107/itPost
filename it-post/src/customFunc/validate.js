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
    const passwordRegex = /^(?=.*\d)[A-Za-z\d]{3,16}$/;
    if (!passwordRegex.test(value)) {
      return `${fieldType} không hợp lệ. Phải có ít nhất 1 ký tự , 1 chữ số, dài 3 đến 16 ký tự.`;
    }
  }

  return true;
}

// export time like facebook
export function formatTime(postedOn) {
  const currentTime = new Date();
  const postTime = new Date(postedOn);
  const timeDifference = Math.floor((currentTime - postTime) / 1000);

  // Chuyển thời gian thành giây, phút, giờ, hoặc ngày
  if (timeDifference < 60) {
    return timeDifference + " giây trước";
  } else if (timeDifference < 3600) {
    return Math.floor(timeDifference / 60) + " phút trước";
  } else if (timeDifference < 86400) {
    return Math.floor(timeDifference / 3600) + " giờ trước";
  } else {
    return Math.floor(timeDifference / 86400) + " ngày trước";
  }
}

// chuyển đổi thời gian sang ngày tháng
export function formatDateTime(timestamp) {
  const dateObject = new Date(timestamp);

  // Lấy thời gian
  const time = dateObject.toLocaleTimeString(); // ví dụ: 14:36:27 PM

  // Lấy ngày tháng
  const options = { year: "numeric", month: "long", day: "numeric" };
  const dateTime = dateObject.toLocaleDateString(undefined, options); // ví dụ: March 21, 2024
  return `${dateTime} at ${time}`;
}
