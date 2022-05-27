import { FC, useContext, useState } from "react";
import { createUseStyles } from "react-jss";
import { useMutation, useQueryClient } from "react-query";
import { ALPHANUMERIC_REGEX, Button, extractErrorMessage, Form, FormTextInput, NotificationService } from "../../imports";
import { CreateEventActivationReqest, EventActivation, UpdateEventActivationRequest } from "../../models"
import { useEventActivationsService } from "../../services";
import { EventActivationsContext } from "./EventActivations";
import * as Yup from 'yup';
import { ADD_EVENT_ACTIVATION, UPDATE_EVENT_ACTIVATION } from "./EventActivationActions";
import { AxiosError } from "axios";

type Props = {
  contextId: string;
  existingEventActivation?: EventActivation;
  isEdit?: boolean;
  type: 'activators' | 'deactivators';
};

const useStyles = createUseStyles({
  submitButton: {
    marginTop: '0.5em'
  }
});

export const AddUpdateEventActivationForm: FC<Props> = ({ type, contextId, existingEventActivation = undefined, isEdit = false }) => {

  const activationName = type === 'activators' ? 'activator' : 'deactivator';
  
  const queryClient = useQueryClient();
  const [eventActivationService] = useEventActivationsService(contextId, type);
  const [notificationService] = useState(new NotificationService());

  const { setResult } = useContext(EventActivationsContext);

  const schema = Yup.object().shape({
    eventType: Yup.string()
      .required(() => ({ eventType: "Name must be provided." })) 
      .matches(ALPHANUMERIC_REGEX, () => ({eventType: "Must be a valid eventType."}))
  });

  const addEventActivationMutation = useMutation((newEventActivation: CreateEventActivationReqest) => eventActivationService.add(newEventActivation), {
    onSuccess: () => {
      queryClient.invalidateQueries(eventActivationService.ID);
      notificationService.success(`You have successfully created a new ${activationName}.`);

      setResult({ status: 'OK', action: ADD_EVENT_ACTIVATION });
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));
    }
  });
  const addEventActivation = (newEventActivation: CreateEventActivationReqest) => addEventActivationMutation.mutate(newEventActivation);

  const updateEventActivationMutation = useMutation((updateEventActivation: UpdateEventActivationRequest) => eventActivationService.update(existingEventActivation?.id ?? '', updateEventActivation), {
    onSuccess: () => {
      queryClient.invalidateQueries(eventActivationService.ID);
      notificationService.success(`You have successfully updated a ${activationName}.`);

      setResult({ status: 'OK', action: UPDATE_EVENT_ACTIVATION });
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));
    }
  });
  const updateEventActivation = (updateEventActivation: UpdateEventActivationRequest) => updateEventActivationMutation.mutate(updateEventActivation);

  const classes = useStyles();

  return (
    <>
      <Form
          initialValue={ existingEventActivation || {} }
          schema={schema}
          onSubmit={({ eventType }) => {
            !isEdit ?
              addEventActivation({ eventType }) :
              updateEventActivation({ eventType });
          } }
        >
          <FormTextInput label="Event Type" name="eventType" />

          <div className={classes.submitButton} >
            <Button type="submit">Submit</Button>
          </div>
        
      </Form>
    </>
  );
}