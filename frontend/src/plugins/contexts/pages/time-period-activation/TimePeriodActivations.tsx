import { createContext, FC, useEffect, useState } from "react";
import { useQuery } from "react-query";
import { useParams } from "react-router-dom";
import { Card, Result } from "../../imports"
import { useTimePeriodActivationsService } from "../../services";
import { TimePeriodActivationsTable } from "./TimePeriodActivationsTable";

type TimePeriodActivationsContextValue = {
  result?: Result,
  setResult: (r: Result) => any
};

export const TimePeriodActivationsContext = createContext<TimePeriodActivationsContextValue>({
  result: undefined,
  setResult: () => {}
});

type Props = {
  type: 'activators' | 'deactivators';
}

export const TimePeriodActivations: FC<Props> = ({ type }) => {

  const params = useParams();
  const contextId = params['contextId'] ?? '';

  const [timePeriodActivationsService] = useTimePeriodActivationsService(contextId, type);

  const [result, setResult] = useState<Result|undefined>(undefined);

  const { data: timePeriodActivations } = useQuery([result, timePeriodActivationsService], () => timePeriodActivationsService.fetchAll(), { enabled: !result });
  
  useEffect(() => {
    if (!result) return;
    setResult(undefined);
  }, [result]);

  return (
    <TimePeriodActivationsContext.Provider value={{ result, setResult }}>
      <Card title={type === 'activators' ? "TimePeriod Activators" : "TimePeriod Deactivators"}>
        <TimePeriodActivationsTable
          type={type}
          contextId={contextId ?? ''}
          timePeriodActivations={timePeriodActivations ?? []}
        />
      </Card>
    </TimePeriodActivationsContext.Provider>
  )
}