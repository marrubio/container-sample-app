# Dockerfile para Next.js en producción con Node.js
# Multi-stage build for minimal production image with nginx
FROM node:20-alpine AS builder
WORKDIR /app
COPY ../package.json package-lock.json* ./
RUN npm install --frozen-lockfile || npm install
COPY .. .
COPY ../.env.production .env.production
RUN cp .env.production .env && npm run build

# --- Production image ---
FROM nginx:alpine
COPY --from=builder /app/out /usr/share/nginx/html
COPY docker/nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
