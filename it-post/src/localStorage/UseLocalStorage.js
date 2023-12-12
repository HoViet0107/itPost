import { useEffect } from "react";
import { useState } from "react";

function useLocalStorage(defaultValue, key) {
  const [value, setValue] = useState(() => {
    // get localStorageValue in localStorage
    const localStorageValue = localStorage.getItem(key);
    // set initial value
    return localStorageValue !== null
      ? JSON.parse(localStorageValue)
      : defaultValue;
  });

  useEffect(() => {
    localStorage.setItem(key, JSON.stringify(value));
  }, [key, value]);

  return [value, setValue];
}

export { useLocalStorage };
