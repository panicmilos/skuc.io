export type CreateGroup = {
  name: string;
}

export type UpdateGroup = CreateGroup;

export type Group = CreateGroup & {
  id: string;
}