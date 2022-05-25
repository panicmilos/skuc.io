import { createContext, FC, useContext, useEffect, useState } from "react"
import { useQuery } from "react-query";
import { useParams } from "react-router-dom";
import {
  Card,
  Result,
  AuthContext,
} from '../../imports';
import { useLocationsService } from "../../services";
import { LocationsTable } from "./LocationsTable";

type LocationsContextValue = {
  result?: Result,
  setResult: (r: Result) => any,
}

export const LocationsContext = createContext<LocationsContextValue>({
  result: undefined,
  setResult: () => {}
});

export const Locations: FC = () => {

  const params = useParams();
  const { user } = useContext(AuthContext);
  const groupId = params['groupId'] || user?.groupId || '';

  const [locationsService] = useLocationsService(groupId);

  const [result, setResult] = useState<Result|undefined>(undefined);

  const { data: locations } = useQuery([result, locationsService], () => locationsService.fetchAll(), { enabled: !result });

  useEffect(() => {
    if (!result) return;
    setResult(undefined);
  }, [result]);

  return (
    <LocationsContext.Provider value={{ result, setResult }}>
      <Card title="Locations">

        <LocationsTable
          groupId={groupId ?? ''}
          locations={locations ?? []}
        />

      </Card>
    </LocationsContext.Provider>
  )
}