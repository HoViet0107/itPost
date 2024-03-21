import { jwtDecode } from "jwt-decode";
import { useLocalStorage } from "@/localStorage/UseLocalStorage";
import { useEffect, useState } from "react";
import ajax from "@/service/ajax.js";
import "./style.scss";
import { formatTime } from "@/customFunc/validate";
import { formatDateTime } from "@/customFunc/validate";
import { FaRegHeart } from "react-icons/fa";
import { FaRegComment } from "react-icons/fa";
import { FaRegShareSquare } from "react-icons/fa";
import { FaRegThumbsDown } from "react-icons/fa";

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
    console.log("clicked!");
  };

  // trả về hình ảnh đầu tiên của post
  const PostFirstImg = () => {
    const firstImg = Object.keys(posts).find(
      (key) => posts[key].imgs.length > 0
    );
    if (firstImg) {
      return (
        <div className="post-img-wrapper">
          <img
            className="post-img"
            key={posts[firstImg].imgs[0].id}
            src={posts[firstImg].imgs[0].img_link}
            alt="img"
          />
          <div className="overlay">
            <p>Xem thêm</p>
          </div>
        </div>
      );
    }
    return null; // Trả về null nếu không có hình ảnh trong bài post
  };

  // trả về bài post giới hạn ký tự
  const Post = (content) => {
    const truncateContent = (content) => {
      if (typeof content.content !== "string") {
        // Kiểm tra xem str có phải là một chuỗi không
        return ""; // Trả về một chuỗi rỗng nếu str không phải là chuỗi
      }
      if (content.content.length <= content.maxLength) {
        return content.content;
      }
      return content.content.slice(0, content.maxLength) + "... "; // Cắt chuỗi
    };

    return (
      <div className="post-content">
        <p>
          {truncateContent(content, content.maxLength)}
          {content.content.length > content.maxLength && (
            <span className="show-more-content">Xem thêm</span>
          )}
        </p>
      </div>
    );
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
              {/* <p className="post-content">{posts[key].content}</p> */}
              <Post content={posts[key].content} maxLength={200} />
              {/* post imgs */}
              {posts[key].imgs.length > 0 && <PostFirstImg />}
              <p
                className="post-time"
                // eslint-disable-next-line react/no-unknown-property
                time-title={formatDateTime(posts[key].postedOn)}
              >
                {formatTime(posts[key].postedOn)}
              </p>
              {/* emoji */}
              <div className="post-emoji">
                <div className="post-emoji-item like">
                  <FaRegHeart />
                  <div>{posts[key].numsOfLike}</div>
                </div>
                <div className="post-emoji-item dislike">
                  <FaRegThumbsDown />
                  <div>{posts[key].numsOfDislike}</div>
                </div>
                <div className="post-emoji-item comment">
                  <FaRegComment />
                  <div>{posts[key].numsOfComment}</div>
                </div>
                <div className="post-emoji-item share">
                  <FaRegShareSquare />
                  <div>{posts[key].numsOfShare}</div>
                </div>
              </div>
            </div>
          );
        })}
      <button onClick={click}>click</button>
    </div>
  );
}
