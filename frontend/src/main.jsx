import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import keycloak from './keycloak';
import MainLayout    from './layouts/MainLayout';
import MarketPage    from './pages/MarketPage';
import PortfolioPage from './pages/PortfolioPage';
import NewsPage      from './pages/NewsPage';
import AnalysisPage  from './pages/AnalysisPage';
import ProtectedRoute from './components/ProtectedRoute'; // <-- KALKAN BURADA İÇERİ ALINDI

// Keycloak başlat — sonra uygulamayı render et
keycloak
    .init({
        onLoad: 'check-sso',           // login zorunlu değil ama token varsa kullan
        checkLoginIframe: false,       // iframe sorunlarını önler
        pkceMethod: 'S256',            // güvenlik — PKCE aktif
    })
    .then(() => {
        ReactDOM.createRoot(document.getElementById('root')).render(
            <React.StrictMode>
                <BrowserRouter>
                    <Routes>
                        <Route path="/" element={<MainLayout keycloak={keycloak} />}>
                            <Route index            element={<MarketPage />} />

                            {/* PORTFÖY SAYFASI KALKANIN İÇİNE ALINDI */}
                            <Route
                                path="portfolio"
                                element={
                                    <ProtectedRoute keycloak={keycloak}>
                                        <PortfolioPage keycloak={keycloak} />
                                    </ProtectedRoute>
                                }
                            />

                            <Route path="news"      element={<NewsPage />} />
                            <Route path="analysis"  element={<AnalysisPage />} />
                        </Route>
                    </Routes>
                </BrowserRouter>
            </React.StrictMode>
        );
    })
    .catch((err) => {
        console.error('Keycloak başlatılamadı:', err);
    });