
export type CreateTemplate = {
  name: string,
  parameters: string[],
  when: string,
  then: string
}

export type Template = {
  id: string,
  groupId: string
  name: string,
  parameters: string[],
  template: string
}