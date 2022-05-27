import { FC, useContext, useState } from "react";
import { createUseStyles } from "react-jss";
import { useMutation, useQueryClient } from "react-query";
import { Button, CRON_REGEX, extractErrorMessage, Form, FormTextInput, NotificationService } from "../../imports";
import { CreateTimePeriodActivationReqest, TimePeriodActivation, UpdateTimePeriodActivationRequest } from "../../models"
import { useTimePeriodActivationsService } from "../../services";
import { TimePeriodActivationsContext } from "./TimePeriodActivations";
import * as Yup from 'yup';
import { ADD_TIME_PERIOD_ACTIVATION, UPDATE_TIME_PERIOD_ACTIVATION } from "./TimePeriodActivationActions";
import { AxiosError } from "axios";

type Props = {
  contextId: string;
  existingTimePeriodActivation?: TimePeriodActivation;
  isEdit?: boolean;
  type: 'activators' | 'deactivators';
};

const useStyles = createUseStyles({
  submitButton: {
    marginTop: '0.5em'
  }
});

export const AddUpdateTimePeriodActivationForm: FC<Props> = ({ type, contextId, existingTimePeriodActivation = undefined, isEdit = false }) => {

  const activationName = type === 'activators' ? 'activator' : 'deactivator';
  
  const queryClient = useQueryClient();
  const [timePeriodActivationService] = useTimePeriodActivationsService(contextId, type);
  const [notificationService] = useState(new NotificationService());

  const { setResult } = useContext(TimePeriodActivationsContext);

  const schema = Yup.object().shape({
    cronStart: Yup.string()
      .required(() => ({ cronStart: "Cron start must be provided." })) 
      .matches(CRON_REGEX, () => ({cronStart: "Must be a valid cron statement."})),
    cronEnd: Yup.string()
      .required(() => ({ cronEnd: "Cron end must be provided." })) 
      .matches(CRON_REGEX, () => ({cronEnd: "Must be a valid cron statement."}))
  });

  const addTimePeriodActivationMutation = useMutation((newTimePeriodActivation: CreateTimePeriodActivationReqest) => timePeriodActivationService.add(newTimePeriodActivation), {
    onSuccess: () => {
      queryClient.invalidateQueries(timePeriodActivationService.ID);
      notificationService.success(`You have successfully created a new ${activationName}.`);

      setResult({ status: 'OK', action: ADD_TIME_PERIOD_ACTIVATION });
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));
    }
  });
  const addTimePeriodActivation = (newTimePeriodActivation: CreateTimePeriodActivationReqest) => addTimePeriodActivationMutation.mutate(newTimePeriodActivation);

  const updateTimePeriodActivationMutation = useMutation((updateTimePeriodActivation: UpdateTimePeriodActivationRequest) => timePeriodActivationService.update(existingTimePeriodActivation?.id ?? '', updateTimePeriodActivation), {
    onSuccess: () => {
      queryClient.invalidateQueries(timePeriodActivationService.ID);
      notificationService.success(`You have successfully updated a ${activationName}.`);

      setResult({ status: 'OK', action: UPDATE_TIME_PERIOD_ACTIVATION });
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));
    }
  });
  const updateTimePeriodActivation = (updateTimePeriodActivation: UpdateTimePeriodActivationRequest) => updateTimePeriodActivationMutation.mutate(updateTimePeriodActivation);

  const classes = useStyles();

  return (
    <>
      <Form
          initialValue={ existingTimePeriodActivation || {} }
          schema={schema}
          onSubmit={({ cronStart, cronEnd }) => {
            !isEdit ?
              addTimePeriodActivation({ cronStart, cronEnd }) :
              updateTimePeriodActivation({ cronStart, cronEnd });
          } }
        >
          <FormTextInput label="CRON Start" name="cronStart" />
          <FormTextInput label="CRON End" name="cronEnd" />

          <div className={classes.submitButton} >
            <Button type="submit">Submit</Button>
          </div>
        
      </Form>
    </>
  );
}