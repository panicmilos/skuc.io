import { CrudService } from "../../core";
import { BACKEND_API } from "../../urls";

export type { ContextPlugin } from "../../core";
export type User = {
  id: string;
  groupId: string;
  email: string;
  password: string;
  fullName: string;
  address: string;
  phoneNumber: string;
};

export class UsersService extends CrudService<User> {
  constructor(groupId: string) {
    super("UsersService", `${BACKEND_API}/groups/${groupId}/users`);
  }
}
