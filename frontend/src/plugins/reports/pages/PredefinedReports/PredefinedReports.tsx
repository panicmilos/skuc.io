import { createContext, FC, useContext, useEffect, useState } from "react";
import { useQuery } from "react-query";
import { AuthContext, Card, Result } from "../../imports";
import { usePredefinedReportsServiceForFetching } from "../../services";
import { PredefinedReportsTable } from "./PredefinedReportsTable";

type PredefinedReportsContextValue = {
  result?: Result,
  setResult: (r: Result) => any,
};

export const PredefinedReportsContext = createContext<PredefinedReportsContextValue>({
  result: undefined,
  setResult: () => {}
});

export const PredefinedReports: FC = () => {

  const { user } = useContext(AuthContext);
  const groupId = user?.groupId || '';

  const [predefinedReportsService] = usePredefinedReportsServiceForFetching(groupId);

  const [result, setResult] = useState<Result|undefined>(undefined);

  const { data: predefinedReports } = useQuery([result, predefinedReportsService], () => predefinedReportsService.fetchAll(), { enabled: !result });

  useEffect(() => {
    if (!result) return;
    setResult(undefined);
  }, [result]);
  
  console.log(predefinedReports);

  return (
    <PredefinedReportsContext.Provider value={{ result, setResult }}>
      <Card title="Predefined Reports">

        <PredefinedReportsTable
          groupId={groupId}
          predefinedReports={predefinedReports ?? []}
        />

      </Card>
    </PredefinedReportsContext.Provider>
  )


}