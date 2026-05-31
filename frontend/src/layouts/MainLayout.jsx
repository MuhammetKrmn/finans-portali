import { Outlet, NavLink } from 'react-router-dom';

const NAV_LINKS = [
    { to: '/',           label: 'Piyasa',   end: true  },
    { to: '/portfolio',  label: 'Portföy',  end: false },
    { to: '/news',       label: 'Haberler', end: false },
    { to: '/analysis',   label: 'Analiz',   end: false },
];

export default function MainLayout({ keycloak }) {
    const user = keycloak?.tokenParsed;

    return (
        <div style={{ minHeight: '100vh', background: '#f8f7f4' }}>
            <nav style={{
                background: '#fff',
                borderBottom: '0.5px solid #e5e5e3',
                padding: '0 24px', height: '48px',
                display: 'flex', alignItems: 'center',
                justifyContent: 'space-between',
                position: 'sticky', top: 0, zIndex: 100,
            }}>
                <div style={{ display: 'flex', alignItems: 'center', gap: '24px' }}>
          <span style={{ fontWeight: 500, fontSize: '15px' }}>
            📈 Finans Portalı
          </span>
                    <div style={{ display: 'flex', gap: '4px' }}>
                        {NAV_LINKS.map(({ to, label, end }) => (
                            <NavLink
                                key={to} to={to} end={end}
                                style={({ isActive }) => ({
                                    fontSize: '13px', padding: '5px 10px',
                                    borderRadius: '6px', textDecoration: 'none',
                                    color:      isActive ? '#1a1a1a' : '#888',
                                    background: isActive ? '#f0f0ed' : 'transparent',
                                    fontWeight: isActive ? 500 : 400,
                                })}
                            >
                                {label}
                            </NavLink>
                        ))}
                    </div>
                </div>

                {/* Kullanıcı alanı */}
                <div style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
                    {keycloak?.authenticated ? (
                        <>
              <span style={{ fontSize: '13px', color: '#666' }}>
                {user?.preferred_username}
              </span>
                            <button
                                onClick={() => keycloak.logout({ redirectUri: 'http://localhost:5173' })}
                                style={{
                                    fontSize: '12px', padding: '5px 12px',
                                    borderRadius: '6px', cursor: 'pointer',
                                    border: '0.5px solid #e5e5e3',
                                    background: 'transparent', color: '#666',
                                }}
                            >
                                Çıkış
                            </button>
                        </>
                    ) : (
                        <button
                            onClick={() => keycloak.login()}
                            style={{
                                fontSize: '12px', padding: '5px 14px',
                                borderRadius: '6px', cursor: 'pointer',
                                border: 'none',
                                background: '#185FA5', color: '#fff',
                            }}
                        >
                            Giriş Yap
                        </button>
                    )}
                </div>
            </nav>

            <main style={{ padding: '24px', maxWidth: '1200px', margin: '0 auto' }}>
                <Outlet />
            </main>
        </div>
    );
}