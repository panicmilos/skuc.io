import { FC, useState } from "react";
import { User } from "../../models/User";
import * as Yup from 'yup';
import {
  Card,
  Form,
  FormTextInput,
  Button,
  NotificationService,
  extractErrorMessage
} from '../../imports';
import { ChangePasswordRequest } from "../../models/ChangePasswordRequest";
import { useMutation, useQueryClient } from "react-query";
import { AxiosError } from "axios";
import { createUseStyles } from "react-jss";
import { useUsersService } from "../../services";

const useStyles = createUseStyles({
  submitButton: {
    marginTop: '1em',
  }
});

type Props = {
  user?: User;
}

export const PasswordForm: FC<Props> = ({ user }) => {

  const schema = Yup.object().shape({
    currentPassword: Yup.string().required(() => ({currentPassword: "Current password must be provided."})),
    newPassword: Yup.string().required(() => ({newPassword: "New password must be provided."}))
                              .matches(/[A-Z]/, ()=> ({newPassword: "New Password must contain capital letters."}))
                              .matches(/[a-z]/, ()=> ({newPassword: "New Password must contain lower letters."}))
                              .matches(/[0-9]/, ()=> ({newPassword: "New Password must contain numbers."}))
                              .matches(/[^a-zA-Z0-9]/, ()=> ({newPassword: "New Password must contain special characters."})),

    confirmPassword: Yup.string().oneOf([Yup.ref('newPassword'), null], () => ({confirmPassword: "Passwords don't match!" })).required(() => ({confirmPassword: "Password must be provided."}))
  });

  const queryClient = useQueryClient();
  
  const [notificationService] = useState(new NotificationService());
  const [usersService,] = useUsersService(user?.groupId ?? '');

  const changePasswordMutation = useMutation((passwordRequest: ChangePasswordRequest) => usersService.changePassword(user?.id ?? '', passwordRequest), 
  {
    onSuccess: () => {
      queryClient.invalidateQueries(usersService.ID);

      notificationService.success('You have successfully changed your password.');
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));
    }
  });
  const classes = useStyles();

  const changePassword = (passwordRequest: ChangePasswordRequest) => changePasswordMutation.mutate(passwordRequest);

  return (
    <Card title="Change Password">
      <Form
          schema={schema}
          onSubmit={changePassword}
        >
          <FormTextInput type="password" label="Current Password" name="currentPassword" />
          <FormTextInput type="password" label="New Password" name="newPassword" />
          <FormTextInput type="password" label="Confirm password" name="confirmPassword" />

          <Button className={classes.submitButton} type="submit">Submit</Button>
        </Form>
    </Card>
  );
}