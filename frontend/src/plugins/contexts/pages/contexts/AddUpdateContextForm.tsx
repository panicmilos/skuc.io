import { FC, useContext, useState } from "react";
import { createUseStyles } from "react-jss";
import { useMutation, useQueryClient } from "react-query";
import { ALPHANUMERIC_REGEX, Button, extractErrorMessage, Form, FormTextInput, NotificationService } from "../../imports";
import { Context, CreateContextReqest, StatusConfiguration, ThresholdConfiguration, UpdateContextRequest } from "../../models"
import { useContextsService } from "../../services";
import { ContextsContext } from "./Contexts";
import * as Yup from 'yup';
import { ADD_CONTEXT, UPDATE_CONTEXT } from "./ContextActions";
import { AxiosError } from "axios";
import { ThresholdConfigurationForm } from "./ThresholdConfigurationForm";
import { StatusConfigurationForm } from "./StatusConfigurationForm";

type Props = {
  groupId: string,
  existingContext?: Context,
  isEdit?: boolean
};

const useStyles = createUseStyles({
  submitButton: {
    marginTop: '0.5em'
  }
});

export const AddUpdateContextForm: FC<Props> = ({ groupId, existingContext = undefined, isEdit = false }) => {

  const queryClient = useQueryClient();
  const [contextsService] = useContextsService(groupId);
  const [notificationService] = useState(new NotificationService());

  const [thresholdConfiguration, setThresholdConfiguration] = useState<ThresholdConfiguration>(existingContext?.configuration?.thresholdConfiguration ?? {});
  const [statusConfiguration, setStatusConfiguration] = useState<StatusConfiguration>(existingContext?.configuration?.statusConfiguration ?? {});

  const { setResult } = useContext(ContextsContext);

  const schema = Yup.object().shape({
    name: Yup.string()
      .required(() => ({ name: "Name must be provided." })) 
      .matches(ALPHANUMERIC_REGEX, () => ({name: "Must be a valid name."}))
  });

  const addContextMutation = useMutation((newContext: CreateContextReqest) => contextsService.add(newContext), {
    onSuccess: () => {
      queryClient.invalidateQueries(contextsService.ID);
      notificationService.success('You have successfully created new context.');

      setResult({ status: 'OK', action: ADD_CONTEXT });
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));
    }
  });
  const addContext = (newContext: CreateContextReqest) => addContextMutation.mutate(newContext);

  const updateContextMutation = useMutation((updateContext: UpdateContextRequest) => contextsService.update(existingContext?.id ?? '', updateContext), {
    onSuccess: () => {
      queryClient.invalidateQueries(contextsService.ID);
      notificationService.success('You have successfully updated a context.');

      setResult({ status: 'OK', action: UPDATE_CONTEXT });
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));
    }
  });
  const updateContext = (updateContext: UpdateContextRequest) => updateContextMutation.mutate(updateContext);

  const classes = useStyles();

  return (
    <>
      <Form
          initialValue={ existingContext || {} }
          schema={schema}
          onSubmit={({ name }) => {
            if (!Object.keys(thresholdConfiguration).length && !Object.keys(statusConfiguration).length) return;
            
            !isEdit ?
              addContext({ name, configuration: { thresholdConfiguration, statusConfiguration }}) :
              updateContext({ configuration: { thresholdConfiguration, statusConfiguration }});
          } }
        >
          <FormTextInput label="Name" name="name" disabled={isEdit} />

          <ThresholdConfigurationForm thresholdConfiguration={existingContext?.configuration?.thresholdConfiguration ?? {}} onChange={setThresholdConfiguration} />
          <StatusConfigurationForm statusConfiguration={existingContext?.configuration?.statusConfiguration ?? {}} onChange={setStatusConfiguration} />

          <div className={classes.submitButton} >
            <Button type="submit">Submit</Button>
          </div>
        
      </Form>
    </>
  );
}