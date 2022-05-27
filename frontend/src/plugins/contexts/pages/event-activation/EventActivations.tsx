import { createContext, FC, useEffect, useState } from "react";
import { useQuery } from "react-query";
import { useParams } from "react-router-dom";
import { Card, Result } from "../../imports"
import { useEventActivationsService } from "../../services";
import { EventActivationsTable } from "./EventActivationsTable";

type EventActivationsContextValue = {
  result?: Result,
  setResult: (r: Result) => any
};

export const EventActivationsContext = createContext<EventActivationsContextValue>({
  result: undefined,
  setResult: () => {}
});

type Props = {
  type: 'activators' | 'deactivators';
}

export const EventActivations: FC<Props> = ({ type }) => {

  const params = useParams();
  const contextId = params['contextId'] ?? '';

  const [eventActivationsService] = useEventActivationsService(contextId, type);

  const [result, setResult] = useState<Result|undefined>(undefined);

  const { data: eventActivations } = useQuery([result, eventActivationsService], () => eventActivationsService.fetchAll(), { enabled: !result });
  
  useEffect(() => {
    if (!result) return;
    setResult(undefined);
  }, [result]);

  return (
    <EventActivationsContext.Provider value={{ result, setResult }}>
      <Card title={type === 'activators' ? "Event Activators" : "Event Deactivators"}>
        <EventActivationsTable
          type={type}
          contextId={contextId ?? ''}
          eventActivations={eventActivations ?? []}
        />
      </Card>
    </EventActivationsContext.Provider>
  )
}