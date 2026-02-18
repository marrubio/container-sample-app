export default function Footer() {
  return (
    <footer style={{
      background: '#222', color: '#fff', padding: '1rem 0', textAlign: 'center', marginTop: '2rem', fontSize: '0.9rem',
      fontFamily: "'Press Start 2P', 'VT323', 'Consolas', 'monospace'"
    }}>
      &copy; {new Date().getFullYear()} sample-frontend. All rights reserved.
    </footer>
  );
}
