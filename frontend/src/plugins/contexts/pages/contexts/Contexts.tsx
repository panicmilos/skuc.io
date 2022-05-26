import { createContext, FC, useContext, useEffect, useState } from "react";
import { useQuery } from "react-query";
import { useParams } from "react-router-dom";
import { AuthContext, Card, Result } from "../../imports"
import { useContextsService } from "../../services";
import { ContextsTable } from "./ContextsTable";

type ContextsContextValue = {
  result?: Result,
  setResult: (r: Result) => any
};

export const ContextsContext = createContext<ContextsContextValue>({
  result: undefined,
  setResult: () => {}
});

export const Contexts: FC = () => {

  const params = useParams();
  const { user } = useContext(AuthContext);
  const groupId = params['groupId'] || user?.groupId || '';

  const [contextsService] = useContextsService(groupId);

  const [result, setResult] = useState<Result|undefined>(undefined);

  const { data: contexts } = useQuery([result, contextsService], () => contextsService.fetchAll(), { enabled: !result });
  
  useEffect(() => {
    if (!result) return;
    setResult(undefined);
  }, [result]);

  return (
    <ContextsContext.Provider value={{ result, setResult }}>
      <Card title="Contexts">

        <ContextsTable
          groupId={groupId ?? ''}
          contexts={contexts ?? []}
        />

      </Card>
    </ContextsContext.Provider>
  )
}