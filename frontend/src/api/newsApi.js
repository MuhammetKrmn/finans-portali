import api from './axiosInstance';

export const getNews = (category = '', page = 0, size = 20) =>
    api.get('/news', { params: { category, page, size } });

export const getNewsById = (id) =>
    api.get(`/news/${id}`);