import { createContext, FC, useEffect, useState } from "react";
import { useQuery } from "react-query";
import {
  Card,
  Result,
} from '../../imports';
import { useGroupsService } from "../../services/GroupsService";
import { GroupsTable } from "./GroupsTable";


type GroupsContextValue = {
  result?: Result,
  setResult: (r: Result) => any,
}

export const GroupsContext = createContext<GroupsContextValue> ({
  result: undefined,
  setResult: () => {}
});

export const Groups: FC = () => {

  const [groupsService] = useGroupsService();

  const [result, setResult] = useState<Result|undefined>(undefined);

  const { data: groups } = useQuery([result], () => groupsService.fetchAll(), { enabled: !result });

  useEffect(() => {
    if (!result) return;
    setResult(undefined);
  }, [result]);
  
  return (
    <GroupsContext.Provider value={{ result, setResult }}>

      <Card title="Groups">

        <GroupsTable
          groups={groups ?? []}
        />
      </Card>
    </GroupsContext.Provider>
  )
}