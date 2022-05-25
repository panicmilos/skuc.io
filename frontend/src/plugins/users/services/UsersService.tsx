import axios from "axios";
import { useEffect, useState } from "react";
import { BACKEND_API, CrudService } from "../imports";
import { ChangePasswordRequest } from "../models/ChangePasswordRequest";
import { CreateUserRequest, UpdateUserRequest, User } from "../models/User";

export const USERS_SERVICE_ID = 'UsersService';

export const useUsersService = (groupId: string) => {

  const [usersService, setUsersService] = useState(new UsersService(groupId));

  useEffect(() => {
    setUsersService(new UsersService(groupId));
  }, [groupId]);

  return [usersService];
}

export class UsersService extends CrudService<User, CreateUserRequest, UpdateUserRequest> {
  constructor(groupId: string) {
    super(USERS_SERVICE_ID, `${BACKEND_API}/groups/${groupId}/users`)
  }

  public async changePassword(userId: string, request: ChangePasswordRequest): Promise<User> {
    return (await axios.put( `${this.baseUrl}/${userId}/password`, request)).data;
  }
}