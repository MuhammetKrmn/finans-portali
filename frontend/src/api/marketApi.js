import api from './axiosInstance';

export const getExchangeRates = () =>
    api.get('/market/rates');

export const getStocks = () =>
    api.get('/market/stocks');

export const getPriceHistory = (symbol, range = '1m') =>
    api.get(`/prices/${encodeURIComponent(symbol)}`, { params: { range } });