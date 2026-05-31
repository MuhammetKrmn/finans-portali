import { useEffect, useState } from 'react';
import { getExchangeRates, getStocks } from '../api/marketApi';

export default function MarketPage() {
    const [rates,      setRates]      = useState([]);
    const [stocks,     setStocks]     = useState([]);
    const [loading,    setLoading]    = useState(true);
    const [error,      setError]      = useState(null);
    const [activeTab,  setActiveTab]  = useState('rates');

    useEffect(() => {
        setLoading(true);
        Promise.all([getExchangeRates(), getStocks()])
            .then(([ratesRes, stocksRes]) => {
                // ApiResponse<T> formatı: { success, message, data }
                setRates(ratesRes.data.data   ?? []);
                setStocks(stocksRes.data.data ?? []);
            })
            .catch((err) => setError('Veri alınamadı: ' + err.message))
            .finally(() => setLoading(false));
    }, []);

    if (loading) return (
        <div style={{ color: '#888', padding: '40px', textAlign: 'center' }}>
            Yükleniyor...
        </div>
    );

    if (error) return (
        <div style={{ color: '#A32D2D', padding: '40px', textAlign: 'center' }}>
            {error}
        </div>
    );

    const data = activeTab === 'rates' ? rates : stocks;

    return (
        <div>
            {/* Başlık + tab */}
            <div style={{
                display: 'flex', justifyContent: 'space-between',
                alignItems: 'center', marginBottom: '16px',
            }}>
                <h2 style={{ fontSize: '18px', fontWeight: 500, margin: 0 }}>
                    Piyasa Verileri
                </h2>
                <div style={{ display: 'flex', gap: '4px' }}>
                    {[
                        { key: 'rates',  label: 'Döviz' },
                        { key: 'stocks', label: 'Hisse' },
                    ].map(tab => (
                        <button
                            key={tab.key}
                            onClick={() => setActiveTab(tab.key)}
                            style={{
                                padding: '5px 14px', borderRadius: '6px',
                                border: 'none', cursor: 'pointer', fontSize: '13px',
                                fontWeight: activeTab === tab.key ? 500 : 400,
                                background: activeTab === tab.key ? '#185FA5' : '#f0f0ed',
                                color:      activeTab === tab.key ? '#fff'    : '#666',
                                transition: 'all 0.15s',
                            }}
                        >
                            {tab.label}
                        </button>
                    ))}
                </div>
            </div>

            {/* Tablo */}
            <div style={{
                background: '#fff',
                border: '0.5px solid #e5e5e3',
                borderRadius: '12px',
                overflow: 'hidden',
            }}>
                <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                    <thead>
                    <tr style={{ background: '#f8f7f4' }}>
                        {['Sembol', 'İsim', 'Fiyat', 'Değişim', 'Güncelleme'].map(h => (
                            <th key={h} style={{
                                padding: '10px 14px', textAlign: 'left',
                                fontSize: '12px', color: '#888', fontWeight: 500,
                                borderBottom: '0.5px solid #e5e5e3',
                            }}>
                                {h}
                            </th>
                        ))}
                    </tr>
                    </thead>
                    <tbody>
                    {data.length === 0 ? (
                        <tr>
                            <td colSpan={5} style={{
                                padding: '40px', textAlign: 'center', color: '#888',
                            }}>
                                Veri bulunamadı
                            </td>
                        </tr>
                    ) : (
                        data.map((item) => (
                            <tr
                                key={item.symbol}
                                style={{ borderTop: '0.5px solid #f0f0ed' }}
                            >
                                <td style={{ padding: '10px 14px', fontWeight: 500 }}>
                                    {item.symbol}
                                </td>
                                <td style={{ padding: '10px 14px', color: '#666' }}>
                                    {item.name}
                                </td>
                                <td style={{ padding: '10px 14px' }}>
                                    {Number(item.price).toFixed(4)}
                                </td>
                                <td style={{ padding: '10px 14px' }}>
                    <span style={{
                        fontSize: '12px', padding: '2px 7px', borderRadius: '4px',
                        background: item.change >= 0 ? '#EAF3DE' : '#FCEBEB',
                        color:      item.change >= 0 ? '#3B6D11' : '#A32D2D',
                    }}>
                      {item.change >= 0 ? '+' : ''}
                        {Number(item.change).toFixed(2)}%
                    </span>
                                </td>
                                <td style={{ padding: '10px 14px', fontSize: '12px', color: '#bbb' }}>
                                    {item.updatedAt
                                        ? new Date(item.updatedAt).toLocaleTimeString('tr-TR')
                                        : '—'}
                                </td>
                            </tr>
                        ))
                    )}
                    </tbody>
                </table>
            </div>
        </div>
    );
}