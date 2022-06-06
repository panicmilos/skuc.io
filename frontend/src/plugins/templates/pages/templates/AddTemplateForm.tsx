import { FC, useContext, useState } from "react";
import { createUseStyles } from "react-jss";
import { useMutation, useQueryClient } from "react-query";
import { ALPHANUMERIC_REGEX, Button, extractErrorMessage, Form, FormTextAreaInput, FormTextInput, NotificationService } from "../../imports";
import { useTemplatesService } from "../../services";
import { TemplatesContext } from "./Templates";
import * as Yup from 'yup';
import { CreateTemplate } from "../../models";
import { ADD_TEMPLATE } from "./TemplateActions";
import { AxiosError } from "axios";

type Props = {
  groupId: string
}

const useStyles = createUseStyles({
  submitButton: {
    marginTop: '0.5em'
  }
});

export const AddTemplateForm: FC<Props> = ({ groupId }) => {
  
  const queryClient = useQueryClient();
  const [templatesService] = useTemplatesService(groupId);
  const [notificationService] = useState(new NotificationService());

  const { setResult } = useContext(TemplatesContext);

  const schema = Yup.object().shape({
    name: Yup.string()
      .required(() => ({ name: "Name must be provided." })) 
      .matches(ALPHANUMERIC_REGEX, () => ({name: "Must be a valid name."})),
    parameters: Yup.string()
      .required(() => ({ parameters: "Parameters must be provided." })) 
      .test('parameters-test', () => ({ parameters: 'Must be a valid parameters.'}), parameters => parameters?.split(',').reduce((acc: boolean, param: string) => acc && !!param.trim(), true) ?? false ),
    when: Yup.string()
      .required(() => ({ when: "When must be provided." })),
    then: Yup.string()
      .required(() => ({ then: "Then must be provided." }))
  });

  const addTemplateMutation = useMutation((newTemplate: CreateTemplate) => templatesService.add(newTemplate), {
    onSuccess: () => {
      queryClient.invalidateQueries(templatesService.ID);
      notificationService.success('You have successfully created new template.');

      setResult({ status: 'OK', action: ADD_TEMPLATE });
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));
    }
  });
  const addTemplate = (newTemplate: CreateTemplate) => addTemplateMutation.mutate(newTemplate);

  const classes = useStyles();

  return (
    <Form
      schema={schema}
      onSubmit={values => addTemplate({ ...values, parameters: values.parameters.split(',').map((param: string) => param.trim()) })}
    >
      <FormTextInput label="Name" name="name" />
      <FormTextInput label="Parameters" name="parameters" />

      <FormTextAreaInput label="When" name="when" rows={10} />
      <FormTextAreaInput label="Then" name="then" rows={10} />

      <div className={classes.submitButton} >
        <Button type="submit">Submit</Button>
      </div>
    
  </Form>
  );
}