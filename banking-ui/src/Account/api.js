import axios from 'axios';

const instance = axios.create({
  baseURL: '/api/v1',
  withCredentials: true,
  headers: {
    'Authorization': 'Basic ' + btoa('admin:admin'),
  },
});

instance.interceptors.response.use(
  (response) => response.data,
  (error) => {
    console.error('API Error:', error);
    throw error;
  }
);

export default instance;
