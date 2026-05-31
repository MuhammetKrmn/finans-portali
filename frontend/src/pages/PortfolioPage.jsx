import { useState, useEffect } from 'react';
import api from '../api/axiosInstance';

export default function PortfolioPage({ keycloak }) {
    const [portfolios, setPortfolios] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        // Eğer kullanıcı giriş yapmışsa, portföylerini backend'den çek
        if (keycloak?.authenticated) {
            api.get('/portfolio')
                .then((res) => {
                    // Backend ApiResponse sınıfında veriyi 'data' içinde dönüyor
                    setPortfolios(res.data.data || []);
                })
                .catch((err) => {
                    console.error("Portföyler getirilirken hata oluştu:", err);
                })
                .finally(() => {
                    setLoading(false);
                });
        }
    }, [keycloak?.authenticated]);

    if (loading) {
        return <div style={{ padding: '20px', color: '#666' }}>Portföyleriniz yükleniyor...</div>;
    }

    return (
        <div style={{ background: '#fff', padding: '24px', borderRadius: '8px', border: '0.5px solid #e5e5e3' }}>
            <h2 style={{ marginTop: 0, color: '#1a1a1a', fontSize: '20px' }}>Portföylerim</h2>

            {portfolios.length === 0 ? (
                <div style={{ padding: '40px 20px', textAlign: 'center', background: '#f8f7f4', borderRadius: '8px' }}>
                    <p style={{ color: '#888', fontSize: '14px', marginBottom: '16px' }}>
                        Henüz bir portföyünüz bulunmuyor.
                    </p>
                    <button style={{
                        padding: '8px 16px', borderRadius: '6px', cursor: 'pointer',
                        background: '#185FA5', color: '#fff', border: 'none', fontSize: '13px'
                    }}>
                        + Yeni Portföy Oluştur
                    </button>
                </div>
            ) : (
                <ul style={{ listStyle: 'none', padding: 0, margin: 0 }}>
                    {portfolios.map(p => (
                        <li key={p.id} style={{
                            padding: '16px', borderBottom: '1px solid #eee',
                            display: 'flex', justifyContent: 'space-between'
                        }}>
                            <span style={{ fontWeight: 500 }}>{p.name}</span>
                            <span style={{ color: '#888', fontSize: '13px' }}>{p.description}</span>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}