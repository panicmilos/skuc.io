import { createContext, FC, useEffect, useState } from "react";
import { useQuery } from "react-query";
import { useParams } from "react-router-dom";
import { Card, Result } from "../../imports"
import { useDevicesService } from "../../services";
import { DevicesTable } from "./DevicesTable";

type DevicesContextValue = {
  result?: Result,
  setResult: (r: Result) => any,
};

export const DevicesContext = createContext<DevicesContextValue>({
  result: undefined,
  setResult: () => {}
});

export const Devices: FC = () => {

  const params = useParams();
  const groupId = params['groupId'] || '';
  const locationId = params['locationId'] || '';

  const [devicesService] = useDevicesService(groupId, locationId);

  const [result, setResult] = useState<Result|undefined>(undefined);

  const { data: devices } = useQuery([result, devicesService], () => devicesService.fetchAll(), { enabled: !result });

  useEffect(() => {
    if (!result) return;
    setResult(undefined);
  }, [result]);

  return (
    <DevicesContext.Provider value={{ result, setResult }}>
      <Card title="Devices">

        <DevicesTable
          groupId={groupId}
          locationId={locationId}
          devices={devices ?? []}
        />

      </Card>
    </DevicesContext.Provider>
  )
}