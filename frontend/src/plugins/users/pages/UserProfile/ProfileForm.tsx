import { FC, useState } from "react";
import {
  ALPHANUMERIC_REGEX,
  Button,
  Card,
  extractErrorMessage,
  Form,
  FormTextInput,
  NotificationService,
  PHONE_NUMBER_REGEX,
  TextInput
} from '../../imports';
import { UpdateUserRequest, User } from "../../models/User";
import * as Yup from 'yup';
import { useMutation, useQueryClient } from "react-query";
import { useUsersService } from "../../services";
import { AxiosError } from "axios";
import { createUseStyles } from "react-jss";

type Props = {
  user?: User;
}

const useStyles = createUseStyles({
  submitButton: {
    marginTop: '0.5em'
  }
});

export const ProfileForm: FC<Props> = ({user}) => {

  const queryClient = useQueryClient();
  const [userService] = useUsersService(user?.groupId ?? '');
  const [notificationService] = useState(new NotificationService());

  const schema = Yup.object().shape({
    fullName: Yup.string()
      .required(() => ({ name: "Full name must be provided." })) 
      .matches(ALPHANUMERIC_REGEX, () => ({name: "Must be a valid full name."})),
    address: Yup.string()
      .required(() => ({ name: "Address must be provided." })) 
      .matches(ALPHANUMERIC_REGEX, () => ({name: "Must be a valid address."})),
    phoneNumber: Yup.string()
      .required(() => ({ name: "Phone number must be provided." })) 
      .matches(PHONE_NUMBER_REGEX, () => ({name: "Must be a valid phone number."}))
  });

  const updateUserMutation = useMutation((updateUser: UpdateUserRequest) => userService.update(user?.id ?? '', updateUser), {
    onSuccess: () => {
      queryClient.invalidateQueries(userService.ID);
      notificationService.success('You have successfully updated your profile.');
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));
    }
  });
  const updateUser = (updateUser: UpdateUserRequest) => updateUserMutation.mutate(updateUser);


  const classes = useStyles();

  return (
    <Card title="Profile">
      {
        !!user && <Form
            initialValue={user}
            schema={schema}
            onSubmit={updateUser}
          >
            <TextInput label="Email" value={user?.email ?? ''} disabled={true} onChange={() => {}}/>
            <FormTextInput label="Full Name" name="fullName" />
            <FormTextInput label="Phone number" name="phoneNumber" />
            <FormTextInput label="Address" name="address" />

            <Button className={classes.submitButton} type="submit">Submit</Button>

          </Form>
      }
    </Card>
  );
}