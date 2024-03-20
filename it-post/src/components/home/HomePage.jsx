import { jwtDecode } from "jwt-decode";
import { useLocalStorage } from "@/localStorage/UseLocalStorage";
import { useEffect, useState } from "react";
import ajax from "@/service/ajax.js";
import "./style.scss";

export default function HomePage() {
  // eslint-disable-next-line no-unused-vars
  const [jwt, setJwt] = useLocalStorage("", "jwt");

  // eslint-disable-next-line no-unused-vars
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

  useEffect(() => {
    ajax("posts", jwt, "GET").then((postData) => {
      setPosts(postData);
    });
  }, [jwt]);

  const click = () => {
    console.log(posts);
  };

  return (
    <div className="home-container">
      {posts.length > 0 &&
        Object.keys(posts).map((key) => {
          // get avatar
          const avatarLink = posts[key].user.ava_imgs;
          const linkRegex = /http.+$/;
          const linkMatch = avatarLink
            ? avatarLink[0].img_link.match(linkRegex)
            : null;
          const link = linkMatch ? linkMatch[0] : null;
          // get post details
          return (
            <div key={posts[key].id} className="post-wrapper">
              {/* post avatar */}
              <div className="user-info">
                <img className="user-avatar" src={link} alt="avatar" />
                <p className="user-name">{posts[key].user.name}</p>
              </div>

              {/* post tags */}
              <div className="post-tags">
                {posts[key].tags.map((tag) => (
                  <div key={tag.id} className="post-tag">
                    #{tag.tagName}
                  </div>
                ))}
              </div>

              {/* post content */}
              <p className="post-content">{posts[key].content}</p>
              <p>{posts[key].postedOn}/posted date</p>
              <p>{posts[key].numsOfLike}/likes</p>
              <p>{posts[key].numsOfDislike}/disLike</p>
              <p>{posts[key].numsOfComment}/comments</p>
              <p>{posts[key].numsOfShare}/shares</p>

              {/* post imgs */}
              <div className="post-imgs">
                {posts[key].imgs.map((img) => {
                  return (
                    <div key={img.id} className="post-img">
                      <img src={img.img_link} alt="img" />
                    </div>
                  );
                })}
              </div>
            </div>
          );
        })}
      <button onClick={click}>click</button>
    </div>
  );
}
