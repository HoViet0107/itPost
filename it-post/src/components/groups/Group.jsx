import { useState } from "react";

export default function Group() {
  const [isLogin, setIsLogin] = useState(false);
  return (
    <div>
      {isLogin ? (
        <div>Groups !</div>
      ) : (
        <div>Bận cần đăng nhập để có thể truy cập!</div>
      )}
    </div>
  );
}
