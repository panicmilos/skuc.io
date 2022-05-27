import { AxiosError } from "axios"
import { FC, useContext, useEffect, useState } from "react"
import { createUseStyles } from "react-jss";
import { useMutation, useQueryClient } from "react-query"
import {
  Button,
  ConfirmationModal,
  Table,
  TableBody,
  TableHead,
  TableRow,
  NotificationService,
  extractErrorMessage,
  Modal
} from '../../imports';
import { User } from "../../models/User"
import { useUsersService } from "../../services/UsersService"
import { AddUpdateUserForm } from "./AddUpdateUserForm";
import { UsersContext } from "./Users"
import { ADD_USER, DELETE_USER, UPDATE_USER } from "./UsersActions"

type Props = {
  groupId: string,
  users: User[],
}

const useStyles = createUseStyles({
  container: {
    '& button': {
      margin: '0em 0.5em 0.5em 0.5em'
    }
  },
  buttonAddUser: {
    display: 'flex',
    justifyContent: 'flex-end',
    marginTop: '-50px'
  }
});

export const UsersTable: FC<Props> = ({ groupId, users,}) => {

  const [isAddUpdateOpen, setIsAddUpdateOpen] = useState(false);
  const [isDeleteOpen, setDeleteOpen] = useState(false);
  const [selectedUser, setSelectedUser] = useState<User|undefined>(undefined);

  const queryClient = useQueryClient();

  const [usersService] = useUsersService(groupId);
  const [notificationService] = useState(new NotificationService());

  const { result, setResult } = useContext(UsersContext);

  const deleteMutation = useMutation(() => usersService.delete(selectedUser?.id ?? ''), {
    onSuccess: () => {
      queryClient.invalidateQueries(usersService.ID);
      notificationService.success('You have successfully deleted user.');

      setResult({ status: 'OK', action: DELETE_USER});
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));

      setResult({ status: 'ERROR', action: DELETE_USER});
    }
  });

  useEffect(() => {
    if (!result) return;

    if (result.status === 'OK' && [ADD_USER, UPDATE_USER].includes(result.action)) {
      setIsAddUpdateOpen(false);
    }

    if (result.action === DELETE_USER) {
      setDeleteOpen(false);
    }
  }, [result]); 

  const onYes = () => deleteMutation.mutate();

  const ActionsButtonGroup = ({user}: any) =>
    <>
      <Button onClick={() => {setSelectedUser(user); setIsAddUpdateOpen(true); }}>Update</Button>
      <Button onClick={() => {setSelectedUser(user); setDeleteOpen(true); }}>Delete</Button>
    </>
    
  const classes = useStyles();

  return (
    <div className={classes.container}>
      <Modal title={!selectedUser ? "Add user" : "Update user"} open={isAddUpdateOpen} onClose={() => setIsAddUpdateOpen(false)}>
        <AddUpdateUserForm groupId={groupId} existingUser={selectedUser} isEdit={!!selectedUser} />
      </Modal>

      <ConfirmationModal title="Delete user" open={isDeleteOpen} onClose={() => setDeleteOpen(false)} onYes={onYes}>
          <p>Are you sure you want to delete this user?</p>
      </ConfirmationModal>

      <div className={classes.buttonAddUser}>
        <Button onClick={() => { setSelectedUser(undefined); setIsAddUpdateOpen(true); }}>Add User</Button>
      </div> 

      <Table hasPagination={false}>
        <TableHead columns={['Email', 'Role', 'Full Name', 'Phone number', 'Address', 'Action']}/>
        <TableBody>
          {
            users?.map((user: User) => 
            <TableRow 
            key={user.id}
            cells={[
              user.email,
              user.role,
              user.fullName,
              user.phoneNumber,
              user.address,
              <ActionsButtonGroup user={user}/>
            ]}/>
            )
          }
        </TableBody>
      </Table>
    </div>
  )
}