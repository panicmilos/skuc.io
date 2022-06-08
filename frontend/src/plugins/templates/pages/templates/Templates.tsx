import { createContext, FC, useContext, useEffect, useState } from "react"
import { useQuery } from "react-query";
import { useParams } from "react-router-dom";
import { AuthContext, Card, Result } from "../../imports"
import { useTemplatesService } from "../../services";
import { TemplatesTable } from "./TemplatesTable";

type TemplatesContextValue = {
  result?: Result,
  setResult: (r: Result) => any
}

export const TemplatesContext = createContext<TemplatesContextValue>({
  result: undefined,
  setResult: () => {}
});

export const Templates: FC = () => {

  const params = useParams();
  const { user } = useContext(AuthContext);
  const groupId = params['groupId'] || user?.groupId || '';

  const [templatesService] = useTemplatesService(groupId);

  const [result, setResult] = useState<Result|undefined>(undefined);

  const { data: templates } = useQuery([result, templatesService], () => templatesService.fetchAll(), { enabled: !result });

  useEffect(() => {
    if (!result) return;
    setResult(undefined);
  }, [result]);
  
  return (
    <TemplatesContext.Provider value={{ result, setResult }}>
      <Card title="Templates">

        <TemplatesTable
          groupId={groupId ?? ''}
          templates={templates ?? []}
        />

      </Card>
    </TemplatesContext.Provider>
  )

}