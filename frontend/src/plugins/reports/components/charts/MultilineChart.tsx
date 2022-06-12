import React, { FC, memo } from 'react';
import { LineChart, Line, CartesianGrid, XAxis, YAxis, Tooltip, ResponsiveContainer, Legend, Brush } from 'recharts';

type Props = {
  dataList: any,
  nameKey: string,
  dataKeys: string[],
  dataUnits: any,
  lineColors: any,
  hasLegend: boolean,
  hasYAxis: boolean,
  hasXAxis: boolean,
  hasBrush: boolean,
  hasGrid: boolean,
  hasTooltip: boolean,
  customTooltip: any,
}

export const MultiLineChart: FC<Props> = memo((
  {
    dataList,
    nameKey,
    dataKeys,
    dataUnits,
    lineColors,
    hasLegend,
    hasYAxis = true,
    hasXAxis = true,
    hasBrush,
    hasGrid = true,
    hasTooltip = true,
    customTooltip,
  }) => {

  const lineColorByNum = (num: number) => (lineColors && lineColors[num]) || "#8884d8";
  return (
    <ResponsiveContainer
      maxHeight={500}
      minHeight={300}
    >
      <LineChart
        data={dataList}
        margin={{ top: 30, right: 30, left: 20, bottom: 5 }}
      >
        {hasGrid && <CartesianGrid strokeDasharray="5 5" />}
        {hasXAxis && <XAxis
          dataKey={nameKey}
        />}
        {hasYAxis && dataKeys?.map((key, i) =>
          <YAxis
            key={i}
            orientation={i % 2 === 0 ? 'left' : "right"}
            type="number"
            yAxisId={i}
            stroke={lineColorByNum(i)}
          />
        )}
        {hasTooltip && <Tooltip content={customTooltip} />}
        {hasLegend && <Legend />}
        {hasBrush && <Brush dataKey={nameKey} height={30} stroke="#8884d8" />}
        {dataKeys?.map((key, i) => <Line key={i} yAxisId={i} type="natural" dataKey={key} unit={dataUnits[i]} stroke={lineColorByNum(i)} animationDuration={500} />)}
      </LineChart>
    </ResponsiveContainer>
  );
});