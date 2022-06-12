import moment from "moment";

export function mapRecordsToChartData(records: any[]) {
  const nameKey = "CreatedAt";
  const keys = !!records && records.length ? Object.keys(records[0]).filter(key => key !== nameKey) : [];

  const data = (records && records.length) ? records.map(r => {
    const record = {...r};
    record[nameKey] = moment(record[nameKey]).format('DD.MM.YYYY. HH:mm');
    keys.forEach(k => {
      if (record[k] === null)
        record[k] = 0;
      else
        record[k] = parseFloat(record[k]);
    });
    return {...record};
  }) : [];

  return {
    nameKey,
    keys,
    data
  }
}