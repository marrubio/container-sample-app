import Header from './Header';
import Footer from './Footer';
import { ReactNode } from 'react';

export default function Layout({ children }: { children: ReactNode }) {
  return (
    <div style={{ minHeight: '100vh', display: 'flex', flexDirection: 'column' }}>
      <Header />
      <div style={{ flex: 1, width: '100%', maxWidth: 800, margin: '0 auto' }}>
        {children}
      </div>
      <Footer />
    </div>
  );
}

