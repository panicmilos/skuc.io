import { FC, useEffect, useState } from "react";
import { createUseStyles } from "react-jss";
import { useLocationsService, Location, Form, FormSelectOptionInput, Container, Button, SelectOptionInput, FormDateInput, TextInput } from "../../imports";
import moment from "moment";
import { algorithms, reportTypes, resolutions } from "../../constants";
import { mapParamFiltersForRequest, mapPeriodForRequest } from "../../utils";

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
            type: reportType,
            locationId: values.locationId,
            period: mapPeriodForRequest(values),
            paramFilters: mapParamFiltersForRequest(paramFilters),
            ...(values.resolution ? { resolution: parseInt(values.resolution) } : {}),
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
                  label='Algorithms'
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
