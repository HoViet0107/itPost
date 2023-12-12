const ajax = async (url, jwt, method, body) => {
  const fetchData = {
    headers: {
      "Content-Type": "application/json",
    },
    method: method,
  };

  if (jwt) {
    fetchData.headers.Authorization = `Bearer ${jwt}`;
  }

  if (body) {
    fetchData.body = JSON.stringify(body);
  }

  const fetchResponse = await fetch(
    `http://localhost:8888/api/v1/${url}`,
    fetchData
  );

  if (fetchResponse.status === 200) {
    return await fetchResponse.json();
  } else {
    return await fetchResponse.json();
  }
};

export default ajax;
