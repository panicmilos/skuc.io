import { FC, useContext, useEffect, useState } from "react";
import { InfoSocketIoClient } from "./clients/InfoSocketIoClient";
import { SocketIoClient } from "./clients/SocketIoClient";
import { AuthContext, getGroupIdFromToken, NotificationService } from "./imports";

export const SocketsContextProvider: FC = ({ children }) => {
  const { isAuthenticated, user } = useContext(AuthContext);

  const [socketClient, setSocketClient] = useState<SocketIoClient|undefined>();
  const [notificationService] = useState(new NotificationService());

  useEffect(() => {
    if(isAuthenticated && user?.role === 'User') {
      const groupId = getGroupIdFromToken();

      const infoSocketClient = new InfoSocketIoClient(groupId);
      infoSocketClient.onInfo((info: any) => {
        notificationService.success(info.message);
      });

      setSocketClient(infoSocketClient);

      return () => infoSocketClient?.close();
    }
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isAuthenticated]);

  return (
    <>
      {children}
    </>
  );
}