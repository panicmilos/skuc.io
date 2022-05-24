import jwt_decode from 'jwt-decode';
import { AxiosInstance, AxiosResponse } from 'axios';

const interceptors: { [key: string]: any } = {

};

export function setAxiosInterceptors(axiosInstance: AxiosInstance, onLogout: () => any = () => {}) {
  setAuthorizationHeaderInterceptor(axiosInstance);
  setUnauthorizedRequestInterceptor(axiosInstance, onLogout);
}

export function setAuthorizationHeaderInterceptor(axiosInstance: AxiosInstance) {
  const interceptorName = 'authHeader';

  if(interceptors[interceptorName]) {
    axiosInstance.interceptors.request.eject(interceptors[interceptorName]);
  }

  interceptors[interceptorName] = axiosInstance.interceptors.request.use((config: any) => {
    const token = getToken() || '';

		if(token) {
      config.headers.Authorization = token;
		}
		
    return config;
  });
}

export function setUnauthorizedRequestInterceptor(axiosInstance: AxiosInstance, onLogout: () => any = () => {}) {
  const interceptorName = 'unauthRequest';

  if(interceptors[interceptorName]) {
    axiosInstance.interceptors.response.eject(interceptors[interceptorName]);
  }

  const checkForNewToken = (response: AxiosResponse) => {
    const newToken = response.headers['new-token'];
    if (newToken) {
      sessionStorage.setItem("jwt-token", newToken);
    }
  }

  interceptors[interceptorName] = axiosInstance.interceptors.response.use(function (response) {
    checkForNewToken(response);
    
		return response;
	}, function (error) {
    checkForNewToken(error.response);

		if (401 === error.response.status && getToken()) {
			onLogout();
			console.log("Should logout here");
		} else {
			return Promise.reject(error);
		}
	});
}

export function getToken() {
  return sessionStorage.getItem('jwt-token');
}

export function is2FactCode(str: string): boolean {
  return str.startsWith('2FactAuth ');
}

export function getUserIdFromToken() {
	return decodeToken()?.sub;
}

export function getGroupIdFromToken() {
	return decodeToken()?.groupId;
}

function decodeToken(): any {
	const token = getToken();

	return !!token ? jwt_decode(token.split(' ')[1]) : null;
}