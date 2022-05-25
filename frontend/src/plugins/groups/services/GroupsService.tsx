import { useState } from "react";
import { BACKEND_API, CrudService } from "../imports";
import { CreateGroup, Group, UpdateGroup } from "../models/Group";

export const GROUPS_SERVICE_ID = 'GroupsService';

export const useGroupsService = () => {
  
  const [groupsService] = useState(new GroupsService());

  return [groupsService];
}

export class GroupsService extends CrudService<Group, CreateGroup, UpdateGroup> {
  constructor() {
    super(GROUPS_SERVICE_ID, `${BACKEND_API}/groups`)
  }

}