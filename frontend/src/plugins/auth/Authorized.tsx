import { FC } from "react";
import { useIsAuthorized } from "../auth-context/hooks";

type Props = {
  permissions?: string[];
  oneOfPermissions?: string[];
};

export const Authorized: FC<Props> = ({
  children,
  permissions = undefined,
  oneOfPermissions = undefined,
}) => {
  const isAuthorized = useIsAuthorized();

  return isAuthorized({ permissions, oneOfPermissions }) ? (
    <>{children}</>
  ) : (
    <></>
  );
};
