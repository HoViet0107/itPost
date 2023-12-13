import { jwtDecode } from "jwt-decode";
import { useLocalStorage } from "@/localStorage/UseLocalStorage";

export default function HomePage() {
  const [jwt, setJwt] = useLocalStorage("", "jwt");

  const isTokenExp = () => {
    const decoded = jwtDecode(jwt);
    /* lấy exp và so sánh với thời gian hiện tại*/
    const exp = decoded.exp * 1000; // Chuyển đổi giây thành milisecond
    const currentTIme = new Date().getTime();
    return exp < currentTIme;
  };

  const click = () => {};

  return (
    <div>
      <h1>Home page!</h1>
      <button onClick={click}>click</button>
    </div>
  );
}
