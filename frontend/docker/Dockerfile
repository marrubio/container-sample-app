# Dockerfile para Next.js en producción compatible con OpenShift
FROM node:20-alpine AS builder
WORKDIR /app
COPY ../package.json package-lock.json* ./
RUN npm install --frozen-lockfile || npm install
COPY .. .
RUN npm run build

FROM node:20-alpine AS runner
WORKDIR /app
ENV NODE_ENV=production
COPY --from=builder /app/.next .next
COPY --from=builder /app/public public
COPY --from=builder /app/package.json ./
COPY --from=builder /app/node_modules ./node_modules
EXPOSE 3000
# OpenShift ejecuta como usuario no root, asegúrate de que /app sea escribible
RUN addgroup -g 1001 appgroup && adduser -D -u 1001 -G appgroup appuser && chown -R 1001:appgroup /app
USER 1001
CMD ["npm", "start"]
