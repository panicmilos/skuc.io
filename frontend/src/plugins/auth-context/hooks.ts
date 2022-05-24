import { useContext } from "react";
import { AuthContext } from "./Context";

export type IsAuthorizedParams = {
  permissions?: string[];
  oneOfPermissions?: string[];
};

export type IsAuthorizedFunction = (params?: IsAuthorizedParams) => boolean;

export function useIsAuthorized(): IsAuthorizedFunction {
  const { isAuthenticated, userPermissions } = useContext(AuthContext);

  return ({ permissions = undefined, oneOfPermissions = undefined } = {}) => {
    const hasAllPermissions =
      !permissions ||
      (userPermissions &&
        permissions.reduce<boolean>(
          (acc, curr) => {
            const hasPermissions = !!userPermissions[curr];
            return acc || (curr.startsWith('!') ? !hasPermissions : hasPermissions)
          }, false
        ));

    const hasOneOfPermissions =
      !oneOfPermissions ||
      (userPermissions &&
        oneOfPermissions.reduce<boolean>(          
          (acc, curr) => acc || userPermissions[curr],
          false
        ));

    return hasAllPermissions && hasOneOfPermissions && isAuthenticated;
  };
}

export type IsUnauthorizedFunction = () => boolean;

export function useIsUnauthorized(): IsUnauthorizedFunction {
  const { isAuthenticated } = useContext(AuthContext);

  return () => !isAuthenticated;
}