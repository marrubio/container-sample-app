"use client";
import { useKeycloak } from "@react-keycloak/web";
import axios from "axios";
import { useEffect, useState } from "react";

interface Game {
  id: number;
  title: string;
  description: string;
  score: number;
  developmentYear: number;
}

export default function Home() {
  const { keycloak, initialized } = useKeycloak();
  const [games, setGames] = useState<Game[]>([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (initialized && keycloak?.authenticated) {
      setLoading(true);
      axios
        .get(`${process.env.NEXT_PUBLIC_BACKEND_URL}/games`, {
          headers: {
            Authorization: `Bearer ${keycloak.token}`,
          },
        })
        .then((res) => setGames(res.data))
        .finally(() => setLoading(false));
    }
  }, [initialized, keycloak]);

  if (!initialized) return <div>Cargando autenticación...</div>;
  if (!keycloak?.authenticated)
    return <button onClick={() => keycloak?.login()}>Iniciar sesión</button>;

  return (
    <main>
      <h1>Listado de Juegos</h1>
      <button onClick={() => keycloak?.logout()}>Cerrar sesión</button>
      {loading ? (
        <p>Cargando juegos...</p>
      ) : (
        <ul>
          {games.map((game) => (
            <li key={game.id}>
              <strong>{game.title}</strong> ({game.developmentYear})
              <br />
              {game.description}
              <br />
              Puntuación: {game.score}
            </li>
          ))}
        </ul>
      )}
    </main>
  );
}
