export default function ProtectedRoute({ keycloak, children }) {
    if (!keycloak?.authenticated) {
        return (
            <div style={{
                display: 'flex', flexDirection: 'column',
                alignItems: 'center', justifyContent: 'center',
                padding: '80px 20px', gap: '16px',
            }}>
                <div style={{ fontSize: '32px' }}>🔒</div>
                <p style={{ color: '#888', fontSize: '14px' }}>
                    Bu sayfayı görmek için giriş yapmalısınız.
                </p>
                <button
                    onClick={() => keycloak.login()}
                    style={{
                        padding: '8px 20px', borderRadius: '8px',
                        background: '#185FA5', color: '#fff',
                        border: 'none', cursor: 'pointer', fontSize: '14px',
                    }}
                >
                    Giriş Yap
                </button>
            </div>
        );
    }
    return children;
}