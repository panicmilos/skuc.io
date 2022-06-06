import { createContext, FC, useEffect, useState } from "react";
import { useQuery } from "react-query";
import { useParams } from "react-router-dom";
import { Card, Result } from "../../imports";
import { useTemplateInstanceService } from "../../services";
import { TemplateInstancesTable } from "./TemplateInstancesTable";

type TemplateInstancesContextValue = {
  result?: Result,
  setResult: (r: Result) => any
}

export const TemplateInstancesContext = createContext<TemplateInstancesContextValue>({
  result: undefined,
  setResult: () => {}
});

export const TemplateInstances: FC = () => {

  const params = useParams();
  const groupId = params['groupId'] || '';
  const templateId = params['templateId'] || '';

  const [templateInstancesService] = useTemplateInstanceService(groupId, templateId);

  const [result, setResult] = useState<Result|undefined>(undefined);

  const { data: templateInstances } = useQuery([result, templateInstancesService], () => templateInstancesService.fetchAll(), { enabled: !result });

  useEffect(() => {
    if (!result) return;
    setResult(undefined);
  }, [result]);
  
  return (
    <TemplateInstancesContext.Provider value={{ result, setResult }}>
      <Card title="Template Instances">

        <TemplateInstancesTable
          groupId={groupId ?? ''}
          templateId={templateId ?? ''}
          templateInstances={templateInstances ?? []}
        />

      </Card>
    </TemplateInstancesContext.Provider>
  )

}