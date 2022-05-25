import { createContext, FC, useContext, useEffect, useState } from "react";
import { useQuery } from "react-query";
import { useParams } from "react-router-dom";
import {
  Card,
  Result,
  AuthContext,
} from '../../imports';
import { useUsersService } from "../../services/UsersService";
import { UsersTable } from "./UsersTable";

type UsersContextValue = {
  result?: Result,
  setResult: (r: Result) => any,
}

export const UsersContext = createContext<UsersContextValue>({
  result: undefined,
  setResult: () => {}
});

export const Users: FC = () => {

  const params = useParams();
  const { user } = useContext(AuthContext);
  const groupId = params['groupId'] || user?.groupId || '';
  
  const [usersService] = useUsersService(groupId);

  const [result, setResult] = useState<Result|undefined>(undefined);

  const { data: users } = useQuery([result, usersService], () => usersService.fetchAll(), { enabled: !result });

  useEffect(() => {
    if (!result) return;
    setResult(undefined);
  }, [result]);

  return (
    <UsersContext.Provider value={{ result, setResult }}>
      <Card title="Users">

        <UsersTable
          groupId={groupId ?? ''}
          users={users ?? []}
        />

      </Card>
    </UsersContext.Provider>
  )
}