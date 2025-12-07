FROM node:22-alpine as build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build -- --configuration production

FROM nginx:alpine
# Tutaj uwzględniamy podfolder "browser", który tworzy nowy builder
COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html
# Kopiujemy config nginx (z tą zmienną API_URL)
COPY nginx.conf /etc/nginx/templates/default.conf.template
EXPOSE 80