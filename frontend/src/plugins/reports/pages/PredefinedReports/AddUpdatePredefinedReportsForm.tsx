import { AxiosError } from "axios";
import { FC, useContext, useEffect, useState } from "react";
import { createUseStyles } from "react-jss";
import { useMutation, useQueryClient } from "react-query";
import { algorithms, reportTypes, resolutions } from "../../constants";
import { useLocationsService, Location, Form, Container, FormSelectOptionInput, SelectOptionInput, Button, TextInput, FormTextInput, NotificationService, extractErrorMessage } from "../../imports";
import { CreatePredefinedReport, PredefinedReport, UpdatePredefinedReport } from "../../models";
import { usePredefinedReportsServiceForModifying } from "../../services";
import { mapParamFiltersForComponents, mapParamFiltersForRequest } from "../../utils";
import { PredefinedReportsContext } from "./PredefinedReports";
import { ADD_PREDEFINED_REPORT, UPDATE_PREDEFINED_REPORT } from "./PredefinedReportsActions";

type Props = {
  groupId: string,
  existingPredefinedReport?: PredefinedReport,
  isEdit: boolean
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


export const AddUpdatePredefinedReportsForm: FC<Props> = ({ groupId, existingPredefinedReport = undefined, isEdit = false }) => {

  const [locationId, setLocationId] = useState('');
  const [locationOptions, setLocationOptions] = useState<any>([]);
  const [reportType, setReportType] = useState('');
  const [paramFilters, setParamFilters] = useState<any>([]);

  const { setResult } = useContext(PredefinedReportsContext);

  const queryClient = useQueryClient();
  const [locationsService] = useLocationsService(groupId);
  const [predefinedReportsService] = usePredefinedReportsServiceForModifying(groupId, locationId);
  const [notificationService] = useState(new NotificationService());

  useEffect(() => {
    setLocationId(existingPredefinedReport?.locationId ?? '');
    setParamFilters(mapParamFiltersForComponents(existingPredefinedReport?.paramFilters ?? []));
  }, [existingPredefinedReport]);

  useEffect(() => {
    locationsService.fetchAll().then(locations => {
      const options = locations?.map((location: Location) => ({
        label: location?.name,
        value: location?.id
      })) ?? [];

      setLocationOptions(options);
    });
  }, []);

  
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
  

  const addPredefinedReportMutation = useMutation((newPredefinedReport: CreatePredefinedReport) => predefinedReportsService.add(newPredefinedReport), {
    onSuccess: () => {
      queryClient.invalidateQueries(predefinedReportsService.ID);
      notificationService.success('You have successfully created new predefined report.');

      setResult({ status: 'OK', action: ADD_PREDEFINED_REPORT });
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));
    }
  });
  const addPredefinedReport = (newPredefinedReport: CreatePredefinedReport) => addPredefinedReportMutation.mutate(newPredefinedReport);

  const updatePredefinedReportMutation = useMutation((updatePredefinedReport: UpdatePredefinedReport) => predefinedReportsService.update(existingPredefinedReport?.id ?? '', updatePredefinedReport), {
    onSuccess: () => {
      queryClient.invalidateQueries(predefinedReportsService.ID);
      notificationService.success('You have successfully updated new predefined report.');

      setResult({ status: 'OK', action: UPDATE_PREDEFINED_REPORT });
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));
    }
  });
  const updatePredefinedReport = (updatePredefinedReport: UpdatePredefinedReport) => updatePredefinedReportMutation.mutate(updatePredefinedReport);


  const classes = useStyles();

  return (
    <>
      <Form
        schema={undefined}
        initialValue={existingPredefinedReport || {}}
        onSubmit={values => {
          const predefinedReports = {
            name: values.name,
            type: reportType,
            paramFilters: mapParamFiltersForRequest(paramFilters),
            resolution: values.resolution ? parseInt(values.resolution) : 0,
          }

          isEdit ? updatePredefinedReport(predefinedReports) : addPredefinedReport(predefinedReports);
        }}
      >
        <Container>

          <SelectOptionInput
            label='Location'
            value={locationId}
            onChange={setLocationId}
            options={locationOptions}
            disabled={isEdit}
          />

          <SelectOptionInput
            label='Report type'
            value={reportType}
            onChange={setReportType}
            options={reportTypes}
            disabled={isEdit}
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
          <FormTextInput label="Name" name='name' />
        </ Container>

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
