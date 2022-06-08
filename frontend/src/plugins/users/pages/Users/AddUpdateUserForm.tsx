import { FC, useContext, useState } from "react";
import { createUseStyles } from "react-jss";
import { useMutation, useQuery, useQueryClient } from "react-query";
import { ALPHANUMERIC_REGEX, Button, EMAIL_REGEX, extractErrorMessage, Form, FormSelectOptionInput, FormTextInput, LOWER_CASE_REGEX, NotificationService, NUMERIC_REGEX, PHONE_NUMBER_REGEX, SPECIAL_CHARACTERS_REGEX } from "../../imports";
import { CreateUserRequest, UpdateUserRequest, User } from "../../models"
import { useUsersService } from "../../services";
import { UsersContext } from "./Users";
import * as Yup from 'yup';
import { ADD_USER, UPDATE_USER } from "./UsersActions";
import { AxiosError } from "axios";
import { useGroupsService } from "../../../groups/services";

type Props = {
  groupId: string,
  existingUser?: User,
  isEdit?: boolean
}

const useStyles = createUseStyles({
  submitButton: {
    marginTop: '0.5em'
  }
});

export const AddUpdateUserForm: FC<Props> = ({ groupId, existingUser = undefined, isEdit = false }) => {

  const queryClient = useQueryClient();
  const [groupService] = useGroupsService();
  const [userService] = useUsersService(groupId);
  const [notificationService] = useState(new NotificationService());

  const { setResult } = useContext(UsersContext);

  const { data: group } = useQuery([groupId], () => groupService.fetch(groupId));

  const schema = Yup.object().shape({
    email: Yup.string()
      .required(() => ({ email: "Email must be provided." }))
      .matches(EMAIL_REGEX, () => ({ email: "Must be a valid email." })),
    password: Yup.string()
      .required(() => ({ password: "Password must be provided." }))
      .matches(LOWER_CASE_REGEX, () => ({ password: "Password must contain lower letters." }))
      .matches(NUMERIC_REGEX, () => ({ password: "Password must contain numbers." }))
      .matches(SPECIAL_CHARACTERS_REGEX, () => ({ password: "Password must contain special characters." })),
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

  const addUserMutation = useMutation((newUser: CreateUserRequest) => userService.add(newUser), {
    onSuccess: () => {
      queryClient.invalidateQueries(userService.ID);
      notificationService.success('You have successfully created new user.');

      setResult({ status: 'OK', action: ADD_USER });
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));
    }
  });
  const addUser = (newUser: CreateUserRequest) => addUserMutation.mutate(newUser);

  const updateUserMutation = useMutation((updateUser: UpdateUserRequest) => userService.update(existingUser?.id ?? '', updateUser), {
    onSuccess: () => {
      queryClient.invalidateQueries(userService.ID);
      notificationService.success('You have successfully updated a user.');

      setResult({ status: 'OK', action: UPDATE_USER });
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));
    }
  });
  const updateUser = (updateUser: UpdateUserRequest) => updateUserMutation.mutate(updateUser);

  const classes = useStyles();

  return (
    <>
      <Form
        initialValue={ existingUser || {} }
        schema={schema}
        onSubmit={!isEdit ? addUser : updateUser}
      >
        <FormTextInput label="Email" name="email" disabled={isEdit} />
        {
          !isEdit && <FormTextInput label="Password" name="password" type="password" disabled={isEdit} />
        }
        <FormTextInput label="Full name" name="fullName"/>
        <FormTextInput label="Address" name="address"/>
        <FormTextInput label="Phone number" name="phoneNumber"/>

        <FormSelectOptionInput
          label='Role'
          name='role'
          hasDefaultOption={false}
          options={
            [group?.name === 'Administrators' ? { label: 'Admin', value: 'Admin' } : { label: 'User', value: 'User' }]
          }
        />

        <div className={classes.submitButton} >
          <Button type="submit">Submit</Button>
        </div>
        
      </Form>
    </>
  )

} 