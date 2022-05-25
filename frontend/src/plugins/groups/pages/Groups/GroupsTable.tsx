import { AxiosError } from "axios"
import { FC, useContext, useEffect, useState } from "react"
import { createUseStyles } from "react-jss"
import { useMutation, useQueryClient } from "react-query"
import { Link } from "react-router-dom"
import {
  Button,
  ConfirmationModal,
  Modal,
  Table,
  TableBody,
  TableHead,
  TableRow,
  NotificationService,
  extractErrorMessage,
} from '../../imports';
import { Group } from "../../models/Group"
import { useGroupsService } from "../../services/GroupsService"
import { AddUpdateGroupForm } from "./AddUpdateGroupForm"
import { GroupsContext } from "./Groups"
import { ADD_GROUP, DELETE_GROUP, UPDATE_GROUP } from "./GroupsActions"

type Props = {
  groups: Group[]
}

const useStyles = createUseStyles({
  container: {
    '& button': {
      margin: '0em 0.5em 0.5em 0.5em'
    }
  },
  buttonAddGroup: {
    display: 'flex',
    justifyContent: 'flex-end',
    marginTop: '-50px'
  }
});

export const GroupsTable: FC<Props> = ({ groups }) => {

  const [isAddUpdateOpen, setIsAddUpdateOpen] = useState(false);
  const [isDeleteOpen, setIsDeleteOpen] = useState(false);

  const [selectedGroup, setSelectedGroup] = useState<Group|undefined>(undefined);

  const queryClient = useQueryClient();
  const [groupsService] = useGroupsService();
  const [notificationService] = useState(new NotificationService());

  const { result, setResult } = useContext(GroupsContext);
  
  const deleteMutation = useMutation(() => groupsService.delete(selectedGroup?.id ?? ''), {
    onSuccess: () => {
      queryClient.invalidateQueries(groupsService.ID);
      notificationService.success('You have successfully deleted group.');

      setResult({ status: 'OK', action: DELETE_GROUP});
    },
    onError: (error: AxiosError) => {
      notificationService.error(extractErrorMessage(error.response?.data));

      setResult({ status: 'ERROR', action: DELETE_GROUP});
    }
  });

  const onYes = () => deleteMutation.mutate();

  const ActionsButtonGroup = ({ group }: any) =>
    <div>
      <Button onClick={() => {setSelectedGroup(group); setIsAddUpdateOpen(true); }}>Update</Button>
      <Button onClick={() => {setSelectedGroup(group); setIsDeleteOpen(true); }}>Delete</Button>
      <Link to={{ pathname: `/groups/${group?.id}/users` }}><Button>Users</Button></Link>
      <Link to={{ pathname: `/groups/${group?.id}/locations` }}><Button>Locations</Button></Link>
    </div>

  useEffect(() => {
    if (!result) return;

    if (result.status === 'OK' && [ADD_GROUP, UPDATE_GROUP].includes(result.action)) {
      setIsAddUpdateOpen(false);
    }

    if (result.action === DELETE_GROUP) {
      setIsDeleteOpen(false);
    }
  }, [result]); 

  const classes = useStyles();

  return(
    <div className={classes.container}>
      <Modal title={!selectedGroup ? "Add group" : "Update group"} open={isAddUpdateOpen} onClose={() => setIsAddUpdateOpen(false)}>
        <AddUpdateGroupForm existingGroup={selectedGroup} isEdit={!!selectedGroup} />
      </Modal>

      <ConfirmationModal title="Delete group" open={isDeleteOpen} onClose={() => setIsDeleteOpen(false)} onYes={onYes}>
        <p>Are you sure you want to delete group {selectedGroup?.name}?</p>
      </ConfirmationModal>

      <div className={classes.buttonAddGroup}>
        <Button onClick={() => { setSelectedGroup(undefined); setIsAddUpdateOpen(true); }}>Add Group</Button>
      </div> 

      <Table hasPagination={false}>
        <TableHead columns={['Name', 'Action']}/>
        <TableBody>
          {
            groups?.map((group: Group) => 
              <TableRow 
                key={group.id}
                cells={[
                  group.name,
                  group.name !== 'Admins' ? <ActionsButtonGroup group={group} /> : <></>
                ]}
              />
            )
          }
        </TableBody>
      </Table>
    </div>
  )
}