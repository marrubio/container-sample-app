import Image from 'next/image';

export default function Header() {
  return (
    <header style={{
      background: '#000', color: '#fff', padding: '1rem 0', textAlign: 'center', marginBottom: '2rem'
    }}>
      <img src="/logo_S.png" alt="Logo" width={872} height={328} style={{ verticalAlign: 'middle' }} />
    </header>
  );
}

