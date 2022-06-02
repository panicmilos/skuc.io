const env = (window as any)._env_;

export const API_URL = env.SAME_HOST === 'true' ? 
  `${env.API_PROTOCOL}://${window.location.hostname}:${env.API_PORT}${env.API_PATH}` 
  :
  `${env.API_PROTOCOL}://${env.API_HOSTNAME}:${env.API_PORT}${env.API_PATH}`

export const BACKEND_API = `http://localhost:8080`;
export const SOCKETS_API = `http://localhost:9093`;