import api from './axiosInstance';

export const getPortfolios = () =>
    api.get('/portfolio');

export const createPortfolio = (data) =>
    api.post('/portfolio', data);

export const addPortfolioItem = (portfolioId, data) =>
    api.post(`/portfolio/${portfolioId}/items`, data);

export const deletePortfolioItem = (portfolioId, itemId) =>
    api.delete(`/portfolio/${portfolioId}/items/${itemId}`);