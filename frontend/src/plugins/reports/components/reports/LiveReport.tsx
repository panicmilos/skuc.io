import moment from "moment";
import { FC, useEffect, useState } from 'react';
import { EventsSocketIoClient } from "../../clients";
import { MultiLineReport } from "./MultilineReport";

const BUFFER_LENGTH = 20;

type Props = {
  client: EventsSocketIoClient
  paused: boolean,
  paramName: string
}

export const LiveReport: FC<Props> = ({ client, paused, paramName, }) => {

  const [dataBuffer, setDataBuffer] = useState<any>([]);
  const [dataSnapshot, setDataSnapshot] = useState<any>([]);

  useEffect(() => {
    client && client.onEvent((event) => {
      console.log(event);

      event.paramName === paramName && addRecordToBuffer({ [paramName]: (event as any)['value'], createdAt: (event as any).createdAt })
    });
  }, [client]);

  useEffect(() => {
    setDataBuffer([]);
  }, [paramName]);

  useEffect(() => {
    paused && setDataSnapshot([...dataBuffer]);
  }, [paused]);

  const addRecordToBuffer = (record: any) => {
    if (dataBuffer.length === BUFFER_LENGTH)
    dataBuffer.shift();
    dataBuffer.push(record);
    setDataBuffer([...dataBuffer]);
  }

  const data = paused ? dataSnapshot : dataBuffer;

  const nameKey = "createdAt";
  const dataKeys = [paramName];

  const dataForChart = (data && data.length) ? data.map((r: any) => {
    const record = { ...r };
    record[nameKey] = moment(record[nameKey]).format('HH:mm:ss');
    return record;
  }) : [];
  
  return (
    <MultiLineReport
      data={dataForChart}
      nameKey={nameKey}
      dataKeys={dataKeys}
      dataUnits={{}}
    />
  );
  
}