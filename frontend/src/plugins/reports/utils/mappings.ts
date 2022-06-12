import moment from "moment";

export function mapParamFiltersForRequest(paramFilters: any) {
  return paramFilters?.map((paramFilter: any) => {
    return {
      ...paramFilter,
      valueFilters: (paramFilter.filters && paramFilter.filters.split(", ").map((filter: any) => {
        const tokens = filter.split(/\s+/)
        return {
          algorithm: tokens[0],
          comparator: tokens[1],
          value: parseFloat(tokens[2])
        }
      })) || []
    };
  })
}


export function mapParamFiltersForComponents(paramFilters: any) {
  console.log(paramFilters);

  return paramFilters.map((paramFilter: any) => ({
    paramName: paramFilter.paramName,
    algorithm: paramFilter.algorithm,
    filters: paramFilter.valueFilters.map(({algorithm, comparator, value}: any) => `${algorithm} ${comparator} ${value}`).join(", ")
  }));
}

export function mapPeriodForRequest({ from, to }: any) {
  return {
    ...(from ? {from: moment(from).format() } : {}),
    ...(to ? {to: moment(to).endOf('day').format() } : {}),
  }
}