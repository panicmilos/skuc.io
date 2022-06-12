import { FC, useEffect, useState } from "react";
import { createUseStyles } from "react-jss";
import { useLocationsService, Location, Form, FormSelectOptionInput, Container, Button, SelectOptionInput, FormDateInput, TextInput } from "../../imports";
import moment from "moment";

type Props = {
  groupId: string,
  submit: (r: any) => void
}

const useStyles = createUseStyles({
  addParamFilterButton: {
    marginTop: '0.1em',
    marginBottom: '1em'
  },
  submitButton: {
    marginTop: '0.5em'
  },
  removeParamFilterContainer: {
    display: 'flex',
    justifyContent: 'flex-end',
    marginTop: '15px',
    marginBottom: '-10px',
  },
  removeParamFilterButton: {
    padding: "0.25em 0.4em 0.15em 0.35em",
  }
});

const reportTypes = [
  { label: 'Normal', value: 'Normal' },
  { label: 'At some point in the time', value: 'AtSomePointInTheTime' },
  { label: 'Max period', value: 'MaxPeriod' },
]

const resolutions = [
  { label: '5 min', value: 5 },
  { label: '15 min', value: 15 },
  { label: '30 min', value: 30 },
  { label: '60 min', value: 60 },
  { label: '240 min', value: 240 },
  { label: '480 min', value: 480 },
  { label: '1440 min', value: 1440 }
]

const algorithms = [
  { label: 'Min', value: 'min' },
  { label: 'Max', value: 'max' },
  { label: 'Sum', value: 'sum' },
  { label: 'Average', value: 'average' },
  { label: 'Count', value: 'count' },
]

export const ReportFiltersForm: FC<Props> = ({ groupId, submit }) => {

  const [locationsService] = useLocationsService(groupId);

  const [locationOptions, setLocationOptions] = useState<any>([]);
  const [reportType, setReportType] = useState('');
  const [paramFilters, setParamFilters] = useState<any>([]);

  useEffect(() => {
    locationsService.fetchAll().then(locations => {
      const options = locations?.map((location: Location) => ({
        label: location?.name,
        value: location?.id
      })) ?? [];

      setLocationOptions(options);
    });
  }, []);

  const classes = useStyles();

  const addNewParamFilter = () => {
    setParamFilters([
      ...paramFilters,
      {
        paramName: '',
        algorithm: '',
        valueFilters: []
      }
    ]);
  }

  const modifyParamFilter = (index: number, filter: any) => {
    setParamFilters([
      ...paramFilters.map((f: any, i: number) => i !== index ? f : filter)
    ]);
  }
  
  const removeParamFilter = (index: number) => {
    setParamFilters([
      ...paramFilters.filter((_: any, i: number) => i !== index)
    ]);
  }


  return (
    <>
      <Form
        schema={undefined}
        onSubmit={values => {
          const params = {
            ...(values.resolution ? { resolution: parseInt(values.resolution) } : {}),
            type: reportType,
            period: {
              ...(values.from ? {from: moment(values.from).format() } : {}),
              ...(values.to ? {to: moment(values.to).endOf('day').format() } : {}),
            },
            locationId: values.locationId,
            paramFilters: paramFilters?.map((paramFilter: any) => {
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

          submit(params);
        }}
      >
        <Container>

          <FormSelectOptionInput
            label='Location'
            name='locationId'
            options={locationOptions}
          />

          <SelectOptionInput
            label='Report type'
            value={reportType}
            onChange={setReportType}
            options={reportTypes}
          />
          
        </Container>

        {
          reportType !== 'MaxPeriod' &&
            <FormSelectOptionInput
              label='Resolution'
              name='resolution'
              options={resolutions}
            />
        }

        <Container>
          <FormDateInput
            label='From'
            name='from'
          />

          <FormDateInput
            label='To'
            name='to'
          />
        </Container>


        {
          paramFilters?.map((paramFilter: any, index: number) => {
            return <>
              <div className={classes.removeParamFilterContainer} >
                <Button className={classes.removeParamFilterButton} type="button" onClick={() => removeParamFilter(index)}>X</Button>
              </div>

              <Container>
                <TextInput label="Param Name" value={paramFilter.paramName} onChange={v => modifyParamFilter(index, { ...paramFilter, paramName: v }) } />
                <SelectOptionInput
                  label='Alogithms'
                  value={paramFilter.algorithm}
                  onChange={v => modifyParamFilter(index, { ...paramFilter, algorithm: v }) }
                  options={algorithms}
                />
              </Container>

              <Container>
                <TextInput label="Filters" value={paramFilter.filters} onChange={v => modifyParamFilter(index, { ...paramFilter, filters: v }) } />
              </ Container>
            </>
          }) ?? []
        }

        <div className={classes.addParamFilterButton} >
          <Button onClick={addNewParamFilter}>Add</Button>
        </div>

        <div className={classes.submitButton} >
          <Button type="submit">Submit</Button>
        </div>
        
      </Form>
    </>
  );

}
