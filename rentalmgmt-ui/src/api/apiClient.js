import axios from 'axios';
 
const apiClient = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL,
    headers: {
        "Content-Type": "application/json", // app send data in json format
        Accept: "application/json", // app receive data in json format
    },
    timeout: 10000,
    withCredentials: true,
});
 
export default apiClient;