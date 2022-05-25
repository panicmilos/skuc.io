import { AxiosError } from "axios";
import { FC, useContext, useState } from "react";
import { useMutation, useQueryClient } from "react-query";
import {
  Form,
  NotificationService,
  Button,
  FormTextInput,
  extractErrorMessage
} from '../../imports';
import { CreateGroup, Group, UpdateGroup } from "../../models/Group";
import { useGroupsService } from "../../services/GroupsService";
import { GroupsContext } from "./Groups";
import { ADD_GROUP, UPDATE_GROUP } from "./GroupsActions";
import * as Yup from 'yup';
import { createUseStyles } from "react-jss";
import { ALPHANUMERIC_REGEX } from "../../../../core/constants";

type Props = {
  existingGroup?: Group;
  isEdit?: boolean;
}

const useStyles = createUseStyles({
  submitButton: {
    marginTop: '0.5em'
  }
});

export const AddUpdateGroupForm: FC<Props> = ({ existingGroup = undefined, isEdit = false }) => {

  const queryClient = useQueryClient();
  const [groupsService] = useGroupsService();
  const [notificationService] = useState(new NotificationService());

  const { setResult } = useContext(GroupsContext);

  const schema = Yup.object().shape({
    name: Yup.string().required(() => ({
      name: "Group name must be provided."
    })) 
    .matches(ALPHANUMERIC_REGEX, () => ({name: "Must be a valid name."}))
  })

  const addGroupMutation = useMutation((newGroup: CreateGroup) => groupsService.add(newGroup), {
    onSuccess: () => {
      queryClient.invalidateQueries(groupsService.ID);
      notificationService.success('You have successfully created new group.');

      setResult({ status: 'OK', action: ADD_GROUP });
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));
    }
  });
  const addGroup = (newGroup: CreateGroup) => addGroupMutation.mutate(newGroup);

  const updateGroupMutation = useMutation((updateGroup: UpdateGroup) => groupsService.update(existingGroup?.id ?? '', updateGroup), {
    onSuccess: () => {
      queryClient.invalidateQueries(groupsService.ID);
      notificationService.success('You have successfully updated group.');

      setResult({ status: 'OK', action: UPDATE_GROUP });
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));
    }
  });
  const updateGroup = (updateGroup: UpdateGroup) => updateGroupMutation.mutate(updateGroup);
  
  const classes = useStyles();

  return (
    <>
      <Form
        initialValue={ existingGroup || {} }
        schema={schema}
        onSubmit={!isEdit ? addGroup : updateGroup}
      >
        <FormTextInput label="Group Name" name="name" />

        <div className={classes.submitButton} >
          <Button type="submit">Submit</Button>
        </div>
        
      </Form>
    </>
  )
}