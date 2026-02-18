"use client";
import { useKeycloak } from "@react-keycloak/web";
import axios from "axios";
import { useEffect, useState } from "react";
import { BACKEND_URL } from "./config";
import Layout from "../components/Layout";

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
    let isMounted = true;
    if (initialized && keycloak?.authenticated) {
      (async () => {
        if (!isMounted) return;
        setLoading(true);
        try {
          const res = await axios.get(`${BACKEND_URL}/games`, {
            headers: {
              Authorization: `Bearer ${keycloak.token}`,
            },
          });
          if (isMounted) setGames(Array.isArray(res.data) ? res.data : []);
        } finally {
          if (isMounted) setLoading(false);
        }
      })();
    }
    return () => {
      isMounted = false;
    };
  }, [initialized, keycloak]);

  if (!initialized)
    return (
      <Layout>
        <div>Authentication loading...</div>
      </Layout>
    );
  if (!keycloak?.authenticated)
    return (
      <Layout>
        <button className="pixelart-btn" onClick={() => keycloak?.login()}>Login</button>
      </Layout>
    );
  return (
    <Layout>
      <h1>Games list</h1>
      <button className="pixelart-btn" onClick={() => keycloak?.logout()}>Logout</button>
      {loading ? (
        <p>Loading games...</p>
      ) : (
        <ul className="pixelart-list">
          {games.map((game) => (
            <li className="pixelart-list-item" key={game.id}>
              <strong>{game.title}</strong> ({game.developmentYear})
              <br />
              {game.description}
              <br />
              Score: {game.score}
            </li>
          ))}
        </ul>
      )}
    </Layout>
  );
}
