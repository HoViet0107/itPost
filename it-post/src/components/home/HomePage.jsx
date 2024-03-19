import { jwtDecode } from "jwt-decode";
import { useLocalStorage } from "@/localStorage/UseLocalStorage";
import { useEffect, useState } from "react";
import ajax from "@/service/ajax.js";

export default function HomePage() {
  const [jwt, setJwt] = useLocalStorage("", "jwt");

  const isTokenExp = () => {
    const decoded = jwtDecode(jwt);
    /* lấy exp và so sánh với thời gian hiện tại*/
    const exp = decoded.exp * 1000; // Chuyển đổi giây thành milisecond
    const currentTIme = new Date().getTime();
    if (exp < currentTIme) {
      return true;
    } else {
      return false;
    }
  };

  // get posts
  const [posts, setPosts] = useState({
    id: null,
    content: "",
    postedOn: "",
    numsOfLike: "",
    numsOfDislike: "",
    numsOfComment: "",
    numsOfShare: "",
    imgs: [],
    tags: [],
    user: [],
  });
  const [avatar, setAvatar] = useState({
    post_id: null,
    img_link: "",
  });

  useEffect(() => {
    ajax("posts", jwt, "GET").then((postData) => {
      setPosts(postData);
    });
  }, [jwt]);

  useEffect(() => {
    if (posts.length > 0) {
      let i = 0;
      Object.keys(posts).map((key) => {
        if (posts[key].user.avaIimgs[i].img_link.includes("Ava ")) {
          const [prefix, link] =
            posts[key].user.avaIimgs[i].img_link.split("Ava ");
          setAvatar({ post_id: posts[key].id, img_link: link });
        }
        i++;
      });
    }
  }, [posts]);
  useEffect(() => {}, [avatar]);

  const click = () => {
    // let i = 0;
    // Object.keys(posts).map((key) => {
    //   console.log(avatar[posts[key].user.name]);
    //   i++;
    // });
    console.log(avatar);
    Object.keys(avatar).map((key) => {
      console.log(avatar[key].img_link);
    });
  };

  return (
    <div className="home-container">
      {posts.length > 0 &&
        Object.keys(posts).map((key) => {
          return (
            <div key={posts[key].id} className="post-wrapper">
              <div className="user-info">
                <img className="user-img" src={avatar[posts[key].id]} alt="" />
                <p className="user-name">{posts[key].user.name}</p>
              </div>
              <p className="post-content">{posts[key].content}</p>
              <h1>Tạo lớp Posts dto nhá. lấy mãi đ dc</h1>
            </div>
          );
        })}
      <button onClick={click}>click</button>
    </div>
  );
}
