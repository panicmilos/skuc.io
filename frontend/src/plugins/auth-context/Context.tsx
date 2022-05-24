import axios from "axios";
import { createContext, FC, useEffect, useState } from "react";
import {
  User,
  UsersService,
} from './imports';
import {
  getGroupIdFromToken,
  getToken,
  getUserIdFromToken,
  setAxiosInterceptors,
} from './utils';

type AuthContextValue = {
  isAuthenticated: boolean;
  user?: User,
  setUser: (u: User|undefined) => any,
  setAuthenticated: (a: boolean) => any,
  userPermissions: {[key: string]: boolean},
  setUserPermissions: (permissions: any) => any
}

export const AuthContext = createContext<AuthContextValue>({
  isAuthenticated: false,
  user: undefined,
  setUser: () => {},
  setAuthenticated: () => {},
  userPermissions: {},
  setUserPermissions: () => {}
});

export const AuthContextProvider: FC = ({ children }) => {
  const [isAuthenticated, setAuthenticated] = useState<boolean>(!!getToken());
  const [user, setUser] = useState<User>();
  const [userPermissions, setUserPermissions] = useState<any>({});

  const configureInterceptors = () => {
    setAxiosInterceptors(axios, () => {
      setAuthenticated(false);
      window.location.href = "/";
      sessionStorage.removeItem('jwt-token');
    });
  }

  useEffect(() => {
    if(isAuthenticated) {
      configureInterceptors();
      const groupId = getGroupIdFromToken();
      const userId = getUserIdFromToken();
      const userService = new UsersService(groupId);
      userService.fetch(userId)
        .then(user => {
          setUser(user);
          const permissions = user.roles?.flatMap(role => role.permissions) ?? [];
          setUserPermissions(permissions.reduce((acc: any, perm: string) => { acc[perm] = true; return acc; }, {}))
        })
        .catch(console.log);
    }
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isAuthenticated]);

  return (
    <AuthContext.Provider value={{ user, setUser, isAuthenticated, setAuthenticated, userPermissions, setUserPermissions }}>
      {children}
    </AuthContext.Provider>
  );
}