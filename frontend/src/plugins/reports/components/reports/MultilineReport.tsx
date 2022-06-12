import React, { FC } from 'react';
import { MultiLineChart } from '../charts';


type Props = {
  data: any,
  nameKey: string,
  dataKeys: string[],
  dataUnits: any,
}

const COLORS = ['#0088FE', '#00C49F', '#FF8042', '#FFBB28'];

export const MultiLineReport: FC<Props> = ({ data, nameKey, dataKeys, dataUnits }) => {

  const displayState = {
    hasLegend: true,
    hasYAxis: true,
    hasXAxis: true,
    hasBrush: false,
    hasGrid: true,
    hasTooltip: true,
    customTooltip: null,
  };

  return (
    <MultiLineChart
      dataList={data}
      nameKey={nameKey}
      dataKeys={dataKeys}
      dataUnits={dataUnits}
      lineColors={COLORS}
      {...displayState}
    />
  );
}