import axios from 'axios';
import keycloak from '../keycloak';

const api = axios.create({
    baseURL: 'http://localhost:8080/api',
    headers: { 'Content-Type': 'application/json' },
    timeout: 10000,
});

api.interceptors.request.use(
    async (config) => {
        if (keycloak.authenticated) {
            try {
                // Token süresi 30 saniyeden az kaldıysa otomatik yenile
                await keycloak.updateToken(30);
            } catch {
                // Yenilenemezse login'e yönlendir
                keycloak.login();
                return Promise.reject(new Error('Token yenilenemedi'));
            }
            config.headers.Authorization = `Bearer ${keycloak.token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response?.status === 401) {
            keycloak.login();
        }
        return Promise.reject(error);
    }
);

export default api;